package top.niunaijun.blackbox.utils.compat;

import android.content.pm.PackageParser;
import android.content.pm.PackageParser.Package;

import java.io.File;

import black.android.content.pm.BRPackageParserMarshmallow;
import black.android.content.pm.BRPackageParserPie;

/**
 * PackageParser compatibility utilities.
 * Simplified for Android 10+ (API 29+) - only needs Pie+ code paths.
 */
public class PackageParserCompat {

    public static final int[] GIDS = new int[]{};

    public static PackageParser createParser(File packageFile) {
        return BRPackageParserMarshmallow.get()._new();
    }

    public static Package parsePackage(PackageParser parser, File packageFile, int flags) throws Throwable {
        return BRPackageParserMarshmallow.getWithException(parser).parsePackage(packageFile, flags);
    }

    public static void collectCertificates(PackageParser parser, Package p, int flags) throws Throwable {
        // Android 10+ always uses Pie path (isPie() is always true)
        BRPackageParserPie.getWithException().collectCertificates(p, true/*skipVerify*/);
    }
}
