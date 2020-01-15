package com.hnr.reactnativepushalarmnotification.modules;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class RNCallMethods {

    private ReactContext mReactContext;


    public RNCallMethods(ReactContext reactContext) {
        this.mReactContext = reactContext;
    }

    private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    public void callEvent(){

        WritableMap params = Arguments.createMap();
        params.putString("eventProperty", "success");

        sendEvent(mReactContext, "RouteTo", params);
    }

    public void callTimerEvent(long seconds) {
        WritableMap params = Arguments.createMap();
        params.putString("timerEventProperty", String.valueOf(seconds));

        sendEvent(mReactContext, "SetTimerTo", params);
    }
}
