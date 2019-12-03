package com.example.obd.Dialog

import android.app.Dialog
import android.content.Context
import com.orange.blelibrary.blelibrary.CallBack.Dailog_SetUp_C
import com.orange.blelibrary.blelibrary.RootActivity
import kotlinx.android.synthetic.main.logout.*

class Da_LogOut :Dailog_SetUp_C(){
    override fun SetUP(root: Dialog, act: RootActivity) {
        root.cancel.setOnClickListener {
            act.DaiLogDismiss()
        }
        root.yes.setOnClickListener { val profilePreferences = act.getSharedPreferences("Frag_SettingPager_Setting", Context.MODE_PRIVATE)
            profilePreferences.edit().putString("admin","nodata").commit()
            android.os.Process.killProcess(android.os.Process.myPid()) }
    }
}