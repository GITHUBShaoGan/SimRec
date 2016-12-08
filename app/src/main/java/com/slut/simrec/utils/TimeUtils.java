package com.slut.simrec.utils;

import com.slut.simrec.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 七月在线科技 on 2016/12/8.
 */

public class TimeUtils {

    private static final String YEAR_DATE = "yyyy-MM-dd";
    private static final String MONTH_DAY = "MM-dd";

    public static String stamp2Str(long stamp, String text) {
        SimpleDateFormat format = new SimpleDateFormat(text);
        Date date = new Date(stamp);
        return format.format(date);
    }

    public static String calInterval(long startTime, long endTime) {
        if (endTime < startTime) {
            return ResUtils.getString(R.string.error_time_end_below_start);
        }
        long interval = endTime - startTime;
        long oneSecond = 1 * 1000;
        long oneMinute = 60 * oneSecond;
        long oneHour = 60 * oneMinute;
        long oneDay = 24 * oneHour;
        long tenDay = 10 * oneDay;
        long oneYear = 365 * oneDay;
        if (interval < oneSecond) {
            return ResUtils.getString(R.string.time_interval_just_now);
        } else if (interval < oneMinute) {
            long second = interval / oneSecond;
            if (second == 1) {
                return second + ResUtils.getString(R.string.time_interval_second_ago);
            }
            return second + ResUtils.getString(R.string.time_interval_seconds_ago);
        } else if (interval < oneHour) {
            long minute = interval / oneMinute;
            if (minute == 1) {
                return minute + ResUtils.getString(R.string.time_interval_minute_ago);
            }
            return minute + ResUtils.getString(R.string.time_interval_minutes_ago);
        } else if (interval < oneDay) {
            long hour = interval / oneHour;
            if (hour == 1) {
                return hour + ResUtils.getString(R.string.time_interval_hour_ago);
            }
            return hour + ResUtils.getString(R.string.time_interval_hours_ago);
        } else if (interval < tenDay) {
            long day = interval / oneDay;
            if (day == 1) {
                return day + ResUtils.getString(R.string.time_interval_day_ago);
            }
            return day + ResUtils.getString(R.string.time_interval_days_ago);
        } else if (interval < oneYear) {
            return stamp2Str(startTime, MONTH_DAY);
        } else {
            return stamp2Str(startTime, YEAR_DATE);
        }
    }

}
