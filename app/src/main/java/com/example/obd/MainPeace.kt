package com.example.obd

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.widget.*
import com.airbnb.lottie.LottieAnimationView
import com.example.obd.FunctionPage.Key_ID
import com.example.obd.MainActivity.HomeFragement
import com.example.obd.MainActivity.Logout
import com.example.obd.MainActivity.TakeOut
import com.orange.obd.R
import com.example.obd.tool.Command
import com.example.obd.tool.FtpManager
import com.orange.blelibrary.blelibrary.BleActivity
import com.orange.etalkinglibrary.E_talking.TalkingActivity
import com.orango.electronic.orangetxusb.SettingPagr.PrivaryPolicy
import com.orango.electronic.orangetxusb.SettingPagr.Set_Languages
import com.orango.electronic.orangetxusb.mmySql.ItemDAO
import kotlinx.android.synthetic.main.fragment_test_fragement.view.*

open class MainPeace : BleActivity() {
    val itemDAO: ItemDAO by lazy { ItemDAO(applicationContext) }

    override fun ChangePageListener(tag:String,frag:Fragment) {
        Log.d("switch",tag)
        if (tag == "Home") {
            Log.d("name", tag)
            back.visibility = View.GONE
            exit.visibility=View.VISIBLE
        } else {
            back.visibility = View.VISIBLE
            exit.visibility=View.GONE
        }
        when(tag){
            "Selection"->{bartitle.text="OBDII DONGLE"}
            "MakeFragement"->{}
            "ModelFragement"->{}
            "Home"->{bartitle.text="Orange TPMS"}
            "YearFragement"->{}
            "OBDII_relearn"->{bartitle.text=getString(R.string.OBDII_relearn)}
            "Show_Read"->{}
            "Setting"->{bartitle.text=getString(R.string.Setting)}
            "SetArea"->{bartitle.text=getString(R.string.AreaLanguage)}
            "policy"->{bartitle.text=getString(R.string.Privacy_Policy)}
            "Update"->{bartitle.text=getString(R.string.update)}
            "UserManual"->{bartitle.text=getString(R.string.Users_manual)}
        }
    }
    var AppVersion=""
    var command = Command()
    var SelectMake = ""
    var SelectModel = ""
    var SelectYear = ""
    lateinit var feage: FrameLayout
    lateinit var textView: TextView
    lateinit var back: ImageView
    lateinit var bartitle: TextView
    lateinit var load: RelativeLayout
    lateinit var exit: ImageView
    private var savedState: Bundle? = null
    lateinit var anim: LottieAnimationView
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_peace)
        (application as Myapp).act = this
        bartitle=findViewById(R.id.bartitle)
        exit=findViewById(R.id.exit)
        back = findViewById(R.id.imageView13)
        feage = findViewById(R.id.frage)
        load = findViewById(R.id.load)
        textView = findViewById(R.id.textView11)
        anim = findViewById(R.id.animation_view)
        savedState = savedInstanceState
        if (savedState != null) onBackStackChanged()
        command.act = this
        LoadingUI(resources.getString(R.string.Data_Loading), 0)
        DonloadMMy()
        val profilePreferences = getSharedPreferences("Setting", Context.MODE_PRIVATE)
        if (profilePreferences.getString("admin", "nodata").equals("nodata")) {
            Set_Languages.place = 0
            PrivaryPolicy.place = 0
            feage.setBackgroundColor(resources.getColor(R.color.backgroung))
            ChangePage(Set_Languages(), R.id.frage, "Set_Languages", false)
        } else {
            feage.setBackgroundColor(R.color.backgroung)
            ChangePage(HomeFragement(),R.id.frage,"Home",false)
        }
    }

    fun DonloadMMy() {
        Thread {
            var a = FtpManager.DownMMy(this)
            if (a) {
                handler.post {
                    LoadingSuccessUI()
                }
            } else {
                DonloadMMy()
            }
        }.start()
    }

    override fun ConnectSituation(boolean: Boolean) {
            if (boolean) {
                Log.d("連線", "連線ok")
            } else {
                Thread{
                    handler.post {
                        Log.d("連線", "Bluetooth is disconnected")
                        LoadingSuccessUI()
                        supportFragmentManager.popBackStack(null,1)
                        Key_ID.s19 = ""
                        TakeOut.DS_OR_CO = 0
                        ShowDaiLog(R.layout.disconnect,true,false)
                    }
                }.start()
            }
    }

    override fun TX(a: String) {
        Log.d("BLEDATA", "TX:"+a)
        handler.post {
            if (supportFragmentManager.fragments.get(supportFragmentManager.fragments.size - 1).tag != null && supportFragmentManager.fragments.get(supportFragmentManager.fragments.size - 1).tag == "Test") {
                var fragment = supportFragmentManager.fragments.get(supportFragmentManager.fragments.size - 1) as TestFragement
                fragment.rootview.teststring.append("\nTX:" + a)
                fragment.rootview.sc.scrollTo(0, 0)
            }
        }
    }

    override fun RX(a: String) {
        Log.d("BLEDATA","RX:"+a)
        handler.post {
            if (supportFragmentManager.fragments.get(supportFragmentManager.fragments.size - 1).tag != null && supportFragmentManager.fragments.get(supportFragmentManager.fragments.size - 1).tag == "Test") {
                var fragment = supportFragmentManager.fragments.get(supportFragmentManager.fragments.size - 1) as TestFragement
                fragment.rootview.teststring.append("\nRX:" + a)
                fragment.rootview.sc.scrollTo(0, 0)
            }
        }
    }

    override fun LoadingUI(a: String, pass: Int) {
        if (pass == 0) {
            textView.text = a
        } else {
            textView.text = a + "...$pass%"
        }

        load.visibility = View.VISIBLE
        anim.visibility = View.VISIBLE

    }

    override fun LoadingSuccessUI() {
        load.visibility = View.GONE
        anim.visibility = View.GONE
    }

    var GoMenu = true;
    fun onclick(view: View) {
        when (view.id) {
            R.id.exit -> {
                var intent = Intent(this, Logout::class.java)
                startActivity(intent)
            }
            R.id.imageView13 -> {
                if (GoMenu) {
                    supportFragmentManager.popBackStack(null,1)
                    GoMenu = false
                    back.setImageResource(R.mipmap.btn_back_normal)
                } else {
                    GoBack()
                }
            }
            R.id.imageView40 -> {
                startActivity(Intent(this, TalkingActivity::class.java))
            }
            R.id.button13 -> {
                GoScanner(TestFragement(), 10, R.id.frage, "Test")
            }
        }
    }
}
