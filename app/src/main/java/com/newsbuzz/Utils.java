package com.newsbuzz;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;


public class Utils {
    public static float convertDpToPixel(float dp){
        Context context=MyApplication.getAppContext();
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }
}
