package com.example.obd;
import android.app.Application;

import com.example.obd.MainActivity.MainPeace;

public class Myapp extends Application {
    public MainPeace act=null;
    @Override
    public void onCreate() {
        super.onCreate();
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
    }
}