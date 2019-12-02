package com.orange.blelibrary.blelibrary

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.orange.blelibrary.blelibrary.CallBack.DiapathKey
import com.orange.blelibrary.blelibrary.tool.LanguageUtil

abstract class Act_Fragement(val layout:Int,val fragid:Int) : Fragment() , DiapathKey {
    val LOCALE_ENGLISH="en"
    val LOCALE_CHINESE="zh"
    val LOCALE_TAIWAIN="tw"
    val LOCALE_ITALIANO="it"
    val LOCALE_DE="de"
    var Fraging: Fragment? = null
    var FragName = ""
    lateinit var rootview:View
    lateinit var act:RootActivity
    var handler= Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        act=activity!! as RootActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(::rootview.isInitialized){return rootview}
        rootview=inflater.inflate(layout, container, false)
        rootview.setOnClickListener { act.HideKeyBoard() }
        Laninit()
        ViewInit()
        return rootview
    }
    open fun GoMenu(){
        //返回首页,清除栈顶
        act.supportFragmentManager.popBackStack(null,1)
    }
    open fun SetPro(key:String,value:Boolean){
        val profilePreferences =act.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        profilePreferences.edit().putBoolean(key,value).commit()
    }
    open fun SetPro(key:String,value:String){
        val profilePreferences =act.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        profilePreferences.edit().putString(key,value).commit()
    }
    open fun SetPro(key:String,value:Int){
        val profilePreferences =act.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        profilePreferences.edit().putInt(key,value).commit()
    }
    open fun GetPro(key:String,value:String):String{
        val profilePreferences =act.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        return profilePreferences.getString(key,value)
    }
    open fun GetPro(key:String,value:Boolean):Boolean{
        val profilePreferences =act.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        return profilePreferences.getBoolean(key,value)
    }
    open fun GetPro(key:String,value:Int):Int{
        val profilePreferences =act.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        return profilePreferences.getInt(key,value)
    }
    open fun SetLan(value:String){
        val profilePreferences =act.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        profilePreferences.edit().putString("Lan",value).commit()
        Laninit()
        ViewInit()
    }
    open fun Laninit(){
        val profilePreferences =act.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        when(profilePreferences.getString("Lan",LOCALE_ENGLISH)){
            LOCALE_ENGLISH->{LanguageUtil.updateLocale(activity, LanguageUtil.LOCALE_ENGLISH);}
            LOCALE_CHINESE->{LanguageUtil.updateLocale(activity, LanguageUtil.LOCALE_CHINESE);}
            LOCALE_TAIWAIN->{LanguageUtil.updateLocale(activity, LanguageUtil.LOCALE_TAIWAIN);}
            LOCALE_ITALIANO->{LanguageUtil.updateLocale(activity, LanguageUtil.LOCALE_ITALIANO);}
            LOCALE_DE->{LanguageUtil.updateLocale(activity, LanguageUtil.LOCALE_DE);}
        }
    }
    open fun Toast(a:String){
        act.Toast(a)
    }
    open fun ChangePage(Translation: Fragment, tag: String, goback: Boolean) {
      act.ChangePage(Translation,fragid,tag,false)
    }
//===============================Abstract Function===============================
    /**
     * 載入rootview
     */
    abstract fun ViewInit()
}
