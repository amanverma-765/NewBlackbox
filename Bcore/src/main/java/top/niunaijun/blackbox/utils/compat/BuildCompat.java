package top.niunaijun.blackbox.utils.compat;

import android.os.Build;

/**
 * Build compatibility utilities.
 * Simplified for Android 10+ (API 29+).
 */
public class BuildCompat {

    public static int getPreviewSDKInt() {
        try {
            return Build.VERSION.PREVIEW_SDK_INT;
        } catch (Throwable e) {
            return 0;
        }
    }

    // Android 14 (API 34)
    public static boolean isU() {
        return Build.VERSION.SDK_INT >= 34 || (Build.VERSION.SDK_INT >= 33 && Build.VERSION.PREVIEW_SDK_INT == 1);
    }

    // Android 13 (API 33)
    public static boolean isTiramisu() {
        return Build.VERSION.SDK_INT >= 33 || (Build.VERSION.SDK_INT >= 32 && Build.VERSION.PREVIEW_SDK_INT == 1);
    }

    // Android 12 (API 31)
    public static boolean isS() {
        return Build.VERSION.SDK_INT >= 31 || (Build.VERSION.SDK_INT >= 30 && Build.VERSION.PREVIEW_SDK_INT == 1);
    }

    // Android 11 (API 30)
    public static boolean isR() {
        return Build.VERSION.SDK_INT >= 30 || (Build.VERSION.SDK_INT >= 29 && Build.VERSION.PREVIEW_SDK_INT == 1);
    }

    // Android 10 (API 29) - baseline, always true
    public static boolean isQ() {
        return true;
    }

    // Android 9 (API 28) - always true on Android 10+
    public static boolean isPie() {
        return true;
    }

    // Android 8 (API 26) - always true on Android 10+
    public static boolean isOreo() {
        return true;
    }

    // Android 7.1 (API 25) - always true on Android 10+
    public static boolean isN_MR1() {
        return true;
    }

    // Android 7 (API 24) - always true on Android 10+
    public static boolean isN() {
        return true;
    }

    // Android 6 (API 23) - always true on Android 10+
    public static boolean isM() {
        return true;
    }

    // Android 5 (API 21) - always true on Android 10+
    public static boolean isL() {
        return true;
    }

    public static boolean isSamsung() {
        return "samsung".equalsIgnoreCase(Build.BRAND) || "samsung".equalsIgnoreCase(Build.MANUFACTURER);
    }

    public static boolean isEMUI() {
        if (Build.DISPLAY.toUpperCase().startsWith("EMUI")) {
            return true;
        }
        String property = SystemPropertiesCompat.get("ro.build.version.emui");
        return property != null && property.contains("EmotionUI");
    }

    public static boolean isMIUI() {
        return SystemPropertiesCompat.getInt("ro.miui.ui.version.code", 0) > 0;
    }

    public static boolean isFlyme() {
        return Build.DISPLAY.toLowerCase().contains("flyme");
    }

    public static boolean isColorOS() {
        return SystemPropertiesCompat.isExist("ro.build.version.opporom")
                || SystemPropertiesCompat.isExist("ro.rom.different.version");
    }

    public static boolean is360UI() {
        String property = SystemPropertiesCompat.get("ro.build.uiversion");
        return property != null && property.toUpperCase().contains("360UI");
    }

    public static boolean isLetv() {
        return Build.MANUFACTURER.equalsIgnoreCase("Letv");
    }

    public static boolean isVivo() {
        return SystemPropertiesCompat.isExist("ro.vivo.os.build.display.id");
    }


    private static ROMType sRomType;

    public static ROMType getROMType() {
        if (sRomType == null) {
            if (isEMUI()) {
                sRomType = ROMType.EMUI;
            } else if (isMIUI()) {
                sRomType = ROMType.MIUI;
            } else if (isFlyme()) {
                sRomType = ROMType.FLYME;
            } else if (isColorOS()) {
                sRomType = ROMType.COLOR_OS;
            } else if (is360UI()) {
                sRomType = ROMType._360;
            } else if (isLetv()) {
                sRomType = ROMType.LETV;
            } else if (isVivo()) {
                sRomType = ROMType.VIVO;
            } else if (isSamsung()) {
                sRomType = ROMType.SAMSUNG;
            } else {
                sRomType = ROMType.OTHER;
            }
        }
        return sRomType;
    }

    public enum ROMType {
        EMUI,
        MIUI,
        FLYME,
        COLOR_OS,
        LETV,
        VIVO,
        _360,
        SAMSUNG,
        OTHER
    }
}
