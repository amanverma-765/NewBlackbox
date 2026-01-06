package top.niunaijun.blackbox.fake.service;

import java.io.File;
import java.lang.reflect.Field;

import dalvik.system.PathClassLoader;
import top.niunaijun.blackbox.utils.Slog;

/**
 * Fixes for Meta's Dynamic Class Loading (MDCLLight) system used by Instagram and other Meta apps.
 *
 * MDCLLight creates its own ClassLoader hierarchy with BootClassLoader as parent,
 * which prevents loading classes from the app's APK (including ReVanced patches).
 *
 * This class provides utilities to:
 * 1. Detect when MDCLLight is set as the context ClassLoader
 * 2. Fix MDCLLight's parent chain by injecting a PathClassLoader with the app's APK
 * 3. Create a wrapper ClassLoader that intercepts class loading failures
 */
public class MDCLLightFixer {
    private static final String TAG = "MDCLLightFixer";

    // The APK path for the current virtual app
    private static String sApkPath;

    // Reference to the PathClassLoader we created for fallback
    private static PathClassLoader sApkClassLoader;

    // Flag to track if we've already fixed MDCLLight
    private static boolean sFixed = false;

    /**
     * Initialize the MDCLLight fixer with the app's APK path.
     * This should be called early in the app binding process.
     */
    public static void init(String apkPath) {
        sApkPath = apkPath;
        sFixed = false;
        sApkClassLoader = null;

        if (apkPath != null && new File(apkPath).exists()) {
            // Create the PathClassLoader eagerly so it's ready when needed
            try {
                sApkClassLoader = new PathClassLoader(apkPath, ClassLoader.getSystemClassLoader().getParent());
                Slog.d(TAG, "Created APK PathClassLoader for: " + apkPath);
            } catch (Exception e) {
                Slog.w(TAG, "Failed to create APK PathClassLoader: " + e.getMessage());
            }
        }

        Slog.d(TAG, "Initialized with APK path: " + apkPath);
    }

    /**
     * Install a custom context ClassLoader wrapper that intercepts class loading.
     * This should be called BEFORE Application.onCreate() to catch MDCLLight early.
     */
    public static void installClassLoaderWrapper(ClassLoader originalLoader) {
        if (originalLoader == null || sApkPath == null) {
            Slog.w(TAG, "Cannot install wrapper: originalLoader or apkPath is null");
            return;
        }

        try {
            ClassLoaderWrapper wrapper = new ClassLoaderWrapper(originalLoader, sApkPath);
            Thread.currentThread().setContextClassLoader(wrapper);
            Slog.d(TAG, "Installed ClassLoader wrapper");
        } catch (Exception e) {
            Slog.w(TAG, "Failed to install ClassLoader wrapper: " + e.getMessage());
        }
    }

    /**
     * Check and fix MDCLLight if it's the current context ClassLoader.
     * This can be called multiple times - it will only fix once.
     */
    public static void checkAndFix() {
        if (sFixed) {
            return;
        }

        try {
            ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
            if (contextLoader == null) {
                return;
            }

            if (isMDCLLight(contextLoader)) {
                fixMDCLLight(contextLoader);
            }
        } catch (Exception e) {
            Slog.w(TAG, "Error checking/fixing MDCLLight: " + e.getMessage());
        }
    }

    /**
     * Check if a ClassLoader is MDCLLight.
     */
    public static boolean isMDCLLight(ClassLoader loader) {
        if (loader == null) return false;
        String name = loader.getClass().getName();
        return name.contains("MultiDexClassLoaderLight") ||
               name.contains("MDCLLight") ||
               name.contains("InMemoryDexClassLoader");
    }

    /**
     * Fix MDCLLight's parent ClassLoader chain by injecting a PathClassLoader
     * that has access to the app's APK.
     */
    public static void fixMDCLLight(ClassLoader mdclLight) {
        if (sApkPath == null || sApkPath.isEmpty()) {
            Slog.w(TAG, "Cannot fix MDCLLight: APK path not set");
            return;
        }

        if (!new File(sApkPath).exists()) {
            Slog.w(TAG, "Cannot fix MDCLLight: APK file doesn't exist: " + sApkPath);
            return;
        }

        try {
            // Get MDCLLight's current parent
            ClassLoader currentParent = mdclLight.getParent();
            String currentParentName = currentParent != null ?
                currentParent.getClass().getName() : "null";

            Slog.d(TAG, "MDCLLight's current parent: " + currentParentName);

            // If parent is already a PathClassLoader, check if it has the APK
            if (currentParent != null &&
                (currentParentName.contains("PathClassLoader") ||
                 currentParentName.contains("DexClassLoader"))) {

                // Already has a proper parent - check if it can load classes
                Slog.d(TAG, "MDCLLight already has PathClassLoader/DexClassLoader parent, skipping fix");
                sFixed = true;
                return;
            }

            // Create a PathClassLoader with the APK, using the current parent as its parent
            PathClassLoader apkClassLoader = new PathClassLoader(sApkPath, currentParent);

            // Use reflection to set MDCLLight's parent field
            Field parentField = ClassLoader.class.getDeclaredField("parent");
            parentField.setAccessible(true);
            parentField.set(mdclLight, apkClassLoader);

            sFixed = true;

            // Verify the fix
            ClassLoader newParent = mdclLight.getParent();
            Slog.d(TAG, "Fixed MDCLLight parent chain. New parent: " +
                (newParent != null ? newParent.getClass().getName() : "null"));

        } catch (NoSuchFieldException e) {
            Slog.w(TAG, "Could not find parent field in ClassLoader: " + e.getMessage());
        } catch (IllegalAccessException e) {
            Slog.w(TAG, "Could not access parent field in ClassLoader: " + e.getMessage());
        } catch (Exception e) {
            Slog.w(TAG, "Error fixing MDCLLight: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Try to load a class using our fallback APK ClassLoader.
     */
    public static Class<?> loadClassFromApk(String className) {
        if (sApkClassLoader == null) {
            return null;
        }

        try {
            return sApkClassLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Reset the fixer state. Useful when a new app is being bound.
     */
    public static void reset() {
        sApkPath = null;
        sApkClassLoader = null;
        sFixed = false;
    }

    /**
     * A wrapper ClassLoader that intercepts class loading and can fix MDCLLight on-the-fly.
     * This wrapper is designed to detect when the actual ClassLoader changes (e.g., when
     * MDCLLight replaces it) and fix it immediately.
     */
    public static class ClassLoaderWrapper extends ClassLoader {
        private final ClassLoader mDelegate;
        private final String mApkPath;
        private PathClassLoader mFallbackLoader;

        public ClassLoaderWrapper(ClassLoader delegate, String apkPath) {
            super(delegate);
            mDelegate = delegate;
            mApkPath = apkPath;

            // Create a fallback loader
            try {
                mFallbackLoader = new PathClassLoader(apkPath, ClassLoader.getSystemClassLoader().getParent());
            } catch (Exception e) {
                Slog.w(TAG, "Failed to create fallback loader: " + e.getMessage());
            }
        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            // Check if context ClassLoader has changed (MDCLLight was set)
            ClassLoader current = Thread.currentThread().getContextClassLoader();
            if (current != this && isMDCLLight(current)) {
                // MDCLLight was set, fix it immediately
                fixMDCLLight(current);
            }

            try {
                // First try the delegate
                return mDelegate.loadClass(name);
            } catch (ClassNotFoundException e) {
                // If delegate fails, try our fallback
                if (mFallbackLoader != null) {
                    try {
                        Class<?> result = mFallbackLoader.loadClass(name);
                        Slog.d(TAG, "Loaded class via fallback: " + name);
                        return result;
                    } catch (ClassNotFoundException e2) {
                        // Fall through to throw the original exception
                    }
                }
                throw e;
            }
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            return loadClass(name, false);
        }
    }

    /**
     * Monitor for context ClassLoader changes and fix MDCLLight when detected.
     * This runs in a background thread to catch MDCLLight being set during Application.onCreate().
     */
    public static void startMonitoring() {
        Thread monitor = new Thread(() -> {
            ClassLoader lastSeen = null;
            int checkCount = 0;
            final int maxChecks = 50; // Check for 5 seconds max

            while (checkCount < maxChecks) {
                try {
                    Thread.sleep(100);
                    checkCount++;

                    ClassLoader current = Thread.currentThread().getContextClassLoader();
                    if (current != lastSeen && isMDCLLight(current)) {
                        Slog.d(TAG, "Monitor detected MDCLLight, fixing...");
                        fixMDCLLight(current);
                        break; // Fixed, stop monitoring
                    }
                    lastSeen = current;

                } catch (InterruptedException e) {
                    break;
                } catch (Exception e) {
                    Slog.w(TAG, "Monitor error: " + e.getMessage());
                }
            }

            Slog.d(TAG, "Monitor finished after " + checkCount + " checks");
        }, "MDCLLight-Monitor");

        monitor.setDaemon(true);
        monitor.start();

        Slog.d(TAG, "Started MDCLLight monitor thread");
    }
}
