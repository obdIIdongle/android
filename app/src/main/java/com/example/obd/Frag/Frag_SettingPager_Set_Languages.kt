package com.example.obd.Frag


import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.KeyEvent
import android.widget.ArrayAdapter
import com.example.obd.MainPeace
import com.example.obd.util.Util_LanguageUtil
import com.orange.blelibrary.blelibrary.RootFragement
import com.orange.obd.R
import kotlinx.android.synthetic.main.fragment_set__languages.*
import kotlinx.android.synthetic.main.fragment_set__languages.view.*

import java.util.ArrayList

class Frag_SettingPager_Set_Languages : RootFragement(R.layout.fragment_set__languages) {

    lateinit var profilePreferences:SharedPreferences
    lateinit var profileEditor :SharedPreferences.Editor
    var Arealist= ArrayList<String>()
    var LanguageList= ArrayList<String>()

    override fun ViewInit() {

        profilePreferences = activity!!.getSharedPreferences("Frag_SettingPager_Setting", Context.MODE_PRIVATE)
        Arealist.add("Select")
        Arealist.add("EU")
        Arealist.add("North America")
        Arealist.add("台灣")
        Arealist.add("中國大陸")
        val arrayAdapter = ArrayAdapter<String>(activity!!, android.R.layout.simple_spinner_item, Arealist)
        rootview.AreaSpinner.adapter=arrayAdapter
        LanguageList.add("Select")
        LanguageList.add("繁體中文")
        LanguageList.add("简体中文")
        LanguageList.add("Deutsche")
        LanguageList.add("English")
        LanguageList.add("Italiano")
        val lanAdapter = ArrayAdapter<String>(activity!!, android.R.layout.simple_spinner_item, LanguageList)
        rootview.LanguagesSpinner.adapter=lanAdapter

        rootview.next2.setOnClickListener {
            if(LanguagesSpinner.selectedItem.toString().equals("Select")||AreaSpinner.selectedItem.toString().equals("Select")){return@setOnClickListener}
            profileEditor = profilePreferences.edit()
            profileEditor.putString("Area",AreaSpinner.selectedItem.toString());
            profileEditor.putString("Language",LanguagesSpinner.selectedItem.toString());
            profileEditor.commit()
            Log.d("Language",LanguagesSpinner.selectedItem.toString())
            when(LanguagesSpinner.selectedItem.toString()){
                "繁體中文"->{
                    SetLan(LOCALE_TAIWAIN)
                }
                "简体中文"->{
                    SetLan(LOCALE_CHINESE)
                }
                "Deutsche"->{
                    SetLan(LOCALE_DE)
                }
                "English"->{
                    SetLan(LOCALE_ENGLISH)
                }
                "Italiano"->{
                    SetLan(LOCALE_ITALIANO)
                }
            }
            if(place ==0){
                (activity as MainPeace).ChangePage(Frag_SettingPager_PrivaryPolicy(),R.id.frage,"Frag_SettingPager_PrivaryPolicy",false)
            }else{
                act.SetHome(Frag_MainActivity_HomeFragement(), "Frag_MainActivity_HomeFragement")
            }
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent?) {

    }

    companion object {
       var place=0
    }
}
