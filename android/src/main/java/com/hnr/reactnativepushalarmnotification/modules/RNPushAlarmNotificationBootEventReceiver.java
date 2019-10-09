package com.hnr.reactnativepushalarmnotification.modules;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Set;

import static com.hnr.reactnativepushalarmnotification.modules.RNPushAlarmNotification.LOG_TAG;

/**
 * Set alarms for scheduled notification after system reboot.
 */
public class RNPushAlarmNotificationBootEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(LOG_TAG, "RNPushAlarmNotificationBootEventReceiver loading scheduled notifications");

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(RNPushAlarmNotificationHelper.PREFERENCES_KEY, Context.MODE_PRIVATE);
            Set<String> ids = sharedPreferences.getAll().keySet();

            Application applicationContext = (Application) context.getApplicationContext();
            RNPushAlarmNotificationHelper rnPushNotificationHelper = new RNPushAlarmNotificationHelper(applicationContext);

            for (String id : ids) {
                try {
                    String notificationAttributesJson = sharedPreferences.getString(id, null);
                    if (notificationAttributesJson != null) {
                        RNPushAlarmNotificationAttributes notificationAttributes = RNPushAlarmNotificationAttributes.fromJson(notificationAttributesJson);

                        if (notificationAttributes.getFireDate() < System.currentTimeMillis()) {
                            rnPushNotificationHelper.openApplication();
                        } else {
                            Log.i(LOG_TAG, "RNPushAlarmNotificationBootEventReceiver: Scheduling notification for " +
                                    notificationAttributes.getId());
                            rnPushNotificationHelper.sendNotificationScheduledCore(notificationAttributes.toBundle());
                        }
                    }
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Problem with boot receiver loading notification " + id, e);
                }
            }
        }
    }
}
