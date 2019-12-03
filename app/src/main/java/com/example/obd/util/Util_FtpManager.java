package com.example.obd.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Util_FtpManager {
    public static boolean Internet = true;
    public static String ip = (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) ? "35.240.51.141:21" : "61.221.15.194:21/OrangeTool";
    private static String encoding = System.getProperty("file.encoding");
    public static String username = "orangerd";
    public static String password = (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) ? "orangetpms(~2" : "orangetpms";
    public static String donloads19 = "OBDB_APP_TO001_191030";

    public static boolean DownMMy(Activity activity) {
        try {
            File DB_PATH = activity.getDatabasePath("usb_tx_mmy.db");
            File file = new File(DB_PATH.getPath().replace("usb_tx_mmy.db", ""));
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    return false;
                }
            }
            return doloadmmy(DB_PATH.getPath(), activity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean DownS19(String Filename, Activity activity) {
        return donloads19(Filename, activity);
    }

    public static String GetS19Name(String name) {
        try {
            URL url = new URL("http://bento2.orange-electronic.com/Orange%20Cloud/Drive/OBD%20DONGLE/" + name + "/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line = null;
            StringBuffer strBuf = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                strBuf.append(line);
            }
            String[] arg = strBuf.toString().split(" HREF=\"");
            for (String a : arg) {
                if (a.contains(".srec")) {
                    return (a.substring(a.indexOf(">") + 1, a.indexOf("<")));
                }
            }
            return "nodata";
        } catch (Exception e) {
            e.printStackTrace();
            return "nodata";
        }
    }

    public static boolean donloads19(String name, Activity activity) {
        if(!Internet){return true;}
        try {
            donloads19 = GetS19Name(name);
            InputStream is = Internet ? new URL("http://bento2.orange-electronic.com/Orange%20Cloud/Drive/OBD%20DONGLE/" + name + "/" + donloads19).openStream() : activity.getAssets().open("TO001.srec");
            FileOutputStream fos = new FileOutputStream(activity.getApplicationContext().getFilesDir().getPath() + "/" + name + ".s19");
            int bufferSize = 8192;
            byte[] buf = new byte[bufferSize];
            while (true) {
                int read = is.read(buf);
                if (read == -1) {
                    break;
                }
                fos.write(buf, 0, read);
            }
            is.close();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean doloadmmy(String fileanme, Activity activity) {
        try {
            SharedPreferences profilePreferences = activity.getSharedPreferences("Frag_SettingPager_Setting", Context.MODE_PRIVATE);
            String mmyname = Internet ? mmyname() : "mmy.db";
            Log.d("mmyname", mmyname);
            if (profilePreferences.getString("mmyname", "").equals(mmyname)) {
                return true;
            }
            URL url = new URL("http://bento2.orange-electronic.com/Orange%20Cloud/Database/MMY/EU/" + mmyname);
            Log.d("path", "http://bento2.orange-electronic.com/Orange%20Cloud/Database/MMY/EU/" + mmyname);
            InputStream is = Internet ? url.openStream() : activity.getAssets().open("mmy.db");
            FileOutputStream fos = new FileOutputStream(fileanme);
            int bufferSize = 8192;
            byte[] buf = new byte[bufferSize];
            while (true) {
                int read = is.read(buf);
                if (read == -1) {
                    break;
                }
                fos.write(buf, 0, read);
            }
            is.close();
            fos.close();
            File f = new File(fileanme);

            if (f.exists() && f.isFile()) {
                Log.d("path", "" + f.length());
            } else {
                Log.d("path", "file doesn't exist or is not a file");
            }
            profilePreferences.edit().putString("mmyname", mmyname).commit();
            return f.length() != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static String mmyname() {
        try {
            URL url = new URL("http://bento2.orange-electronic.com/Orange%20Cloud/Database/MMY/EU/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line = null;
            StringBuffer strBuf = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                strBuf.append(line);
            }
            String[] arg = strBuf.toString().split("HREF=\"");
            for (String a : arg) {
                if (a.contains(".db")) {
                    return (a.substring(a.indexOf(">") + 1, a.indexOf("<")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "nodata";
    }
}
