package com.codepath.thenewyorktimes.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class Utils {

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    public static Typeface getCheltenhamFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/cheltenham.ttf");
    }

    public static Typeface getGeorgiaFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/georgia.ttf");
    }
}