package top.niunaijun.blackbox.utils.compat;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

// Simplified for Android 10+ (API 29+) - no version checks needed

/**
 * updated by alex5402 on 4/9/21.
 * * ∧＿∧
 * (`･ω･∥
 * 丶　つ０
 * しーＪ
 * TFNQw5HgWUS33Ke1eNmSFTwoQySGU7XNsK (USDT TRC20)
 */
public class StartActivityCompat {
    // Simplified for API 29+ (Android 10+) - always use R+ (API 30+) indices
    // callingFeatureIdIndex was added in Android 11 (API 30)
    // All indices are final since minSdk 29 means we always use the same layout
    private static final int appThreadIndex = 0;
    private static final int callingPageIndex = 1;
    private static final int callingFeatureIdIndex = 2;
    private static final int intentIndex = 3;
    private static final int resolvedTypeIndex = 4;
    private static final int resultToIndex = 5;
    private static final int resultWhoIndex = 6;
    private static final int requestCodeIndex = 7;
    private static final int flagsIndex = 8;
    private static final int profilerInfoIndex = 9;
    private static final int optionsIndex = 10;

    public static Object getIApplicationThread(Object[] args) {
        if (args == null || args.length < appThreadIndex) {
            return null;
        }
        return args[appThreadIndex];
    }

    public static String getCallingPackage(Object[] args) {
        if (args == null || args.length < callingPageIndex) {
            return null;
        }
        return (String) args[callingPageIndex];
    }

    public static Intent getIntent(Object[] args) {
        if (args == null || args.length < intentIndex) {
            return null;
        }
        return (Intent) args[intentIndex];
    }

    public static String getResolvedType(Object[] args) {
        if (args == null || args.length < resolvedTypeIndex) {
            return null;
        }
        return (String) args[resolvedTypeIndex];
    }

    public static IBinder getResultTo(Object[] args) {
        if (args == null || args.length < resultToIndex) {
            return null;
        }
        return (IBinder) args[resultToIndex];
    }

    public static String getResultWho(Object[] args) {
        if (args == null || args.length < resultWhoIndex) {
            return null;
        }
        return (String) args[resultWhoIndex];
    }

    public static int getRequestCode(Object[] args) {
        if (args == null || args.length < requestCodeIndex) {
            return -1;
        }
        return (int) args[requestCodeIndex];
    }

    public static int getFlags(Object[] args) {
        if (args == null || args.length < flagsIndex) {
            return -1;
        }
        return (int) args[flagsIndex];
    }

    public static Object getProfilerInfo(Object[] args) {
        if (args == null || args.length < profilerInfoIndex) {
            return null;
        }
        return args[profilerInfoIndex];
    }

    public static Bundle getOptions(Object[] args) {
        if (args == null || args.length < optionsIndex) {
            return null;
        }
        return (Bundle) args[optionsIndex];
    }


}
