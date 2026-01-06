package black.android.content;

import android.content.pm.ProviderInfo;
import android.os.IInterface;

import top.niunaijun.blackreflection.annotation.BClassName;
import top.niunaijun.blackreflection.annotation.BField;

/**
 * ContentProviderHolder reflection interface for API 26+ (Oreo+).
 * Named "Oreo" to indicate API version where this structure was introduced.
 * Always available on minSdk 29.
 */
@BClassName("android.app.ContentProviderHolder")
public interface ContentProviderHolderOreo {
    @BField
    ProviderInfo info();

    @BField
    boolean noReleaseNeeded();

    @BField
    IInterface provider();
}
