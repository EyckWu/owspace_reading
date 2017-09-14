package com.eyck.fxreading.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Eyck on 2017/8/30.
 */

public class TimeUtil {
    public static long getCurrentSeconds(){
        long ls = System.currentTimeMillis()/1000;
        return ls;
    }

    public static String[] getCalendarShowTime(long paramLong)
    {
        String[] localObject;
        String str = new SimpleDateFormat("yyyy:MMM:d", Locale.ENGLISH).format(new Date(paramLong));
        try
        {
            String[] arrayOfString = str.split(":");
            localObject = arrayOfString;
            if ((localObject != null) && (localObject.length == 3));
            return localObject;
        }
        catch (Exception localException)
        {
            while (true)
                localException.printStackTrace();
        }
    }

    public static String[] getCalendarShowTime(String paramString)
    {
        try {
            long l = Long.valueOf(paramString);
            Calendar localCalendar = Calendar.getInstance();
            localCalendar.setTimeInMillis(1000L * l);
            return getCalendarShowTime(localCalendar.getTimeInMillis());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getDate(String formate){
        String str = new SimpleDateFormat(formate, Locale.ENGLISH).format(new Date());
        return str;
    }

    public static String formatDuration(int duration){
        duration /= 1000; // milliseconds into seconds
        int minute = duration / 60;
        int hour = minute / 60;
        minute %= 60;
        int second = duration % 60;
        if (hour != 0)
            return String.format("%2d:%02d:%02d", hour, minute, second);
        else
            return String.format("%02d:%02d", minute, second);
    }
}
