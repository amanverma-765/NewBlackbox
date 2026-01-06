package black.android.app;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;

import top.niunaijun.blackreflection.annotation.BClassName;
import top.niunaijun.blackreflection.annotation.BField;
import top.niunaijun.blackreflection.annotation.BMethod;
import top.niunaijun.blackreflection.annotation.BStaticMethod;

/**
 * Consolidated ContextImpl reflection interface for API 29+.
 * Includes fields from former ContextImplKitkat (API 19+) which are always available.
 */
@BClassName("android.app.ContextImpl")
public interface ContextImpl {
    @BField
    String mBasePackageName();

    @BField
    ContentResolver mContentResolver();

    @BField
    Object mPackageInfo();

    @BField
    PackageManager mPackageManager();

    // Consolidated from ContextImplKitkat - always available on API 29+
    @BField
    Object mDisplayAdjustments();

    @BField
    java.io.File[] mExternalCacheDirs();

    @BField
    java.io.File[] mExternalFilesDirs();

    @BField
    String mOpPackageName();

    @BStaticMethod
    Object createAppContext();

    @BMethod
    Context getReceiverRestrictedContext();

    @BMethod
    void setOuterContext(Context Context0);

    @BMethod
    Object getAttributionSource();

    @BMethod
    PackageManager getPackageManager();
}
