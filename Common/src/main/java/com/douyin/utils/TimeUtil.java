package com.douyin.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Cra2iTeT
 * @date 2022/10/13 17:01
 */
public class TimeUtil {

    private static final String TIMEZONE = "GMT+8";

    private static final String DATEFORMAT = "yyyy-MM-dd";

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATEFORMAT);

    public static Date getDateNow4Date() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        return calendar.getTime();
    }

    public static String getDateNow4String() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        return simpleDateFormat.format(calendar.getTime());
    }

    public static int getDayOfMonth() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static Date getDateWithBf4Date(int bfDay) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        calendar.add(Calendar.DATE, -bfDay);
        return calendar.getTime();
    }

    public static String getDateWithBf4String(int bfDay) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        calendar.add(Calendar.DATE, -bfDay);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static int getHourWithBf4(int bfHour) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        calendar.add(Calendar.HOUR_OF_DAY, -bfHour);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getHourOfDay() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static String getDateWithFormat(String format) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        SimpleDateFormat setDateFormat = new SimpleDateFormat(format);
        return setDateFormat.format(calendar.getTime());
    }

    public static Long getTimeInMillis4Long() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        return calendar.getTimeInMillis();
    }

    public static String getTimeInMillis4Str() {
        return String.valueOf(getTimeInMillis4Long());
    }
}
