package com.example.goobee_yuer.testcamera;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.example.goobee_yuer.testcamera.utils.FileUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;

/**
 * Created by BB on 2018/1/8.
 */

public class MyApp extends Application {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        CrashReport.initCrashReport(getApplicationContext(), "d5ce68976c", true);

        //设置该CrashHandler为程序的默认处理器
        UnCeHandler catchExcep = new UnCeHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);

//        File eis = new File(Environment.getExternalStorageDirectory().getPath() + "/test");
//        try {
//            if (!eis.exists()) {
//                eis.mkdir();
//            }
//        } catch (Exception e) {
//
//        }
        FileUtils.crSDFile("test","img");
        FileUtils.crSDFile("test","video");
    }

    public static Context getContext() {
        return context;
    }


}
