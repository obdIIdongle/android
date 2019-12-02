package com.example.obd

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.obd.MainActivity.HomeFragement
import com.example.obd.MainActivity.Logout
import com.orange.obd.R
import com.example.obd.tool.Command
import com.example.obd.tool.FtpManager
import com.orange.blelibrary.blelibrary.CallBack.Ble_Callback_C
import com.orange.blelibrary.blelibrary.CallBack.Dailog_SetUp_C
import com.orange.blelibrary.blelibrary.RootActivity
import com.orange.blelibrary.blelibrary.RootFragement
import com.orange.blelibrary.blelibrary.Server.BleServiceControl
import com.orange.etalkinglibrary.E_talking.TalkingActivity
import com.orango.electronic.orangetxusb.SettingPagr.PrivaryPolicy
import com.orango.electronic.orangetxusb.SettingPagr.Set_Languages
import com.orango.electronic.orangetxusb.mmySql.ItemDAO
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
       var command = Command()
       var SelectMake = ""
       var SelectModel = ""
       var SelectYear = ""
       val itemDAO: ItemDAO by lazy { ItemDAO(applicationContext) }
       var Obd_PAD=0
       override fun ViewInit(rootview: View) {
           (application as Myapp).act = this
           command.act = this
           ShowDaiLog(R.layout.dataloading,false,false,object :Dailog_SetUp_C(){
               override fun SetUP(root: Dialog, act: RootActivity) {
                   root.findViewById<TextView>(R.id.tit).text=resources.getString(R.string.Data_Loading)
               }
           })
           DonloadMMy()
           val profilePreferences = getSharedPreferences("Setting", Context.MODE_PRIVATE)
           if (profilePreferences.getString("admin", "nodata").equals("nodata")) {
               Set_Languages.place = 0
               PrivaryPolicy.place = 0
               rootview.frage.setBackgroundColor(resources.getColor(R.color.backgroung))
               ChangePage(Set_Languages(), R.id.frage, "Set_Languages", false)
           } else {
               rootview.frage.setBackgroundColor(resources.getColor(R.color.backgroung))
               ChangePage(HomeFragement(), R.id.frage, "Home", false)
           }
           rootview.exit.setOnClickListener {
               var intent = Intent(this, Logout::class.java)
               startActivity(intent) }
           rootview.back.setOnClickListener { GoBack() }
           rootview.test.setOnClickListener { GoScanner(TestFragement(), 10, R.id.frage, "Test") }
           rootview.customer.setOnClickListener { startActivity(Intent(this, TalkingActivity::class.java)) }
       }

       override fun KeyLinsten(event: KeyEvent) {

       }
    override fun ChangePageListener(tag: String, frag: Fragment) {
        Log.d("switch", tag)
        if (tag == "Set_Languages"||tag == "Policy"||tag == "Sign_in"||tag == "Home") {
            Log.d("name", tag)
            rootview.back.visibility = View.GONE
            rootview.exit.visibility = View.VISIBLE
        } else {
            rootview.back.visibility = View.VISIBLE
            rootview.exit.visibility = View.GONE
        }
        when (tag) {
            "Selection" -> {
                rootview.bartitle.text = "OBDII DONGLE"
            }
            "MakeFragement" -> {
            }
            "ModelFragement" -> {
            }
            "Home" -> {
                rootview.bartitle.text = "Orange TPMS"
            }
            "YearFragement" -> {
            }
            "OBDII_relearn" -> {
                rootview.bartitle.text = getString(R.string.OBDII_relearn)
            }
            "Show_Read" -> {
            }
            "Setting" -> {
                rootview.bartitle.text = getString(R.string.Setting)
            }
            "SetArea" -> {
                rootview.bartitle.text = getString(R.string.AreaLanguage)
            }
            "policy" -> {
                rootview.bartitle.text = getString(R.string.Privacy_Policy)
            }
            "Update" -> {
                rootview.bartitle.text = getString(R.string.update)
            }
            "UserManual" -> {
                rootview.bartitle.text = getString(R.string.Users_manual)
            }
        }
    }

    fun DonloadMMy() {
        Thread {
            var a = FtpManager.DownMMy(this)
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
