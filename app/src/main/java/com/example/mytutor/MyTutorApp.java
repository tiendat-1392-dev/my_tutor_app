package com.example.mytutor;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.mytutor.utils.SharedPreferenceUtil;

public class MyTutorApp extends Application {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferenceUtil.init(this);
    }
}
