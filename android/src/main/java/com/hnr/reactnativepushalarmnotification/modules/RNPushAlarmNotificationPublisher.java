package com.hnr.reactnativepushalarmnotification.modules;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static com.hnr.reactnativepushalarmnotification.modules.RNPushAlarmNotification.LOG_TAG;

public class RNPushAlarmNotificationPublisher extends BroadcastReceiver {
    final static String NOTIFICATION_ID = "notificationId";

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        long currentTime = System.currentTimeMillis();

        Log.i(LOG_TAG, "NotificationPublisher: Prepare To Publish: " + id + ", Now Time: " + currentTime);

        Application applicationContext = (Application) context.getApplicationContext();

        new RNPushAlarmNotificationHelper(applicationContext)
                .sendToNotificationCentre(intent.getExtras());
    }
}
