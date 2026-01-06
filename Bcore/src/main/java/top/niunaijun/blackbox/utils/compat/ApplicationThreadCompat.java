package top.niunaijun.blackbox.utils.compat;

import android.os.IBinder;
import android.os.IInterface;

import black.android.app.BRApplicationThreadNative;
import black.android.app.BRIApplicationThreadOreoStub;

public class ApplicationThreadCompat {

    public static IInterface asInterface(IBinder binder) {
        // Always use Oreo+ API on API 29+
        return BRIApplicationThreadOreoStub.get().asInterface(binder);
    }
}
