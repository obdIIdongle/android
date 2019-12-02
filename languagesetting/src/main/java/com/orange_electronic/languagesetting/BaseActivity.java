package com.orange_electronic.languagesetting;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.orange_electronic.languagesetting.util.ViewUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        ActivityManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
        ActivityManager.getInstance().removeActivity(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = languageWork(newBase);
        super.attachBaseContext(context);

    }

    private Context languageWork(Context context) {
        // 8.0及以上使用createConfigurationContext设置configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return updateResources(context);
        } else {
            Locale locale = LanguageUtil.getLocale(context);
            LanguageUtil.updateLocale(context, locale);
            return context;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Context updateResources(Context context) {
        Resources resources = context.getResources();
        Locale locale = LanguageUtil.getLocale(context);
        if (locale==null) {
            return context;
        }
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLocales(new LocaleList(locale));
        return context.createConfigurationContext(configuration);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onStringEvent(ClassEvent event) {
        android.util.Log.d("test","LangeChangeActivity got message:" +  event);
        ViewUtil.updateViewLanguage(findViewById(android.R.id.content));
    }
}
