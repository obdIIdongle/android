package com.example.obd.MainActivity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.*
import com.airbnb.lottie.LottieAnimationView
import com.example.obd.FunctionPage.Key_ID
import com.example.obd.Myapp
import com.example.obd.TestFragement
import com.orange.obd.R
import com.example.obd.tool.Command
import com.example.obd.tool.FtpManager
import com.example.obd.tool.LanguageUtil
import com.orange.blelibrary.blelibrary.BleActivity
import com.orange.etalkinglibrary.E_talking.TalkingActivity
import com.orango.electronic.orangetxusb.SettingPagr.PrivaryPolicy
import com.orango.electronic.orangetxusb.SettingPagr.Set_Languages
import com.orango.electronic.orangetxusb.mmySql.ItemDAO
import kotlinx.android.synthetic.main.fragment_test_fragement.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import kotlin.concurrent.schedule

open class MainPeace : BleActivity() {
    val itemDAO: ItemDAO by lazy { ItemDAO(applicationContext) }
    override fun onBackStackChanged() {
        if(supportFragmentManager.fragments.get(supportFragmentManager.fragments.size-1).tag!=null&&supportFragmentManager.fragments.get(supportFragmentManager.fragments.size-1).tag=="Home"){
            val tname=supportFragmentManager.fragments.get(supportFragmentManager.fragments.size-1).tag!!
        Log.d("name",tname)
            back.visibility=View.GONE
        }else{
            back.visibility=View.VISIBLE
        }
    }
    var iBinder:IBinder?=null
    var command= Command()
    var SelectMake=""
    var SelectModel=""
    var SelectYear=""
    lateinit var feage:FrameLayout
    lateinit var textView: TextView
    lateinit var back: ImageView
lateinit var load:RelativeLayout
    private var savedState: Bundle? = null
    lateinit var anim: LottieAnimationView
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_peace)
        (application as Myapp).act=this
        init()
        back=findViewById(R.id.imageView13)
        feage=findViewById(R.id.frage)
        load=findViewById(R.id.load)
        textView=findViewById(R.id.textView11)
        anim=findViewById(R.id.animation_view)
        savedState = savedInstanceState
        if(savedState != null) onBackStackChanged()
        command.act=this
        LoadingUI(resources.getString(R.string.Data_Loading))
        DonloadMMy()
        val profilePreferences = getSharedPreferences("Setting", Context.MODE_PRIVATE)
        val handler = Handler()
        if(profilePreferences.getString("admin","nodata").equals("nodata")){
            Set_Languages.place=0
            PrivaryPolicy.place=0
                feage.setBackgroundColor(resources.getColor(R.color.backgroung))
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frage, Set_Languages())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                        .commit()
        }else{
                feage.setBackgroundColor(R.color.backgroung)
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frage, HomeFragement(),"Home")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                        .commit()
        }
    }
fun DonloadMMy(){
    Thread{
        var a=FtpManager.DownMMy(this)
        if(a){
            handler.post {
                LoadingSuccessUI()
            }
        }else{
            DonloadMMy()
        }
    }.start()
}
    override fun ConnectSituation(boolean: Boolean){
        if (boolean) {
            Log.d("連線","連線ok")
        } else {
            Log.d("連線","Bluetooth is disconnected")
            LoadingSuccessUI()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frage, HomeFragement(),"Home")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                    .commit()
            Key_ID.s19=""
            TakeOut.DS_OR_CO=0
            startActivity(Intent(this,TakeOut::class.java))
        }
    }

    override fun TX(a: String) {
        handler.post {
            if(supportFragmentManager.fragments.get(supportFragmentManager.fragments.size-1).tag!=null&&supportFragmentManager.fragments.get(supportFragmentManager.fragments.size-1).tag=="Test"){
                var fragment=supportFragmentManager.fragments.get(supportFragmentManager.fragments.size-1) as TestFragement
                fragment.rootview.teststring.append("\nTX:"+a)
            }
        }

    }
    override fun RX(a: String) {
        handler.post {
            if(supportFragmentManager.fragments.get(supportFragmentManager.fragments.size-1).tag!=null&&supportFragmentManager.fragments.get(supportFragmentManager.fragments.size-1).tag=="Test"){
                var fragment=supportFragmentManager.fragments.get(supportFragmentManager.fragments.size-1) as TestFragement
                fragment.rootview.teststring.append("\nRX:"+a)
            }
        }
    }
    override fun LoadingUI(a:String){   textView.text=a
        load.visibility= View.VISIBLE
        anim.visibility=View.VISIBLE}
    override fun LoadingSuccessUI(){
        load.visibility= View.GONE
        anim.visibility=View.GONE
    }
    fun onclick(view: View){
        when(view.id){
            R.id.imageView3->{     var intent=Intent(this,Logout::class.java)
                startActivity(intent)}
            R.id.imageView13->{
                goback()
            }
            R.id.imageView40->{
                startActivity(Intent(this,TalkingActivity::class.java))
            }
        } }
}
