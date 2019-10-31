package com.example.obd.SettingPager


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.obd.HttpCommand.Fuction
import com.example.obd.MainActivity.HomeFragement
import com.example.obd.MainPeace
import com.orange.obd.R


class Sign_in : Fragment() {
    lateinit  var rootview:View
    lateinit var admin:EditText
    lateinit var password:EditText
    var handler= Handler()
    var run=false
    lateinit var act: MainPeace
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootview=inflater.inflate(R.layout.activity_sign_in, container, false)
        admin=rootview.findViewById(R.id.editText3)
        act=activity!! as MainPeace
        password=rootview.findViewById(R.id.editText4)
        (rootview.findViewById(R.id.button4) as Button).setOnClickListener {
            if(run){
                return@setOnClickListener
            }
            run=true
            val admin=admin.text.toString()
            val password=password.text.toString()
            act.LoadingUI("Data Loading",0)
            Thread{
                val a= Fuction.ValidateUser(admin,password)
                run=false
                handler.post {
                    act.LoadingSuccessUI()
                    if(a){
                        val profilePreferences = act.getSharedPreferences("Setting", Context.MODE_PRIVATE)
                        profilePreferences.edit().putString("admin",admin).putString("password",password).commit()
                        (activity as MainPeace).ChangePage(HomeFragement(),R.id.frage,"Home",false)
                    }else{
                       Toast.makeText(act,R.string.signfall, Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }
        (rootview.findViewById(R.id.button2) as Button).setOnClickListener {
            (activity as MainPeace).ChangePage(Enroll(),R.id.frage,"Enroll",true)
        }
        (rootview.findViewById(R.id.textView26) as TextView).setOnClickListener {
            (activity as MainPeace).ChangePage(ResetPass(),R.id.frage,"ResetPass",true)
        }
        (rootview.findViewById(R.id.imageView22) as ImageView).setOnClickListener {
            (activity as MainPeace).ChangePage(ResetPass(),R.id.frage,"ResetPass",true)
        }
        return rootview
    }


}
