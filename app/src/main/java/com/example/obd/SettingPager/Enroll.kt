package com.example.obd.SettingPager


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.airbnb.lottie.LottieAnimationView
import com.example.obd.HttpCommand.Fuction
import com.example.obd.MainActivity.HomeFragement
import com.example.obd.MainPeace

import com.orange.obd.R
import kotlinx.android.synthetic.main.activity_enroll.view.*
import java.util.ArrayList

class Enroll : Fragment() {
lateinit var rootview:View
    lateinit var AreaSpinner: Spinner
    lateinit var Store: Spinner
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var repeatpassword: EditText
    lateinit var serialnumber: EditText
    lateinit var firstname: EditText
    lateinit var lastname: EditText
    lateinit var company: EditText
    lateinit var programAnimator: LottieAnimationView
    lateinit var phone: EditText
    lateinit var streat: EditText
    lateinit var city: EditText
    lateinit var state: EditText
    lateinit var zpcode: EditText
    lateinit var loadtitle: TextView
    lateinit var load: RelativeLayout
    lateinit var act: MainPeace
    var Arealist= ArrayList<String>()
    var Arealist2= ArrayList<String>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootview=inflater.inflate(R.layout.activity_enroll, container, false)
        act=activity!! as MainPeace
        load=rootview.findViewById(R.id.load)
        loadtitle=rootview.findViewById(R.id.textView11)
        programAnimator=rootview.findViewById(R.id.animation_view6)
        email=rootview.findViewById(R.id.email)
        password=rootview.findViewById(R.id.password)
        repeatpassword=rootview.findViewById(R.id.repeatpassword)
        serialnumber=rootview.findViewById(R.id.serialnumber)
        firstname=rootview.findViewById(R.id.firstname)
        lastname=rootview.findViewById(R.id.lastname)
        company=rootview.findViewById(R.id.company)
        phone=rootview.findViewById(R.id.phone)
        streat=rootview.findViewById(R.id.streat)
        city=rootview.findViewById(R.id.city)
        state=rootview.findViewById(R.id.state)
        zpcode=rootview.findViewById(R.id.zpcode)
        Store=rootview.findViewById(R.id.spinner6)
        AreaSpinner=rootview.findViewById(R.id.spinner5)
        Arealist.add("Select")
        Arealist.add("EU")
        Arealist.add("North America")
        Arealist.add("台灣")
        Arealist.add("中國大陸")
        Arealist2.add(getString(R.string.Distributor))
        Arealist2.add(getString(R.string.Retailer))
        val arrayAdapter = ArrayAdapter<String>(act, android.R.layout.simple_spinner_item, Arealist)
        val arrayAdapter2 = ArrayAdapter<String>(act, android.R.layout.simple_spinner_item, Arealist2)
        AreaSpinner.adapter=arrayAdapter
        Store.adapter=arrayAdapter2
rootview.cancel.setOnClickListener {
  act.GoBack()
}
        rootview.next.setOnClickListener {
            if(isrunn){
                return@setOnClickListener
            }
            act.LoadingUI("Data Loading",0)
            var email=email.text.toString()
            var password=password.text.toString()
            var repeatpassword=repeatpassword.text.toString()
            var serialnumber=serialnumber.text.toString()
            var firstname=firstname.text.toString()
            var lastname=lastname.text.toString()
            var company=company.text.toString()
            var phone=phone.text.toString()
            var streat=streat.text.toString()
            var city=city.text.toString()
            var state=AreaSpinner.selectedItem.toString()
            var storetype=Store.selectedItem.toString()
            var zpcode=zpcode.text.toString()
            if(!password.equals(repeatpassword)){
                Toast.makeText(act,resources.getString(R.string.confirm_password),Toast.LENGTH_SHORT).show()
            }
            Thread{
                isrunn=true
                var a=0
                if(storetype.equals(getString(R.string.Distributor))){
                    a= Fuction.Register(email,password,serialnumber,"Distributor",company,firstname,lastname,phone,state,city,streat,zpcode)
                }else{
                    a=Fuction.Register(email,password,serialnumber,"Retailer",company,firstname,lastname,phone,state,city,streat,zpcode)
                }
                handler.post {
                    act.LoadingSuccessUI()
                    if(a==-1){
                        Toast.makeText(act,resources.getString(R.string.error),Toast.LENGTH_SHORT).show()
                    }else if(a==1){
                        Toast.makeText(act,resources.getString(R.string.be_register),Toast.LENGTH_SHORT).show()
                    }else{
                        val profilePreferences = act.getSharedPreferences("Setting", Context.MODE_PRIVATE)
                        profilePreferences.edit().putString("admin",email).putString("password",password).commit()
                        (activity as MainPeace).ChangePage(HomeFragement(),R.id.frage,"Home",false)
                    }
                }
                isrunn=false
            }.start()

        }
        return rootview
    }
    var isrunn=false
    var handler= Handler()
}
