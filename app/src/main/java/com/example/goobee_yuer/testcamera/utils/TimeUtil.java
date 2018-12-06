package com.example.goobee_yuer.testcamera.utils;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 */

public class TimeUtil {

    public static final String TAG = "TimeUtils";


    public static String getSystemTime() {
        return (String) DateFormat.format(("yyyy-MM-dd   HH:mm "), new Date());
    }

    public static int getHours() {
//        return (String) DateFormat.format(("hh"), new Date());
        final Calendar mCalendar = Calendar.getInstance();
        return mCalendar.get(Calendar.HOUR_OF_DAY);
    }


    /**
     * 调此方法输入所要转换的时间输入例如（"2014-06-14-16-09-00"）返回时间戳
     *
     * @param time
     * @return
     */
    public static Date dataOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss",
                Locale.CHINA);
        Date date = null;
        try {
            date = sdr.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
