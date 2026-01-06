package black.android.content.pm;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.os.Bundle;

import java.io.File;
import java.util.List;

import top.niunaijun.blackreflection.annotation.BClassName;
import top.niunaijun.blackreflection.annotation.BConstructor;
import top.niunaijun.blackreflection.annotation.BField;
import top.niunaijun.blackreflection.annotation.BMethod;
import top.niunaijun.blackreflection.annotation.BStaticMethod;

/**
 * Reflection interface for android.content.pm.PackageParser.
 * Consolidated from PackageParser + PackageParserMarshmallow + PackageParserPie for API 29+.
 */
@BClassName("android.content.pm.PackageParser")
public interface PackageParser {
    // Merged from PackageParserMarshmallow (API 23+, always available on API 29+)
    @BConstructor
    android.content.pm.PackageParser _new();

    @BMethod
    android.content.pm.PackageParser.Package parsePackage(File File0, int int1);

    // Merged from PackageParserPie (API 28+, always available on API 29+)
    @BStaticMethod
    void collectCertificates(android.content.pm.PackageParser.Package p, boolean skipVerify);

    @BClassName("android.content.pm.PackageParser$SigningDetails")
    interface SigningDetails {
        @BField
        Signature[] pastSigningCertificates();

        @BField
        Signature[] signatures();

        @BMethod
        Boolean hasPastSigningCertificates();

        @BMethod
        Boolean hasSignatures();
    }

    @BClassName("android.content.pm.PackageParser$Component")
    interface Component {
        @BField
        String className();

        @BField
        ComponentName componentName();

        @BField
        List<android.content.IntentFilter> intents();
    }

    @BClassName("android.content.pm.PackageParser$PermissionGroup")
    interface PermissionGroup {
        @BField
        PermissionGroupInfo info();
    }

    @BClassName("android.content.pm.PackageParser$Permission")
    interface Permission {
        @BField
        PermissionInfo info();
    }

    @BClassName("android.content.pm.PackageParser$Service")
    interface Service {
        @BField
        ServiceInfo info();
    }

    @BClassName("android.content.pm.PackageParser$Provider")
    interface Provider {
        @BField
        ProviderInfo info();
    }

    @BClassName("android.content.pm.PackageParser$Activity")
    interface Activity {
        @BField
        ActivityInfo info();
    }

    @BClassName("android.content.pm.PackageParser$Package")
    interface Package {
        @BField
        List activities();

        @BField
        Bundle mAppMetaData();

        @BField
        String mSharedUserId();

        @BField
        Signature[] mSignatures();

        @BField
        Object mSigningDetails();

        @BField
        Integer mVersionCode();

        @BField
        String packageName();

        @BField
        List permissionGroups();

        @BField
        List permissions();

        @BField
        List<String> protectedBroadcasts();

        @BField
        List providers();

        @BField
        List receivers();

        @BField
        List<String> requestedPermissions();

        @BField
        List services();
    }
}
