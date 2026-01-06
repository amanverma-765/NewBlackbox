package black.android.app;

import android.content.ComponentName;
import android.os.IBinder;

import top.niunaijun.blackreflection.annotation.BClassName;
import top.niunaijun.blackreflection.annotation.BMethod;

/**
 * Reflection interface for Android 16 (API 36+) IServiceConnection.
 * Added new method signature with IBinderSession parameter.
 */
@BClassName("android.app.IServiceConnection")
public interface IServiceConnectionV {
    /**
     * Android 16+ connected method with IBinderSession.
     * The session parameter is typed as IBinder since IBinderSession may not exist on older APIs.
     *
     * @param componentName The service component
     * @param service The service binder
     * @param session The binder session (IBinderSession)
     * @param dead Whether the service is dead
     */
    @BMethod
    void connected(ComponentName componentName, IBinder service, IBinder session, boolean dead);
}
