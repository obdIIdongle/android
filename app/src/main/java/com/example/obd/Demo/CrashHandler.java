package com.example.obd.Demo;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
/**
 * UncaughtException處理類,當程式發生Uncaught異常的時候,有該類來接管程式,並記錄傳送錯誤報告.
 *
 * @author user
 *
 */
public class CrashHandler implements UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";
    //系統預設的UncaughtException處理類
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler例項
    private static CrashHandler INSTANCE = new CrashHandler();
    //程式的Context物件
    private Context mContext;
    //用來儲存裝置資訊和異常資訊
    private Map<String, String> infos = new HashMap<String, String>();
    //用於格式化日期,作為日誌檔名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    /** 保證只有一個CrashHandler例項 */
    private CrashHandler() {
    }
    /** 獲取CrashHandler例項 ,單例模式 */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }
    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
//獲取系統預設的UncaughtException處理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
//設定該CrashHandler為程式的預設處理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    /**
     * 當UncaughtException發生時會轉入該函式來處理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
//如果使用者沒有處理則讓系統預設的異常處理器來處理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
//退出程式
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }
    /**
     * 自定義錯誤處理,收集錯誤資訊 傳送錯誤報告等操作均在此完成.
     *
     * @param ex
     * @return true:如果處理了該異常資訊;否則返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
//使用Toast來顯示異常資訊
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程式出現異常,即將退出.", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
//收集裝置引數資訊
        collectDeviceInfo(mContext);
//儲存日誌檔案
        saveCrashInfo2File(ex);
        return true;
    }
    /**
     * 收集裝置引數資訊
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode+"";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName()  + " : "+   field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }
    /**
     * 儲存錯誤資訊到檔案中
     *
     * @param ex
     * @return 返回檔名稱,便於將檔案傳送到伺服器
     */
    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key  +"="+   value +  "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-"   +time+   "-"+   timestamp+   ".log";
            SharedPreferences sharedPreferences= mContext.getSharedPreferences("data", Context .MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            //步骤3：将获取过来的值放入文件
            editor.putString("crash", sb.toString());
            editor.commit();
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }
}