package com.example.android_final_project;

import android.app.Application;

import com.example.android_final_project.Utilities.DbOperations;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DbOperations.init(this);
    }
}
