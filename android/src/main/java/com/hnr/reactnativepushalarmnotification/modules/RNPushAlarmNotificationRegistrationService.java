package com.hnr.reactnativepushalarmnotification.modules;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import static com.hnr.reactnativepushalarmnotification.modules.RNPushAlarmNotification.LOG_TAG;

public class RNPushAlarmNotificationRegistrationService extends IntentService {

    private static final String TAG = "RNPushAlarmNotification";

    public RNPushAlarmNotificationRegistrationService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            String SenderID = intent.getStringExtra("senderID");
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(SenderID,
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            sendRegistrationToken(token);
        } catch (Exception e) {
            Log.e(LOG_TAG, TAG + " failed to process intent " + intent, e);
        }
    }

    private void sendRegistrationToken(String token) {
        Intent intent = new Intent(this.getPackageName() + ".RNPushAlarmNotificationRegisteredToken");
        intent.putExtra("token", token);
        sendBroadcast(intent);
    }
}
