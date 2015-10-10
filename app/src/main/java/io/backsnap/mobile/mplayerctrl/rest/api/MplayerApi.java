package io.backsnap.mobile.mplayerctrl.rest.api;


import retrofit.http.GET;
import retrofit.http.Path;

public interface MplayerApi {
    @GET("/mplayer/{cmd}")
    String cmd(@Path("cmd") final String cmd);
}
