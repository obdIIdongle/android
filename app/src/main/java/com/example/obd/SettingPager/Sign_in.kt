package com.example.obd.SettingPager


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.obd.HttpCommand.Fuction
import com.example.obd.MainActivity.HomeFragement
import com.example.obd.MainActivity.MainPeace
import com.orange.obd.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Sign_in : Fragment() {
    lateinit  var rootview:View
    lateinit var admin:EditText
    lateinit var password:EditText
    var handler= Handler()
    var run=false
    lateinit var act:MainPeace
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
            act.LoadingUI("Data Loading")
            Thread{
                val a= Fuction.ValidateUser(admin,password)
                run=false
                handler.post {
                    act.LoadingSuccessUI()
                    if(a){
                        val profilePreferences = act.getSharedPreferences("Setting", Context.MODE_PRIVATE)
                        profilePreferences.edit().putString("admin",admin).putString("password",password).commit()
                        val transaction = fragmentManager!!.beginTransaction()
                        transaction.replace(R.id.frage, HomeFragement(),"Home")
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                                .commit()
                    }else{
                       Toast.makeText(act,R.string.signfall, Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }
        (rootview.findViewById(R.id.button2) as Button).setOnClickListener {
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.frage, Enroll())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                    .addToBackStack(null)
                    .commit()
        }
        (rootview.findViewById(R.id.textView26) as TextView).setOnClickListener {
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.frage, ResetPass())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                    .addToBackStack(null)
                    .commit()
        }
        (rootview.findViewById(R.id.imageView22) as ImageView).setOnClickListener {
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.frage, ResetPass())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                    .addToBackStack(null)
                    .commit()
        }
        return rootview
    }


}
