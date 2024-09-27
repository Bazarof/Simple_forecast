package com.example.simple_forecast;

import android.content.Context;
import android.view.View;

public class ScreenScale {
    public static int getDp(Context context, int pixels)
    {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pixels * scale + 0.5f);
    }
}
