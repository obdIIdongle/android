package com.example.obd.Frag

import android.content.Context
import android.view.KeyEvent
import android.widget.*
import com.example.obd.util.Util_Http_Command_Function
import com.example.obd.MainPeace
import com.orange.blelibrary.blelibrary.CallBack.Dailog_SetUp_C
import com.orange.blelibrary.blelibrary.RootFragement
import com.orange.obd.R
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in.view.*

class Frag_SettingPager_Sign_in : RootFragement(R.layout.activity_sign_in) {

    var run=false
    lateinit var mainPeace: MainPeace

    override fun ViewInit() {

        mainPeace=activity!! as MainPeace
        rootview.button4.setOnClickListener {
            if(run){
                return@setOnClickListener
            }
            run=true
            val admin=admin.text.toString()
            val password=password.text.toString()
            act.ShowDaiLog(R.layout.dataloading,false,false, Dailog_SetUp_C())
            Thread{
                val a= Util_Http_Command_Function.ValidateUser(admin,password)
                run=false
                handler.post {
                    act.DaiLogDismiss()
                    if(a){
                        val profilePreferences = act.getSharedPreferences("Frag_SettingPager_Setting", Context.MODE_PRIVATE)
                        profilePreferences.edit().putString("admin",admin).putString("password",password).commit()
                        act.SetHome(Frag_MainActivity_HomeFragement(),"Frag_MainActivity_HomeFragement")
                    }else{
                        Toast.makeText(act,R.string.signfall, Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }
        rootview.button2.setOnClickListener {
            (activity as MainPeace).ChangePage(Frag_SettingPager_Enroll(),R.id.frage,"Frag_SettingPager_Enroll",true)
        }
        rootview.textView26.setOnClickListener {
            (activity as MainPeace).ChangePage(Frag_SettingPager_ResetPass(),R.id.frage,"Frag_SettingPager_ResetPass",true)
        }
        rootview.imageView22.setOnClickListener {
            (activity as MainPeace).ChangePage(Frag_SettingPager_ResetPass(),R.id.frage,"Frag_SettingPager_ResetPass",true)
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent?) {

    }

}
