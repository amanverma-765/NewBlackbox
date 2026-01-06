package top.niunaijun.blackbox.fake.delegate;

import android.app.IServiceConnection;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import black.android.app.BRIServiceConnectionO;

/**
 * updated by alex5402 on 4/2/21.
 * * ∧＿∧
 * (`･ω･∥
 * 丶　つ０
 * しーＪ
 * TFNQw5HgWUS33Ke1eNmSFTwoQySGU7XNsK (USDT TRC20)
 */
public class ServiceConnectionDelegate extends IServiceConnection.Stub {
    private static final Map<IBinder, ServiceConnectionDelegate> sServiceConnectDelegate = new HashMap<>();
    private final IServiceConnection mConn;
    private final ComponentName mComponentName;

    private ServiceConnectionDelegate(IServiceConnection mConn, ComponentName targetComponent) {
        this.mConn = mConn;
        this.mComponentName = targetComponent;
    }

    public static ServiceConnectionDelegate getDelegate(IBinder iBinder) {
        return sServiceConnectDelegate.get(iBinder);
    }

    public static IServiceConnection createProxy(IServiceConnection base, Intent intent) {
        final IBinder iBinder = base.asBinder();
        ServiceConnectionDelegate delegate = sServiceConnectDelegate.get(iBinder);
        if (delegate == null) {
            try {
                iBinder.linkToDeath(new IBinder.DeathRecipient() {
                    @Override
                    public void binderDied() {
                        sServiceConnectDelegate.remove(iBinder);
                        iBinder.unlinkToDeath(this, 0);
                    }
                }, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            delegate = new ServiceConnectionDelegate(base, intent.getComponent());
            sServiceConnectDelegate.put(iBinder, delegate);
        }
        return delegate;
    }

    @Override
    public void connected(ComponentName name, IBinder service) throws RemoteException {
        connected(name, service, false);
    }

    public void connected(ComponentName name, IBinder service, boolean dead) throws RemoteException {
        // Always use Oreo+ API on API 29+
        BRIServiceConnectionO.get(mConn).connected(mComponentName, service, dead);
    }

    /**
     * Transaction codes for IServiceConnection.connected() methods.
     *
     * Pre-Oreo: connected(ComponentName, IBinder) = FIRST_CALL_TRANSACTION + 0
     * Oreo+: connected(ComponentName, IBinder, boolean) = FIRST_CALL_TRANSACTION + 1
     * Android 16: connected(ComponentName, IBinder, IBinderSession, boolean) = FIRST_CALL_TRANSACTION + 2
     */
    private static final int TRANSACTION_connected = FIRST_CALL_TRANSACTION;
    private static final int TRANSACTION_connected_dead = FIRST_CALL_TRANSACTION + 1;
    private static final int TRANSACTION_connected_session = FIRST_CALL_TRANSACTION + 2;

    /**
     * Override onTransact to handle Android 16's new IServiceConnection.connected() signature
     * that includes IBinderSession parameter.
     *
     * Android 16 added: connected(ComponentName, IBinder, IBinderSession, boolean)
     * We need to intercept this transaction before the stub tries to dispatch to
     * the abstract method that doesn't exist in our implementation.
     */
    @Override
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        try {
            switch (code) {
                case TRANSACTION_connected: {
                    // Original: connected(ComponentName, IBinder)
                    data.enforceInterface(DESCRIPTOR);
                    ComponentName componentName = null;
                    if (data.readInt() != 0) {
                        componentName = ComponentName.CREATOR.createFromParcel(data);
                    }
                    IBinder service = data.readStrongBinder();
                    connected(componentName, service);
                    return true;
                }
                case TRANSACTION_connected_dead: {
                    // Oreo+: connected(ComponentName, IBinder, boolean)
                    data.enforceInterface(DESCRIPTOR);
                    ComponentName componentName = null;
                    if (data.readInt() != 0) {
                        componentName = ComponentName.CREATOR.createFromParcel(data);
                    }
                    IBinder service = data.readStrongBinder();
                    boolean dead = data.readInt() != 0;
                    connected(componentName, service, dead);
                    return true;
                }
                case TRANSACTION_connected_session: {
                    // Android 16+: connected(ComponentName, IBinder, IBinderSession, boolean)
                    Log.d("ServiceConnDelegate", "Handling Android 16+ IBinderSession transaction");
                    data.enforceInterface(DESCRIPTOR);
                    ComponentName componentName = null;
                    if (data.readInt() != 0) {
                        componentName = ComponentName.CREATOR.createFromParcel(data);
                    }
                    IBinder service = data.readStrongBinder();
                    // Read and discard IBinderSession (it's an IBinder interface we don't use)
                    data.readStrongBinder(); // IBinderSession
                    boolean dead = data.readInt() != 0;
                    connected(componentName, service, dead);
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        } catch (AbstractMethodError e) {
            // Fallback: if we still get AbstractMethodError, try to handle it gracefully
            Log.e("ServiceConnDelegate", "AbstractMethodError in onTransact, code=" + code, e);
            // Try to parse as the new format
            try {
                data.setDataPosition(0);
                data.enforceInterface(DESCRIPTOR);
                ComponentName componentName = null;
                if (data.readInt() != 0) {
                    componentName = ComponentName.CREATOR.createFromParcel(data);
                }
                IBinder service = data.readStrongBinder();
                // Try to read potential IBinderSession
                IBinder session = data.readStrongBinder();
                boolean dead = false;
                if (data.dataAvail() > 0) {
                    dead = data.readInt() != 0;
                }
                connected(componentName, service, dead);
                return true;
            } catch (Exception ex) {
                Log.e("ServiceConnDelegate", "Failed to handle transaction fallback", ex);
                throw e;
            }
        }
    }
}
