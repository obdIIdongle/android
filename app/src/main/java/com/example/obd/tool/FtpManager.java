package com.example.obd.tool;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import com.example.obd.HttpCommand.SensorRecord;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FtpManager {
    public static boolean Internet=false;
    public static String ip=(Build.VERSION.SDK_INT > Build.VERSION_CODES.M) ? "35.240.51.141:21":"61.221.15.194:21/OrangeTool";
    private static String encoding = System.getProperty("file.encoding");
    public static String username="orangerd";
    public static String password=(Build.VERSION.SDK_INT > Build.VERSION_CODES.M) ? "orangetpms(~2":"orangetpms";
    public static String donloads19="nodata";
    public static boolean downFile(String url, int port, String username,
                                   String password, String remotePath, String fileName,
                                   String localPath) {
        FTPClient ftpClient = new FTPClient();
        boolean result = false;
        try {
            int reply;
            ftpClient.setControlEncoding(encoding);
            /*
             * 為了上傳和下載中文檔案，有些地方建議使用以下兩句代替
             * new String(remotePath.getBytes(encoding),"iso-8859-1")轉碼。
             * 經過測試，通不過。
             */
//   FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
//   conf.setServerLanguageCode("zh");
            ftpClient.connect(url, port);
// 如果採用預設埠，可以使用ftp.connect(url)的方式直接連線FTP伺服器
            ftpClient.login(username, password);// 登入
// 設定檔案傳輸型別為二進位制
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
// 獲取ftp登入應答程式碼
            reply = ftpClient.getReplyCode();
// 驗證是否登陸成功
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                System.err.println("FTP server refused connection.");
                return result;
            }
// 轉移到FTP伺服器目錄至指定的目錄下
            ftpClient.changeWorkingDirectory(new String(remotePath.getBytes(encoding), StandardCharsets.ISO_8859_1));
// 獲取檔案列表
            FTPFile[] fs = ftpClient.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath);
                    OutputStream is = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }
            ftpClient.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }
    public static boolean DownMMy( Activity activity) {
        try {
            File DB_PATH = activity.getDatabasePath("usb_tx_mmy.db");
            File file=new File(DB_PATH.getPath().replace("usb_tx_mmy.db",""));
            if(!file.exists()){ if(!file.mkdirs()){return false;}
            }
            return    doloadmmy(DB_PATH.getPath(),activity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean DownS19(String Filename,Activity activity){
        return donloads19(Filename,activity);
    }

    public static String GetS19Name(String name){
        try{
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect("35.240.51.141",21);
            ftpClient.login(username, "orangetpms(~2");
            FTPFile[] files=ftpClient.listFiles("Drive/OBD DONGLE/"+name);
            if(files.length>0){Log.d("filename",files[0].getName());
                SensorRecord.SensorCode_Version= files[0].getName();
                return files[0].getName(); }
            return "nodata";
        }catch (Exception e){e.printStackTrace(); return "nodata";}
    }

    public static boolean donloads19(String name,Activity activity){
            try{
                donloads19=GetS19Name(name);
                InputStream is=Internet ? new URL("ftp://"+username+":"+password+"@"+ip+"/Drive/OBD DONGLE/"+name+"/"+donloads19).openStream():activity.getAssets().open("TO001.srec");
                FileOutputStream fos=new FileOutputStream(activity.getApplicationContext().getFilesDir().getPath()+"/"+name+".s19");
                int bufferSize = 8192;
                byte[] buf = new byte[bufferSize];
                while(true){
                    int read=is.read(buf);
                    if(read==-1){  break;}
                    fos.write(buf, 0, read);
                }
                is.close();
                fos.close();
                return true;
            }catch (Exception e){e.printStackTrace(); return false;}
    }
//    F5FE14000FD30300000000B30300000001D2780A
//    F5FE14000FC30300000000A30300000001FFAC0A
//    F5FE14000FD30300000000B30300000001D2780A



    public static boolean doloadmmy(String fileanme,Activity activity){try {
        SharedPreferences profilePreferences = activity.getSharedPreferences("Setting", Context.MODE_PRIVATE);
        String mmyname =Internet ? mmyname():"mmy.db";
        if (profilePreferences.getString("mmyname", "").equals(mmyname)) {
            return true;
        }
        URL url = new URL("ftp://" + username + ":" + password + "@" + ip + "/Database/MMY/EU/" + mmyname);
        Log.d("path", "ftp://" + username + ":" + password + "@" + ip + "/Database/MMY/EU/" + mmyname);
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
    }catch (Exception e){e.printStackTrace();return false;}

    }

    public static String mmyname(){
        try{
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect("35.240.51.141",21);
            ftpClient.login(username, "orangetpms(~2");
            FTPFile[] files=ftpClient.listFiles("Database/MMY/EU");
            if(files.length>0){Log.d("filename",files[0].getName());
                SensorRecord.DB_Version=files[0].getName();
                return files[0].getName(); }
            return "nodata";
        }catch (Exception e){e.printStackTrace(); return "nodata";}
    }
}
