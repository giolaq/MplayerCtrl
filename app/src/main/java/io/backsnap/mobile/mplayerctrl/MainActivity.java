package io.backsnap.mobile.mplayerctrl;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;
import com.estimote.sdk.eddystone.Eddystone;

import org.w3c.dom.Text;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.backsnap.mobile.mplayerctrl.rest.MplayerService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getCanonicalName();

    @Bind(R.id.toolbar)Toolbar toolbar;

    @Bind(R.id.info)TextView info;

    private Region region;
    private BeaconManager beaconManager;

    private static final int UNKNOW = -1;
    private static final int ENTER = 0;
    private static final int EXIT = 1;

    private static int status = UNKNOW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        info.setText("UNKNOWN");
        status = UNKNOW;

        region = new Region("regionId", UUID.fromString("b9407f30-f5f8-466e-aff9-25556b57fe6d"), 59584, 50675);

        beaconManager = new BeaconManager(this);

        beaconManager.setBackgroundScanPeriod(TimeUnit.MILLISECONDS.toMillis(2), 0);
        beaconManager.setForegroundScanPeriod(TimeUnit.MILLISECONDS.toMillis(2), 0);

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> beacons) {
                Log.i(TAG, "onEnteredRegion");
                info.setText("ENTER");

                if (status != ENTER) {
                    status = ENTER;

                    mplayerCmd();
                }
            }

            @Override
            public void onExitedRegion(Region region) {
                Log.i(TAG, "onExitedRegion");
                info.setText("EXIT");

                if (status != EXIT) {
                    status = EXIT;

                    mplayerCmd();
                }
            }
        });

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                Log.i(TAG, "onBeaconsDiscovered");

                for (final Beacon  beacon : list) {
                    Log.v(TAG, "beacon: " + beacon);
                    Log.d(TAG, String.format("beacon accuracy: %.2fm", Utils.computeAccuracy(beacon)));
                }
            }
        });
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                Log.i(TAG, "onServiceReady");

                beaconManager.startMonitoring(region);
                beaconManager.startRanging(region);
            }
        });

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");

        beaconManager.stopMonitoring(region);
        beaconManager.stopRanging(region);

        beaconManager.disconnect();

        super.onDestroy();
    }

    @OnClick(R.id.fab)
    public void onClick(View v) {
        Log.i(TAG, "onClick");

        mplayerCmd();
    }

    private void mplayerCmd() {
        Log.i(TAG, "mplayerCmd");

        final Intent mplayerService = new Intent(this, MplayerService.class);
        startService(mplayerService);
    }
}
