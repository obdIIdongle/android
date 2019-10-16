package com.example.obd;
import android.app.Application;

import com.example.obd.MainActivity.MainPeace;
import com.facebook.drawee.backends.pipeline.Fresco;

import static com.orange.blelibrary.blelibrary.tool.FormatConvert.StringHexToByte;

public class Myapp extends Application {
    public MainPeace act=null;
    @Override
    public void onCreate() {
        super.onCreate();
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
        Fresco.initialize(this);
    }
    public static byte[] GetCrc(String a){
        byte command[]=StringHexToByte(a);
        int xor=0;
        for (int i=0;i<command.length-2;i++){
            xor=xor ^ command[i];
        }
        command[command.length-2]=(byte)xor;
        return command;
    }
}