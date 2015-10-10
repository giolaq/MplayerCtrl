package io.backsnap.mobile.mplayerctrl;

import android.app.Application;
import android.util.Log;

import com.estimote.sdk.EstimoteSDK;

public class App extends Application {
    private static final String TAG = App.class.getCanonicalName();

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");

        EstimoteSDK.initialize(this, "safeback-ckq", "0254ab34de3f9ac765bfc32b18954660");
        EstimoteSDK.enableDebugLogging(true);

        super.onCreate();
    }
}
