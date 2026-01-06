package black.android.app;

import top.niunaijun.blackreflection.annotation.BClassName;
import top.niunaijun.blackreflection.annotation.BField;

/**
 * Reflection interface for android.app.Notification.
 * Consolidated from NotificationO for API 29+ (notification channels).
 * Note: setLatestEventInfo was removed as it was deprecated in API 23.
 */
@BClassName("android.app.Notification")
public interface Notification {
    @BField
    String mChannelId();

    @BField
    String mGroupKey();
}
