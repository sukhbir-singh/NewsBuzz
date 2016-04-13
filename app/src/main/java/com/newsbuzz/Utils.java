package com.newsbuzz;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

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
    public static int getTimestamp(String pubDate){
        DateTimeFormatter formatter= DateTimeFormat.forPattern("E, d MMM yyyy HH:mm:ss z");
        DateTime dateTime=formatter.parseDateTime(pubDate);
        DateTime start=new DateTime(DateTimeZone.forID("Asia/Kolkata"));
        DateTime end=new DateTime(dateTime.getMillis(),DateTimeZone.forID("Asia/Kolkata"));
        Duration duration=new Duration(start,end);
       return (int) Math.abs(duration.getStandardSeconds());
    }
    public  static String getTime(int time){
        int minute=time/60;
        int hour=minute/60;
        int day=hour/24;
        if(time<60)
        return time+" seconds ago";
        else if (minute<60)
            return minute+" minute ago";
        else if(hour<24)
            return hour+" hour ago";
        else
            return day+"day ago";
    }
}
