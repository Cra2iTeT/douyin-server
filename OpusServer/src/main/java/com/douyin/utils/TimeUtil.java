package com.douyin.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Cra2iTeT
 * @date 2022/10/13 17:01
 */
@Component
public class TimeUtil {

    private static final String TIMEZONE = "GMT+8";

    private static final String DATEFORMAT = "yyyy-MM-dd";

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATEFORMAT);

    public Date getDateNow4Date() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        return calendar.getTime();
    }

    public String getDateNow4String() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        return simpleDateFormat.format(calendar.getTime());
    }

    public int getDayOfMonth() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public Date getDateWithBf4Date(int bfDay) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        calendar.add(Calendar.DATE, -bfDay);
        return calendar.getTime();
    }

    public String getDateWithBf4String(int bfDay) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        calendar.add(Calendar.DATE, -bfDay);
        return simpleDateFormat.format(calendar.getTime());
    }

    public int getHourWithBf4(int bfHour) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        calendar.add(Calendar.HOUR_OF_DAY, -bfHour);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getHourOfDay() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public String getDateWithFormat(String format) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        SimpleDateFormat setDateFormat = new SimpleDateFormat(format);
        return setDateFormat.format(calendar.getTime());
    }

    public Long getTimeInMillis() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        return calendar.getTimeInMillis();
    }
}
