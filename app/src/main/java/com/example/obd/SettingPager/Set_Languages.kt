package com.orango.electronic.orangetxusb.SettingPagr


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.obd.MainActivity.HomeFragement
import com.example.obd.MainPeace
import com.example.obd.tool.LanguageUtil
import com.orange.obd.R

import kotlinx.android.synthetic.main.fragment_set__languages.view.*
import java.util.ArrayList


class Set_Languages : Fragment() {
    companion object {
       var place=0
    }
    lateinit var AreaSpinner: Spinner
    lateinit var LanguagesSpinner: Spinner
    lateinit var profilePreferences:SharedPreferences
    lateinit var profileEditor :SharedPreferences.Editor
    var Arealist= ArrayList<String>()
    var LanguageList= ArrayList<String>()
    lateinit var rootView:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView=inflater.inflate(R.layout.fragment_set__languages, container, false)
        profilePreferences = activity!!.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        AreaSpinner=rootView.findViewById(R.id.spinner2)
        LanguagesSpinner=rootView.findViewById(R.id.spinner4)
        Arealist.add("Select")
        Arealist.add("EU")
        Arealist.add("North America")
        Arealist.add("台灣")
        Arealist.add("中國大陸")
        val arrayAdapter = ArrayAdapter<String>(activity!!, android.R.layout.simple_spinner_item, Arealist)
        AreaSpinner.adapter=arrayAdapter
        LanguageList.add("Select")
        LanguageList.add("繁體中文")
        LanguageList.add("简体中文")
        LanguageList.add("Deutsche")
        LanguageList.add("English")
        LanguageList.add("Italiano")
        val lanAdapter = ArrayAdapter<String>(activity!!, android.R.layout.simple_spinner_item, LanguageList)
        LanguagesSpinner.adapter=lanAdapter

        rootView.next2.setOnClickListener {
            if(LanguagesSpinner.selectedItem.toString().equals("Select")||AreaSpinner.selectedItem.toString().equals("Select")){return@setOnClickListener}
            profileEditor = profilePreferences.edit()
            profileEditor.putString("Area",AreaSpinner.selectedItem.toString());
            profileEditor.putString("Language",LanguagesSpinner.selectedItem.toString());
            profileEditor.commit()
            Log.d("Language",LanguagesSpinner.selectedItem.toString())
            when(LanguagesSpinner.selectedItem.toString()){
                "繁體中文"->{ LanguageUtil.updateLocale(activity, LanguageUtil.LOCALE_TAIWAIN);}
                "简体中文"->{ LanguageUtil.updateLocale(activity, LanguageUtil.LOCALE_CHINESE);}
                "Deutsche"->{ LanguageUtil.updateLocale(activity, LanguageUtil.LOCALE_DE);}
                "English"->{ LanguageUtil.updateLocale(activity, LanguageUtil.LOCALE_ENGLISH);}
                "Italiano"->{ LanguageUtil.updateLocale(activity, LanguageUtil.LOCALE_ITALIANO);}
            }
            if(place==0){
                (activity as MainPeace).ChangePage(PrivaryPolicy(),R.id.frage,"Policy",true)
            }else{
                (activity as MainPeace).ChangePage(HomeFragement(),R.id.frage,"Home",true)
            }
        }
        return rootView
    }

    override fun onResume() {
        super.onResume()
//        (activity as MainPeace).setActionBarTitle(activity!!.resources.getString(R.string.AreaLanguage))
        (activity as MainPeace).back.visibility=View.VISIBLE
//        (activity as MainPeace).RightTop.visibility=View.GONE
    }
}
