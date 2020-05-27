package com.example.listoftasks;

import android.app.Application;

import com.example.listoftasks.di.KoinModulesKt;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        KoinModulesKt.startMyKoin(this.getApplicationContext());
    }
}
