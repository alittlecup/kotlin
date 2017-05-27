package com.example.hbl.kotlin;

import android.app.Application;

/**
 * Created by hbl on 2017/5/27.
 */

public class CodeReadApplication extends Application {
    public static Application Instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Instance = this;
    }
}
