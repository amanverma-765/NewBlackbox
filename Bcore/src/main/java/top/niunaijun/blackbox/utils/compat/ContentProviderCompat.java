package top.niunaijun.blackbox.utils.compat;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;

/**
 * ContentProvider compatibility utilities.
 * Simplified for Android 10+ (API 29+).
 */
public class ContentProviderCompat {

    public static Bundle call(Context context, Uri uri, String method, String arg, Bundle extras, int retryCount) throws IllegalAccessException {
        ContentProviderClient client = acquireContentProviderClientRetry(context, uri, retryCount);
        try {
            if (client == null) {
                throw new IllegalAccessException();
            }
            return client.call(method, arg, extras);
        } catch (RemoteException e) {
            throw new IllegalAccessException(e.getMessage());
        } finally {
            releaseQuietly(client);
        }
    }


    private static ContentProviderClient acquireContentProviderClient(Context context, Uri uri) {
        try {
            return context.getContentResolver().acquireUnstableContentProviderClient(uri);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ContentProviderClient acquireContentProviderClientRetry(Context context, Uri uri, int retryCount) {
        ContentProviderClient client = acquireContentProviderClient(context, uri);
        if (client == null) {
            int retry = 0;
            long startTime = System.currentTimeMillis();
            long timeout = 2000; // 2 seconds total timeout

            while (retry < retryCount && client == null) {
                // Check if we've exceeded total timeout
                if (System.currentTimeMillis() - startTime > timeout) {
                    break;
                }

                SystemClock.sleep(200); // Reduced from 400ms to 200ms
                retry++;
                client = acquireContentProviderClient(context, uri);
            }
        }
        return client;
    }

    public static ContentProviderClient acquireContentProviderClientRetry(Context context, String name, int retryCount) {
        ContentProviderClient client = acquireContentProviderClient(context, name);
        if (client == null) {
            int retry = 0;
            long startTime = System.currentTimeMillis();
            long timeout = 2000; // 2 seconds total timeout

            while (retry < retryCount && client == null) {
                // Check if we've exceeded total timeout
                if (System.currentTimeMillis() - startTime > timeout) {
                    break;
                }

                SystemClock.sleep(200); // Reduced from 400ms to 200ms
                retry++;
                client = acquireContentProviderClient(context, name);
            }
        }
        return client;
    }

    private static ContentProviderClient acquireContentProviderClient(Context context, String name) {
        return context.getContentResolver().acquireUnstableContentProviderClient(name);
    }

    private static void releaseQuietly(ContentProviderClient client) {
        if (client != null) {
            try {
                client.close();
            } catch (Exception ignored) {
            }
        }
    }
}
