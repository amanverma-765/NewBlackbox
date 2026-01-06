package top.niunaijun.blackbox.utils.compat;

import android.content.pm.PackageParser;
import android.content.pm.PackageParser.Package;

import java.io.File;

import black.android.content.pm.BRPackageParser;

/**
 * PackageParser compatibility utilities.
 * Simplified for Android 10+ (API 29+).
 */
public class PackageParserCompat {

    public static final int[] GIDS = new int[]{};

    public static PackageParser createParser(File packageFile) {
        return BRPackageParser.get()._new();
    }

    public static Package parsePackage(PackageParser parser, File packageFile, int flags) throws Throwable {
        return BRPackageParser.getWithException(parser).parsePackage(packageFile, flags);
    }

    public static void collectCertificates(PackageParser parser, Package p, int flags) throws Throwable {
        BRPackageParser.getWithException().collectCertificates(p, true/*skipVerify*/);
    }
}
