# BlackBox Java to Kotlin Conversion Plan

**Created:** January 2026
**Status:** In Progress
**Target:** Convert 505 Java files to Kotlin with simplification

---

## Overview

| Metric | Current | Target |
|--------|---------|--------|
| Java Files | 285 | 0 |
| Kotlin Files | 275 | 560 |
| Java LOC | ~40,000 | 0 |
| Estimated Kotlin LOC | - | ~35,000 (30% reduction) |

---

## Phase Summary

| Phase | Description | Files | Status |
|-------|-------------|-------|--------|
| **Phase 1** | BlackReflection Interfaces | 220 | [x] **COMPLETED** |
| **Phase 2** | Entity/Model Classes | 16 | [ ] Not Started |
| **Phase 3** | Utility Classes | 49 | [ ] Not Started |
| **Phase 4** | Compat/Delegate Classes | 35 | [ ] Not Started |
| **Phase 5** | Proxy Classes | 97 | [ ] Not Started |
| **Phase 6** | System Services | 54 | [ ] Not Started |
| **Phase 7** | Core Classes | 34 | [ ] Not Started |
| **Phase 8** | Testing & Validation | - | [ ] Not Started |

---

## Phase 1: BlackReflection Interfaces (220 files)

**Risk Level:** Low
**Complexity:** Simple mechanical conversion
**Location:** `Bcore/src/main/java/black/`

### Conversion Pattern

**Before (Java):**
```java
@BClassName("android.app.ActivityThread")
public interface ActivityThread {
    @BField
    Instrumentation mInstrumentation();

    @BStaticMethod
    Object currentActivityThread();
}
```

**After (Kotlin):**
```kotlin
@BClassName("android.app.ActivityThread")
interface ActivityThread {
    @BField
    fun mInstrumentation(): Instrumentation

    @BStaticMethod
    fun currentActivityThread(): Any
}
```

### Files to Convert

#### black/android/app/ (25 files)
- [ ] `ActivityManager.java`
- [ ] `ActivityManagerNative.java`
- [ ] `ActivityManagerOreo.java`
- [ ] `ActivityThread.java`
- [ ] `ActivityThreadNMR1.java`
- [ ] `ActivityThreadQ.java`
- [ ] `ActivityThreadR.java`
- [ ] `ActivityThreadS.java`
- [ ] `AppOpsManager.java`
- [ ] `Application.java`
- [ ] `ContextImpl.java`
- [ ] `IActivityManager.java`
- [ ] `IApplicationThread.java`
- [ ] `IServiceConnection.java`
- [ ] `Instrumentation.java`
- [ ] `LoadedApk.java`
- [ ] `Notification.java`
- [ ] `NotificationChannel.java`
- [ ] `NotificationChannelGroup.java`
- [ ] `PendingIntent.java`
- [ ] `ResourcesManager.java`
- [ ] `Service.java`
- [ ] `WallpaperManager.java`
- [ ] ... (remaining files)

#### black/android/content/ (30 files)
- [ ] `ContentProvider.java`
- [ ] `ContentProviderClient.java`
- [ ] `ContentResolver.java`
- [ ] `Context.java`
- [ ] `Intent.java`
- [ ] `pm/ApplicationInfo.java`
- [ ] `pm/PackageParser.java`
- [ ] `pm/PackageManager.java`
- [ ] ... (remaining files)

#### black/android/os/ (40 files)
- [ ] `Binder.java`
- [ ] `Build.java`
- [ ] `Environment.java`
- [ ] `Handler.java`
- [ ] `IBinder.java`
- [ ] `Process.java`
- [ ] `ServiceManager.java`
- [ ] `storage/StorageManager.java`
- [ ] `storage/StorageVolume.java`
- [ ] ... (remaining files)

#### black/android/other packages/ (125 files)
- [ ] `accounts/` (10 files)
- [ ] `graphics/` (5 files)
- [ ] `hardware/` (8 files)
- [ ] `location/` (6 files)
- [ ] `media/` (12 files)
- [ ] `net/` (15 files)
- [ ] `provider/` (8 files)
- [ ] `telephony/` (20 files)
- [ ] `view/` (10 files)
- [ ] `webkit/` (5 files)
- [ ] ... (remaining)

---

## Phase 2: Entity/Model Classes (16 files)

**Risk Level:** Low
**Complexity:** Simple - great for Kotlin data classes
**Location:** `Bcore/src/main/java/top/niunaijun/blackbox/entity/`

### Conversion Pattern

**Before (Java):**
```java
public class AppConfig implements Parcelable {
    public String packageName;
    public int userId;
    // constructor, getters, setters, parcelable boilerplate...
}
```

**After (Kotlin):**
```kotlin
@Parcelize
data class AppConfig(
    val packageName: String,
    val userId: Int
) : Parcelable
```

### Files to Convert

#### entity/am/ (5 files)
- [ ] `PendingResultData.java` → `PendingResultData.kt`
- [ ] `ReceiverData.java` → `ReceiverData.kt`
- [ ] `RunningAppProcessInfo.java` → `RunningAppProcessInfo.kt`
- [ ] `RunningServiceInfo.java` → `RunningServiceInfo.kt`
- [ ] `Intent.java` → `Intent.kt`

#### entity/pm/ (6 files)
- [ ] `InstallOption.java` → `InstallOption.kt`
- [ ] `InstalledModule.java` → `InstalledModule.kt`
- [ ] `InstalledPackage.java` → `InstalledPackage.kt`
- [ ] `InstallResult.java` → `InstallResult.kt`
- [ ] `PackageFlags.java` → `PackageFlags.kt`
- [ ] `UnInstalledPackage.java` → `UnInstalledPackage.kt`

#### entity/ (5 files)
- [ ] `AppConfig.java` → `AppConfig.kt`
- [ ] `ClientConfig.java` → `ClientConfig.kt`
- [ ] `DeviceInfo.java` → `DeviceInfo.kt`
- [ ] `GpsConfig.java` → `GpsConfig.kt`
- [ ] `UserInfo.java` → `UserInfo.kt`

---

## Phase 3: Utility Classes (49 files)

**Risk Level:** Low-Medium
**Complexity:** Moderate - good Kotlin idiom candidates
**Location:** `Bcore/src/main/java/top/niunaijun/blackbox/utils/`

### Conversion Priorities

#### Priority 1 - Simple Utilities (20 files)
- [ ] `AbiUtils.java` → `AbiUtils.kt`
- [ ] `ArrayUtils.java` → `ArrayUtils.kt`
- [ ] `Base64Utils.java` → `Base64Utils.kt`
- [ ] `ClipboardUtils.java` → `ClipboardUtils.kt`
- [ ] `ComponentUtils.java` → `ComponentUtils.kt`
- [ ] `DrawableUtils.java` → `DrawableUtils.kt`
- [ ] `FileUtils.java` → `FileUtils.kt`
- [ ] `IoUtils.java` → `IoUtils.kt`
- [ ] `MD5Utils.java` → `MD5Utils.kt`
- [ ] `NativeUtils.java` → `NativeUtils.kt`
- [ ] `ScreenUtils.java` → `ScreenUtils.kt`
- [ ] `ShellUtils.java` → `ShellUtils.kt`
- [ ] `Slog.java` → `Slog.kt`
- [ ] `StringUtils.java` → `StringUtils.kt`
- [ ] `SystemUtils.java` → `SystemUtils.kt`
- [ ] `UriCompat.java` → `UriCompat.kt`
- [ ] `Util.java` → `Util.kt`
- [ ] `ZipUtils.java` → `ZipUtils.kt`
- [ ] `ProviderUtils.java` → `ProviderUtils.kt`
- [ ] `SignatureUtils.java` → `SignatureUtils.kt`

#### Priority 2 - Compat Utilities (15 files)
- [ ] `compat/ActivityCompat.java`
- [ ] `compat/ApplicationThreadCompat.java`
- [ ] `compat/BuildCompat.java`
- [ ] `compat/BundleCompat.java`
- [ ] `compat/ContentProviderCompat.java`
- [ ] `compat/ContextCompat.java`
- [ ] `compat/DexFileCompat.java`
- [ ] `compat/ParceledListSliceCompat.java`
- [ ] `compat/PermissionCompat.java`
- [ ] `compat/StartActivityCompat.java`
- [ ] `compat/StrictModeCompat.java`
- [ ] `compat/TaskDescriptionCompat.java`
- [ ] `compat/UserHandleCompat.java`
- [ ] `compat/VersionCompat.java`
- [ ] `compat/XposedCompat.java`

#### Priority 3 - Complex Utilities (14 files)
- [ ] `CrashMonitor.java`
- [ ] `DexCrashPrevention.java`
- [ ] `NativeCrashPrevention.java`
- [ ] `Reflector.java`
- [ ] `ReflectionUtils.java`
- [ ] `VirtualRuntime.java`
- [ ] `ApkSignatureVerifier.java`
- [ ] `AutoCloseableMutex.java`
- [ ] `AtomicFile.java`
- [ ] `OsUtils.java`
- [ ] `ProcessUtils.java`
- [ ] `PackageUtils.java`
- [ ] `ParceledListSlice.java`
- [ ] `FastXmlSerializer.java`

---

## Phase 4: Compat/Delegate Classes (35 files)

**Risk Level:** Medium
**Complexity:** Moderate
**Location:** `Bcore/src/main/java/top/niunaijun/blackbox/fake/delegate/`

### Files to Convert

#### Delegate Classes (15 files)
- [ ] `AppInstrumentation.java`
- [ ] `ApplicationDelegate.java`
- [ ] `ActivityLifecycleDelegate.java`
- [ ] `BroadcastReceiverDelegate.java`
- [ ] `ContentProviderDelegate.java`
- [ ] `InnerReceiverDelegate.java`
- [ ] `ServiceConnectionDelegate.java`
- [ ] ... (remaining)

#### Provider Classes (10 files)
- [ ] `fake/provider/FileProvider.java`
- [ ] `fake/provider/FileProviderHandler.java`
- [ ] ... (remaining)

#### Frameworks (10 files)
- [ ] `fake/frameworks/BActivityManager.java`
- [ ] `fake/frameworks/BContentResolver.java`
- [ ] `fake/frameworks/BJobManager.java`
- [ ] `fake/frameworks/BLocationManager.java`
- [ ] `fake/frameworks/BNotificationManager.java`
- [ ] `fake/frameworks/BPackageManager.java`
- [ ] `fake/frameworks/BStorageManager.java`
- [ ] `fake/frameworks/BUserManager.java`
- [ ] `fake/frameworks/BWebViewFactory.java`
- [ ] `fake/frameworks/BWindowManager.java`

---

## Phase 5: Proxy Classes (97 files)

**Risk Level:** Medium-High
**Complexity:** Complex - core hooking logic
**Location:** `Bcore/src/main/java/top/niunaijun/blackbox/fake/service/`

### Conversion Order

#### Group 1: Base Classes (5 files)
- [ ] `ProxyMethod.java`
- [ ] `ClassLoaderProxy.java`
- [ ] `StubMethodProxy.java`
- [ ] `ValueMethodProxy.java`
- [ ] `MethodParameterUtils.java`

#### Group 2: Simple Proxies (30 files)
- [ ] `IAlarmManagerProxy.java`
- [ ] `IAppOpsManagerProxy.java`
- [ ] `IAudioManagerProxy.java`
- [ ] `IAutofillManagerProxy.java`
- [ ] `IClipboardManagerProxy.java`
- [ ] `IConnectivityManagerProxy.java`
- [ ] `IContextHubServiceProxy.java`
- [ ] `IDevicePolicyManagerProxy.java`
- [ ] `IDnsResolverProxy.java`
- [ ] `IDownloadManagerProxy.java`
- [ ] ... (remaining simple proxies)

#### Group 3: Complex Proxies (40 files)
- [ ] `IActivityManagerProxy.java` (836 lines)
- [ ] `IActivityTaskManagerProxy.java`
- [ ] `IPackageManagerProxy.java` (739 lines)
- [ ] `IPermissionManagerProxy.java`
- [ ] `INotificationManagerProxy.java`
- [ ] `ITelephonyManagerProxy.java`
- [ ] `HCallbackProxy.java`
- [ ] ... (remaining complex proxies)

#### Group 4: Hook Classes (22 files)
- [ ] `fake/hook/HookManager.java`
- [ ] `fake/hook/IHook.java`
- [ ] ... (remaining)

---

## Phase 6: System Services (54 files)

**Risk Level:** High
**Complexity:** Very Complex - core virtualization
**Location:** `Bcore/src/main/java/top/niunaijun/blackbox/core/system/`

### Files to Convert

#### Package Management (15 files)
- [ ] `pm/BPackage.java` (735 lines)
- [ ] `pm/BPackageManagerService.java` (836 lines)
- [ ] `pm/BPackageSettings.java`
- [ ] `pm/BPackageUserState.java`
- [ ] `pm/ComponentResolver.java` (643 lines)
- [ ] `pm/IntentResolver.java` (741 lines)
- [ ] `pm/PackageManagerCompat.java`
- [ ] `pm/Settings.java`
- [ ] ... (remaining)

#### Activity Management (12 files)
- [ ] `am/BActivityManagerService.java`
- [ ] `am/ActivityStack.java` (560 lines)
- [ ] `am/ActiveServices.java`
- [ ] `am/BroadcastManager.java`
- [ ] `am/PendingIntentRecord.java`
- [ ] `am/ProcessRecord.java`
- [ ] `am/RunningServiceRecord.java`
- [ ] `am/TaskRecord.java`
- [ ] ... (remaining)

#### Account Management (8 files)
- [ ] `accounts/BAccount.java`
- [ ] `accounts/BAccountManagerService.java` (1,849 lines)
- [ ] `accounts/BAuthenticatorCache.java`
- [ ] ... (remaining)

#### Other Services (19 files)
- [ ] `user/BUserHandle.java`
- [ ] `user/BUserInfo.java`
- [ ] `user/BUserManagerService.java`
- [ ] `os/BStorageManagerService.java`
- [ ] `notification/BNotificationManagerService.java`
- [ ] `job/BJobManagerService.java`
- [ ] `location/BLocationManagerService.java`
- [ ] `permission/XiaomiPermissionManager.java`
- [ ] ... (remaining)

---

## Phase 7: Core Classes (34 files)

**Risk Level:** Critical
**Complexity:** Highest - main engine
**Location:** `Bcore/src/main/java/top/niunaijun/blackbox/`

### Critical Files (Must be converted carefully)

#### Core Engine (10 files)
- [ ] `BlackBoxCore.java` (1,983 lines) - **CRITICAL**
- [ ] `app/BActivityThread.java` (1,381 lines) - **CRITICAL**
- [ ] `app/LauncherActivity.java`
- [ ] `app/LoadedPackage.java`
- [ ] `core/NativeCore.java`
- [ ] `core/IOCore.java`
- [ ] `core/GmsCore.java`
- [ ] `core/JarManager.java`
- [ ] `core/VMCore.java`
- [ ] `core/CrashHandler.java`

#### Proxy Components (12 files)
- [ ] `proxy/ProxyActivity.java`
- [ ] `proxy/ProxyBroadcastReceiver.java`
- [ ] `proxy/ProxyContentProvider.java`
- [ ] `proxy/ProxyJobService.java`
- [ ] `proxy/ProxyManifest.java`
- [ ] `proxy/ProxyPendingReceiver.java`
- [ ] `proxy/ProxyService.java`
- [ ] `proxy/ProxyVpnService.java`
- [ ] `proxy/record/ProxyActivityRecord.java`
- [ ] `proxy/record/ProxyBroadcastRecord.java`
- [ ] `proxy/record/ProxyPendingIntentRecord.java`
- [ ] `proxy/record/ProxyServiceRecord.java`

#### Environment (12 files)
- [ ] `core/env/AppSystemEnv.java`
- [ ] `core/env/BEnvironment.java`
- [ ] `core/env/SystemUserEnv.java`
- [ ] `core/env/VirtualRuntime.java`
- [ ] ... (remaining)

---

## Phase 8: Testing & Validation

**Risk Level:** Essential
**Complexity:** Varies

### Test Cases

- [ ] App installation in virtual environment
- [ ] App launch and lifecycle
- [ ] Activity transitions
- [ ] Service binding
- [ ] Broadcast receiving
- [ ] Content provider queries
- [ ] Storage isolation
- [ ] GPS spoofing
- [ ] Multi-user support
- [ ] Xposed module loading
- [ ] Native library loading
- [ ] Permission handling
- [ ] Notification display
- [ ] Account management

### Validation Checklist

- [ ] All unit tests pass
- [ ] Debug APK builds successfully
- [ ] Release APK builds successfully
- [ ] Install test app in virtual environment
- [ ] Test app runs correctly
- [ ] No memory leaks
- [ ] No performance regression
- [ ] JNI calls work correctly

---

## Kotlin Conversion Guidelines

### General Rules

1. **Use data classes** for POJOs/entities
2. **Use object** for singletons instead of static getInstance()
3. **Use extension functions** for utility methods
4. **Use sealed classes** for enums with data
5. **Use coroutines** for async operations (carefully)
6. **Use nullable types** (`?`) appropriately
7. **Use `when`** instead of switch statements
8. **Use `apply`/`also`/`let`/`run`** for scoping

### JNI Considerations

```kotlin
// Keep @JvmStatic for JNI-called methods
companion object {
    @JvmStatic
    external fun nativeInit(apiLevel: Int)

    @JvmStatic
    fun callbackFromNative(data: String) { }
}
```

### AIDL Considerations

AIDL files remain as-is (Java). Kotlin implementation classes work fine.

### BlackReflection Considerations

BlackReflection annotations work with Kotlin interfaces.

---

## Progress Tracking

### Weekly Progress

| Week | Phase | Files Converted | Notes |
|------|-------|-----------------|-------|
| 1 | Phase 1 | 220 | BlackReflection interfaces completed |
| 2 | | | |
| 3 | | | |
| 4 | | | |
| 5 | | | |
| 6 | | | |

### Metrics

| Metric | Start | Current | Target |
|--------|-------|---------|--------|
| Java Files | 505 | 285 | 0 |
| Kotlin Files | 55 | 275 | 560 |
| Total LOC | 59,754 | ~55,000 | ~45,000 |
| Build Time | - | - | - |
| APK Size | 18MB | 17.3MB | - |

---

## Notes

- Always run `./gradlew assembleDebug` after each conversion batch
- Keep git commits small and focused (one phase/group at a time)
- Test on real device after each phase completion
- Document any issues encountered in this file

---

## Changelog

| Date | Change |
|------|--------|
| 2026-01-06 | Initial plan created |
| 2026-01-06 | **Phase 1 COMPLETED** - 220 BlackReflection interfaces converted to Kotlin |
| 2026-01-06 | Added Kotlin plugin to Bcore/build.gradle (kotlin-android, kotlin-kapt) |
| 2026-01-06 | Build verified successful |