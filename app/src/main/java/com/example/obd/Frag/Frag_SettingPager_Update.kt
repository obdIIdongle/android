package com.example.obd.Frag

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.view.KeyEvent
import com.example.obd.MainPeace
import com.orange.blelibrary.blelibrary.RootFragement
import com.orange.obd.R
import kotlinx.android.synthetic.main.fragment_update.view.*


class Frag_SettingPager_Update : RootFragement(R.layout.fragment_update) {

    lateinit var mainPeace: MainPeace

    override fun ViewInit() {
        mainPeace=activity!! as MainPeace

        //rootview.textView4.text = getVersionCode(mainPeace).toString()
        rootview.textView71.text = "USB TPMS " + getVersionCode(mainPeace).toString() + " \nOrange Electronic\n25.1MB"
    }

    override fun dispatchKeyEvent(event: KeyEvent?) {

    }

    /**
     * 获取版本号
     *
     * @param context 上下文
     *
     * @return 版本号
     */
    fun getVersionCode(context: Context): Int {

        //获取包管理器
        val pm = context.getPackageManager()
        //获取包信息
        try {
            val packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            //返回版本号
            return packageInfo.versionCode
        } catch (e:PackageManager.NameNotFoundException) {
            e.printStackTrace();
        }

        return 0;

    }

}
