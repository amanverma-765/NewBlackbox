package black.android.content.pm;

import top.niunaijun.blackreflection.annotation.BClassName;
import top.niunaijun.blackreflection.annotation.BField;

/**
 * Reflection interface for android.content.pm.ApplicationInfo.
 * Consolidated from ApplicationInfoL + ApplicationInfoN for API 29+.
 */
@BClassName("android.content.pm.ApplicationInfo")
public interface ApplicationInfo {
    // Merged from ApplicationInfoL (API 21+, always available on API 29+)
    @BField
    String primaryCpuAbi();

    @BField
    Integer privateFlags();

    @BField
    String scanPublicSourceDir();

    @BField
    String scanSourceDir();

    @BField
    String secondaryCpuAbi();

    @BField
    String secondaryNativeLibraryDir();

    @BField
    String[] splitPublicSourceDirs();

    @BField
    String[] splitSourceDirs();

    // Merged from ApplicationInfoN (API 24+, always available on API 29+)
    @BField
    String credentialEncryptedDataDir();

    @BField
    String credentialProtectedDataDir();

    @BField
    String deviceEncryptedDataDir();

    @BField
    String deviceProtectedDataDir();
}
