package com.example.obd.MainActivity


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.obd.MainPeace
import com.example.obd.UserManual.UserManual

import com.orange.obd.R
import com.orango.electronic.orangetxusb.SettingPagr.Setting
import kotlinx.android.synthetic.main.fragment_home_fragement.view.*

class HomeFragement : Fragment() {

    lateinit var act: MainPeace
    lateinit var rootView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
      rootView= inflater.inflate(R.layout.fragment_home_fragement, container, false)
        act=activity!! as MainPeace
        val profilePreferences = act.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        val a= profilePreferences.getString("Language","English")
        if(a=="Italiano"){
            rootView.online_shopping_btn.setBackgroundResource(R.mipmap.setting)
            rootView.Setim.visibility=View.GONE
            rootView.textView68.visibility=View.GONE
            rootView.changer.text=resources.getString(R.string.Setting)
        }
        rootView.imageView4.setOnClickListener {
            act.ChangePage(MyFavorite(),R.id.frage,"MyFavorite",true)
        }
        rootView.Setim.setOnClickListener {
            act.ChangePage(Setting(),R.id.frage,"Setting",true)
        }
        rootView.selectmmy.setOnClickListener {
            act.GoScanner(Selection(),10,R.id.frage,"Selection")
          act.back.visibility=View.VISIBLE
        }
        rootView.imageView6.setOnClickListener {
            act.ChangePage(UserManual(),R.id.frage,"UserManual",true)
        }
        act.back.visibility=View.GONE
        rootView.online_shopping_btn.setOnClickListener {
            val profilePreferences = act.getSharedPreferences("Setting", Context.MODE_PRIVATE)
            val a= profilePreferences.getString("Language","English")
            var uti="http://simple-sensor.com"
            when(a){
                "繁體中文"->{ uti="http://simple-sensor.com"}
                "简体中文"->{ uti="http://simple-sensor.com"}
                "Deutsch"->{ uti="http://orange-rdks.de"}
                "English"->{ uti="http://simple-sensor.com"}
                "Italiano"->{
                    act.ChangePage(Setting(),R.id.frage,"Setting",true)
                    return@setOnClickListener
                }
            }
            try {
                val uri = Uri.parse(uti)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                act.startActivity(intent)
            }catch ( e:Exception){}

        }

        return rootView
    }

    override fun onResume() {
        act.back.visibility=View.GONE
        super.onResume()
    }

}
