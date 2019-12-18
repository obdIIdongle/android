package com.example.obd.Frag


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.example.obd.Beans.Bs_Write_and_Read
import com.example.obd.MainPeace

import com.orange.obd.R
import com.orange.blelibrary.blelibrary.RootFragement
import kotlinx.android.synthetic.main.activity_main_peace.view.*
import kotlinx.android.synthetic.main.fragment_home_fragement.view.*

class Frag_MainActivity_HomeFragement : RootFragement(R.layout.fragment_home_fragement) {
    
    lateinit var mainPeace: MainPeace
   
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
    override fun ViewInit() {

        mainPeace=activity!! as MainPeace
        val profilePreferences = act.getSharedPreferences("Frag_SettingPager_Setting", Context.MODE_PRIVATE)
        val a= profilePreferences.getString("Language","English")
        if(a=="Italiano"){
            rootview.online_shopping_btn.setBackgroundResource(R.mipmap.setting)
            rootview.Setim.visibility=View.GONE
            rootview.textView68.visibility=View.GONE
            rootview.changer.text=resources.getString(R.string.Setting)
        }
        rootview.pad.setOnClickListener {
            mainPeace.Obd_PAD=1
            mainPeace.GoScanner(Frag_Function_Selection(),10,R.id.frage,"Frag_Function_Selection")
        }

        rootview.read_sensor.setOnClickListener {
            mainPeace.Obd_PAD=0
            Bs_Write_and_Read.Trun = Bs_Write_and_Read.讀取
            mainPeace.GoScanner(Frag_Function_Selection(),10,R.id.frage,"Frag_Function_Selection")
        }

        rootview.Setim.setOnClickListener {
            act.ChangePage(Frag_SettingPager_Setting(),R.id.frage,"Frag_SettingPager_Setting",true)
        }
        rootview.obd.setOnClickListener {
            mainPeace.Obd_PAD=0
            Bs_Write_and_Read.Trun = Bs_Write_and_Read.寫入
            mainPeace.GoScanner(Frag_Function_Selection(),10,R.id.frage,"Frag_Function_Selection")
        }
        rootview.user_menual.setOnClickListener {
            act.ChangePage(Frag_UserManual(),R.id.frage,"Frag_UserManual",true)
        }
        act.rootview.back.visibility=View.GONE
        rootview.online_shopping_btn.setOnClickListener {
            val profilePreferences = act.getSharedPreferences("Frag_SettingPager_Setting", Context.MODE_PRIVATE)
            val a= profilePreferences.getString("Language","English")
            var uti="http://simple-sensor.com"
            when(a){
                "繁體中文"->{ uti="http://simple-sensor.com"}
                "简体中文"->{ uti="http://simple-sensor.com"}
                "Deutsch"->{ uti="http://orange-rdks.de"}
                "English"->{ uti="http://simple-sensor.com"}
                "Italiano"->{
                    act.ChangePage(Frag_SettingPager_Setting(),R.id.frage,"Frag_SettingPager_Setting",true)
                    return@setOnClickListener
                }
            }
            try {
                val uri = Uri.parse(uti)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                act.startActivity(intent)
            }catch ( e:Exception){}

        }
    }

    override fun dispatchKeyEvent(event: KeyEvent?) {
        
    }

    override fun onResume() {
        super.onResume()
    }

}
