package com.example.obd

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.obd.Beans.Bs_Write_and_Read
import com.example.obd.Dialog.Da_LogOut
import com.example.obd.Frag.Frag_MainActivity_HomeFragement
import com.orange.obd.R
import com.example.obd.util.Util_Command
import com.example.obd.util.Util_FtpManager
import com.orange.blelibrary.blelibrary.CallBack.Ble_Callback_C
import com.orange.blelibrary.blelibrary.CallBack.Dailog_SetUp_C
import com.orange.blelibrary.blelibrary.RootActivity
import com.orange.blelibrary.blelibrary.Server.BleServiceControl
import com.orange.etalkinglibrary.E_talking.TalkingActivity
import com.example.obd.Frag.Frag_SettingPager_PrivaryPolicy
import com.example.obd.Frag.Frag_SettingPager_Set_Languages
import com.orango.electronic.orangetxusb.mmySql.Util_MmySql_ItemDAO
import kotlinx.android.synthetic.main.activity_main_peace.view.*
import kotlinx.android.synthetic.main.fragment_test_fragement.view.*

class MainPeace : RootActivity(R.layout.activity_main_peace,R.id.frage),Ble_Callback_C {
       override var RXDATA: String=""
       override var id: Int=0
       override var tag: String=""
       override var ConnectDelay: Int=10
       override lateinit var Translation: Fragment
       override var bleServiceControl: BleServiceControl=BleServiceControl()
       override fun bleact(): RootActivity {
           return this
       }
       var AppVersion = ""
       var command = Util_Command()
       var SelectMake = ""
       var SelectModel = ""
       var SelectYear = ""
       val utilMmySqlItemDAO: Util_MmySql_ItemDAO by lazy { Util_MmySql_ItemDAO(applicationContext) }
       var Obd_PAD=0
       override fun ViewInit(rootview: View) {
           (application as Myapp).act = this
           rootview.test.visibility = View.GONE
           command.act = this
           ShowDaiLog(R.layout.dataloading,false,false,object :Dailog_SetUp_C(){
               override fun SetUP(root: Dialog, act: RootActivity) {
                   root.findViewById<TextView>(R.id.title).text=resources.getString(R.string.Data_Loading)
               }
           })
           DonloadMMy()
           val profilePreferences = getSharedPreferences("Frag_SettingPager_Setting", Context.MODE_PRIVATE)
           if (profilePreferences.getString("admin", "nodata").equals("nodata")) {
               Frag_SettingPager_Set_Languages.place = 0
               Frag_SettingPager_PrivaryPolicy.place = 0
               rootview.frage.setBackgroundColor(resources.getColor(R.color.backgroung))
               ChangePage(Frag_SettingPager_Set_Languages(), R.id.frage, "Frag_SettingPager_Set_Languages_first", false)
           } else {
               rootview.frage.setBackgroundColor(resources.getColor(R.color.backgroung))
               SetHome(Frag_MainActivity_HomeFragement(), "Frag_MainActivity_HomeFragement")
           }
           //rootview.exit.setOnClickListener {
                   //ShowDaiLog(R.layout.logout,false,false, Da_LogOut())
           //}
           rootview.back.setOnClickListener { GoBack() }
           //rootview.test.setOnClickListener { GoScanner(TestFragement(), 10, R.id.frage, "Test") }
           rootview.customer.setOnClickListener { startActivity(Intent(this, TalkingActivity::class.java)) }
       }

       override fun KeyLinsten(event: KeyEvent) {

       }
    override fun ChangePageListener(tag: String, frag: Fragment) {
        Log.d("switch", tag)

        rootview.back.setImageResource(R.mipmap.btn_back_normal)
        rootview.back.setOnClickListener { GoBack() }
        //rootview.back.visibility = View.GONE

        rootview.exit.setImageResource(R.drawable.out)
      //  rootview.exit.visibility = View.GONE
        rootview.exit.setOnClickListener {
            ShowDaiLog(R.layout.logout,false,false, Da_LogOut())
        }

        if (tag == "Frag_SettingPager_Setting" || tag == "Frag_SettingPager_Set_Languages"||tag == "Frag_SettingPager_PrivaryPolicy"
                || tag == "Frag_UserManual" || tag == "Frag_UserManual_Detail" || tag == "Frag_SettingPager_Update"
                || tag == "Frag_SettingPager_Enroll" || tag == "Frag_SettingPager_ResetPass") {
            Log.d("name", tag)

            //rootview.exit.setImageResource(R.drawable.out)
            rootview.back.visibility = View.VISIBLE
            rootview.exit.visibility = View.GONE
            //rootview.exit.setOnClickListener {
                //ShowDaiLog(R.layout.logout,false,false, Da_LogOut())
            //}
        }
        else if(tag == "Frag_SettingPager_Sign_in")
        {
            //rootview.exit.setImageResource(R.drawable.out)
            rootview.back.visibility = View.GONE
            rootview.exit.visibility = View.GONE
        }
        else if(tag == "Frag_MainActivity_HomeFragement")
        {
            //rootview.exit.setImageResource(R.drawable.out)
            rootview.back.visibility = View.GONE
            rootview.exit.visibility = View.VISIBLE
        }
        else {
            rootview.back.visibility = View.VISIBLE
            rootview.exit.visibility = View.GONE
            if(bleServiceControl.isconnect)
            {
                rootview.exit.visibility = View.VISIBLE
                rootview.exit.setImageResource(R.mipmap.icon_obdii)
                rootview.exit.setOnClickListener {

                }
            }
        }

        when (tag) {
            "Frag_SettingPager_Set_Languages_first"->{
                rootview.back.visibility=View.GONE
                rootview.exit.visibility=View.GONE
            }
            "Frag_SettingPager_Sign_in" ->
            {
                rootview.bartitle.text = "OBDII DONGLE"
            }

            "Frag_SettingPager_Enroll" ->
            {
                rootview.bartitle.text = "OBDII DONGLE"
            }

            "Frag_SettingPager_ResetPass" ->
            {
                rootview.bartitle.text = "OBDII DONGLE"
            }

            "Frag_Function_Selection" -> {
                //rootview.bartitle.text = "OBDII DONGLE"
                when (Bs_Write_and_Read.Trun) {
                    Bs_Write_and_Read.讀取 -> {
                        rootview.bartitle.text = getString(R.string.app_sensor_info_read)
                    }

                    Bs_Write_and_Read.寫入 ->
                    {
                        rootview.bartitle.text = getString(R.string.OBDII_relearn)
                    }
                }
            }

            "Frag_SelectMmyPage_MakeFragement" -> {
            }
            "Frag_SelectMmyPage_ModelFragement" -> {
            }
            "Frag_MainActivity_HomeFragement" -> {
                rootview.bartitle.text = "Orange TPMS"
            }
            "Frag_SelectMmyPage_YearFragement" -> {
            }
            "Frag_Function_OBDII_relearn" -> {
                when (Bs_Write_and_Read.Trun) {
                    Bs_Write_and_Read.讀取 -> {
                        rootview.bartitle.text = getString(R.string.app_sensor_info_read)
                    }

                    Bs_Write_and_Read.寫入 ->
                    {
                        rootview.bartitle.text = getString(R.string.OBDII_relearn)
                    }
                }
            }
            "Frag_Function_Show_Read" -> {
            }
            "Frag_SettingPager_Setting" -> {
                rootview.bartitle.text = getString(R.string.Setting)
            }
            "Frag_SettingPager_Set_Languages" -> {
                rootview.bartitle.text = getString(R.string.AreaLanguage)
            }
            "Frag_SettingPager_PrivaryPolicy_first"->{
                rootview.back.visibility=View.GONE
                rootview.exit.visibility=View.GONE
            }
            "Frag_SettingPager_PrivaryPolicy" -> {
                rootview.bartitle.text = getString(R.string.Privacy_Policy)
            }
            "Frag_SettingPager_Update" -> {
                rootview.bartitle.text = getString(R.string.update)
            }
            "Frag_UserManual" -> {
                rootview.bartitle.text = getString(R.string.Users_manual)
            }
        }
    }

    fun DonloadMMy() {
        Thread {
            var a = Util_FtpManager.DownMMy(this)
            if (a) {
                handler.post {
                    DaiLogDismiss()
                }
            } else {
                DonloadMMy()
            }
        }.start()
    }
    override fun TX(a: String) {
        Log.d("BLEDATA", "TX:" + a)
        handler.post {
            if (supportFragmentManager.fragments.get(supportFragmentManager.fragments.size - 1).tag != null && supportFragmentManager.fragments.get(supportFragmentManager.fragments.size - 1).tag == "Test") {
                var fragment = supportFragmentManager.fragments.get(supportFragmentManager.fragments.size - 1) as TestFragement
                fragment.rootview.teststring.append("\nTX:" + a)
                fragment.rootview.sc.scrollTo(0, 0)
            }
        }
    }

    override fun RX(a: String) {
        Log.d("BLEDATA", "RX:" + a)
        handler.post {
            if (supportFragmentManager.fragments.get(supportFragmentManager.fragments.size - 1).tag != null && supportFragmentManager.fragments.get(supportFragmentManager.fragments.size - 1).tag == "Test") {
                var fragment = supportFragmentManager.fragments.get(supportFragmentManager.fragments.size - 1) as TestFragement
                fragment.rootview.teststring.append("\nRX:" + a)
                fragment.rootview.sc.scrollTo(0, 0)
            }
        }
    }
}
