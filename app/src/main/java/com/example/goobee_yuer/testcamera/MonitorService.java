package com.example.goobee_yuer.testcamera;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Goobee_yuer on 2018/11/15.
 */

public class MonitorService extends Service implements SurfaceHolder.Callback {
    public static final String ACTION_BACK_HOME = "com.byd.recorder.ACTION_BACK_HOME";
    public static final String ACTION_SHOW_RECORDER = "com.byd.recorder.ACTION_SHOW_RECORDER";

    private static final int NOTIFICATION_DI = 1234;

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private int screenWidth;
    private int screenHeight;
    private View mRecorderView;
    private SurfaceView mSurfaceView;

    /* RecorderManager manages the job of video recording */
    private RecorderManager mRecorderManager;
    private LocalBroadcastManager mLocalBroadcastManager;
    private LocalReceiver mLocalReceiver;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        super.onCreate();

        // Start foreground service to avoid unexpected kill
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Background Video Recorder")
                .setContentText("Click into the application")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0))
                .build();
        startForeground(NOTIFICATION_DI, notification);

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mRecorderView = LayoutInflater.from(this).inflate(R.layout.recorder_layout, null);
        mSurfaceView = (SurfaceView) mRecorderView.findViewById(R.id.sv_recorder);
        mSurfaceView.getHolder().addCallback(this);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.width = screenWidth;
        mLayoutParams.height = screenHeight;
        mWindowManager.addView(mRecorderView, mLayoutParams);
        registerLocalReceiver();
    }

    @Override
    public void onDestroy() {
        if (mWindowManager != null) {
            mWindowManager.removeView(mRecorderView);
        }
        if (mRecorderManager != null) {
            mRecorderManager.stop();
        }

        mLocalBroadcastManager.unregisterReceiver(mLocalReceiver);
        stopForeground(true);
        mRecorderManager.destroy();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void registerLocalReceiver() {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BACK_HOME);
        intentFilter.addAction(ACTION_SHOW_RECORDER);
        mLocalReceiver = new LocalReceiver();
        mLocalBroadcastManager.registerReceiver(mLocalReceiver, intentFilter);
    }

    public void hideRecorder(boolean isHide) {
        if (isHide) {
            mLayoutParams.width = 1;
            mLayoutParams.height = 1;
        } else {
            mLayoutParams.width = 480;//screenWidth;
            mLayoutParams.height = 600;//screenHeight;
        }
        mWindowManager.updateViewLayout(mRecorderView, mLayoutParams);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mRecorderManager = new RecorderManager();
        mRecorderManager.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_BACK_HOME)) {
                hideRecorder(true);
            }
            if (intent.getAction().equals(ACTION_SHOW_RECORDER)) {
                hideRecorder(false);
            }
        }
    }


}