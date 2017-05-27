package com.example.hbl.kotlin.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.example.hbl.kotlin.CodeReadApplication;


public class DeviceUtils {

    public static int getStatusBarHeight() {
        int result = 0;
        int resId = CodeReadApplication.Instance
                .getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result =  CodeReadApplication.Instance
                    .getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

    public static float dpToPx(Context context, float dpValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, metrics);
    }

}
