package top.niunaijun.blackbox.proxy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;

import top.niunaijun.blackbox.BlackBoxCore;
import top.niunaijun.blackbox.utils.Slog;

/**
 * Transparent activity to handle VPN permission request flow.
 * Android requires VpnService.prepare() to be called before establish().
 */
public class VpnPermissionActivity extends Activity {
    private static final String TAG = "VpnPermissionActivity";
    private static final int VPN_REQUEST_CODE = 1001;

    // Prevent duplicate permission activities from multiple processes
    private static volatile boolean sPermissionActivityActive = false;
    private static final Object sActivityLock = new Object();

    /**
     * Request VPN permission from the user
     * @param context Application context
     */
    public static void requestVpnPermission(Context context) {
        synchronized (sActivityLock) {
            if (sPermissionActivityActive) {
                Slog.d(TAG, "Permission activity already active, skipping duplicate");
                return;
            }
            sPermissionActivityActive = true;
        }

        Intent intent = new Intent(context, VpnPermissionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if VPN permission is already granted
        Intent prepareIntent = VpnService.prepare(this);
        if (prepareIntent == null) {
            // Permission already granted
            onPermissionGranted();
        } else {
            // Request permission from user
            startActivityForResult(prepareIntent, VPN_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VPN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                onPermissionGranted();
            } else {
                Slog.w(TAG, "VPN permission denied by user");
            }
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (sActivityLock) {
            sPermissionActivityActive = false;
        }
    }

    private void onPermissionGranted() {
        Slog.d(TAG, "VPN permission granted");
        BlackBoxCore.get().startVpnServiceWithPermission();
    }
}
