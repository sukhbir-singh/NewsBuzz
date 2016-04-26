package com.newsbuzz;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class Utils {
    public static float convertDpToPixel(float dp){
        Context context=MyApplication.getAppContext();
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }
    public static String getTimestamp(String pubDate){
        DateTimeFormatter formatter= DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss z");
        DateTime dateTime=formatter.parseDateTime(pubDate);
        DateTime start=new DateTime(DateTimeZone.forID("Asia/Kolkata"));
        DateTime end=new DateTime(dateTime.getMillis(),DateTimeZone.forID("Asia/Kolkata"));
        Duration duration=new Duration(start,end);
       return getTime(Math.abs(duration.getStandardSeconds()));
    }
    private static String getTime(long time){
        long minute=time/60l;
        long hour=minute/60l;
        long day=hour/24l;
        if(time<60l)
        return time+" seconds ago";
        else if (minute<60l)
            return minute+" minute ago";
        else if(hour<24l)
            return hour+" hour ago";
        else
            return day+"day ago";
    }
}
