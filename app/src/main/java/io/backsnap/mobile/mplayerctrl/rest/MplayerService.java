package io.backsnap.mobile.mplayerctrl.rest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MplayerService extends IntentService {
    private static final String TAG = MplayerService.class.getCanonicalName();

    public MplayerService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent");

        new RestClient().API.cmd("pause");
    }
}
