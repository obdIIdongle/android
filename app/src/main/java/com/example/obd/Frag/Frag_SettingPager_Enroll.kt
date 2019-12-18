package com.example.obd.Frag


import android.content.Context
import android.view.KeyEvent
import android.widget.*
import com.example.obd.Beans.Bs_SensorRecord
import com.example.obd.util.Util_Http_Command_Function
import com.example.obd.MainPeace
import com.orange.blelibrary.blelibrary.CallBack.Dailog_SetUp_C
import com.orange.blelibrary.blelibrary.RootFragement

import com.orange.obd.R
import kotlinx.android.synthetic.main.activity_enroll.*
import kotlinx.android.synthetic.main.activity_enroll.view.*
import java.util.ArrayList

class Frag_SettingPager_Enroll : RootFragement(R.layout.activity_enroll) {

    var Arealist = ArrayList<String>()
    var Arealist2 = ArrayList<String>()

    lateinit var mainPeace: MainPeace

    override fun ViewInit() {

        mainPeace = activity!! as MainPeace
        Arealist.add("Select")
        Arealist.add("EU")
        Arealist.add("North America")
        Arealist.add("台灣")
        Arealist.add("中國大陸")
        Arealist2.add(getString(R.string.Distributor))
        Arealist2.add(getString(R.string.Retailer))

        val arrayAdapter = ArrayAdapter<String>(act, android.R.layout.simple_spinner_item, Arealist)
        val arrayAdapter2 = ArrayAdapter<String>(act, android.R.layout.simple_spinner_item, Arealist2)

        rootview.AreaSpinner.adapter = arrayAdapter
        rootview.Store.adapter = arrayAdapter2

        rootview.cancel.setOnClickListener {
            act.GoBack()
        }

        rootview.next.setOnClickListener {
            if (isrunn) {
                return@setOnClickListener
            }
            act.ShowDaiLog(R.layout.dataloading, false, false, Dailog_SetUp_C())
            var email = email.text.toString()
            var password = password.text.toString()
            var repeatpassword = repeatpassword.text.toString()
            var serialnumber = serialnumber.text.toString()
            var firstname = firstname.text.toString()
            var lastname = lastname.text.toString()
            var company = company.text.toString()
            var phone = phone.text.toString()
            var streat = streat.text.toString()
            var city = city.text.toString()
            var state = AreaSpinner.selectedItem.toString()
            var storetype = Store.selectedItem.toString()
            var zpcode = zpcode.text.toString()

            if (!password.equals(repeatpassword)) {
                Toast.makeText(act, resources.getString(R.string.confirm_password), Toast.LENGTH_SHORT).show()
            }
            Thread {
                isrunn = true
                var a = 0
                if (storetype.equals(getString(R.string.Distributor))) {
                    a = Util_Http_Command_Function.Register(email, password, serialnumber, "Distributor", company, firstname, lastname, phone, state, city, streat, zpcode)
                } else {
                    a = Util_Http_Command_Function.Register(email, password, serialnumber, "Retailer", company, firstname, lastname, phone, state, city, streat, zpcode)
                }
                handler.post {
                    act.DaiLogDismiss()
                    if (a == -1) {
                        Toast.makeText(act, resources.getString(R.string.error), Toast.LENGTH_SHORT).show()
                    } else if (a == 1) {
                        Toast.makeText(act, resources.getString(R.string.be_register), Toast.LENGTH_SHORT).show()
                    } else {
                        val profilePreferences = act.getSharedPreferences("Frag_SettingPager_Setting", Context.MODE_PRIVATE)
                        profilePreferences.edit().putString("admin", email).putString("password", password).commit()
                        (activity as MainPeace).ChangePage(Frag_MainActivity_HomeFragement(), R.id.frage, "Home", false)
                    }
                }
                isrunn = false
            }.start()

        }
    }

    override fun dispatchKeyEvent(event: KeyEvent?) {

    }

    var isrunn = false

}
