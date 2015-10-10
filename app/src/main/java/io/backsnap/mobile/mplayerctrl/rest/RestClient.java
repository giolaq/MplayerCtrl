package io.backsnap.mobile.mplayerctrl.rest;

import android.util.Log;

import io.backsnap.mobile.mplayerctrl.rest.api.MplayerApi;
import retrofit.RestAdapter;

public class RestClient {
    private static final String TAG = RestClient.class.getCanonicalName();

    private static final String MPLAYERAPI_URL = "http://10.10.1.120:8080/";

    public MplayerApi API;

    public RestClient() {
        Log.i(TAG, "RestClient");

        final RestAdapter.Builder restBuilder = new RestAdapter.Builder();
        restBuilder.setLogLevel(RestAdapter.LogLevel.FULL);
        restBuilder.setEndpoint(MPLAYERAPI_URL);

        API = restBuilder.build().create(MplayerApi.class);
    }
}
