package com.example.obd.Frag


import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AlertDialog
import android.text.InputFilter
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import com.example.obd.MainPeace

import com.orange.obd.R
import com.example.obd.util.Util_CustomTextWatcher
import com.example.obd.util.Util_FtpManager.DownS19
import com.orango.electronic.orangetxusb.mmySql.Util_MmySql_ItemDAO
import kotlinx.android.synthetic.main.activity_main_peace.view.*
import kotlinx.android.synthetic.main.fragment_key__id.*
import kotlinx.android.synthetic.main.fragment_key__id.view.*
import kotlinx.android.synthetic.main.fragment_key__id.view.Lf
import kotlinx.android.synthetic.main.fragment_key__id.view.Lft
import kotlinx.android.synthetic.main.fragment_key__id.view.Lr
import kotlinx.android.synthetic.main.fragment_key__id.view.Lrt
import kotlinx.android.synthetic.main.fragment_key__id.view.Rf
import kotlinx.android.synthetic.main.fragment_key__id.view.Rft
import kotlinx.android.synthetic.main.fragment_key__id.view.Rr
import kotlinx.android.synthetic.main.fragment_key__id.view.Rrt
import kotlinx.android.synthetic.main.fragment_key__id.view.program
import java.util.ArrayList
import com.orange.blelibrary.blelibrary.CallBack.Dailog_SetUp_C
import com.orange.blelibrary.blelibrary.RootActivity
import com.orange.blelibrary.blelibrary.RootFragement


class Frag_Fcnction_Key_ID : RootFragement(R.layout.fragment_key__id)
{
    lateinit var mainPeace:MainPeace
    var first=true
    var ScanLf=""
    var ScanRf=""
    var ScanRr=""
    var ScanLr=""
    var ScanSp=""
    var scanner= Frag_Function_QrcodeScanner()
    val utilMmySqlItemDAO: Util_MmySql_ItemDAO by lazy { Util_MmySql_ItemDAO(activity!!) }
    lateinit var directfit:String
    var ShowSelect=true
    var SCAN_OR_KEY=1
    var need=8
    var ProgranFinsh=false

    override fun ViewInit() {
        mainPeace=activity as MainPeace

        if(ShowSelect){rootview.Select_Key.visibility=View.VISIBLE}else{rootview.Select_Key.visibility=View.GONE}
        if(SCAN_OR_KEY==0){
            scanner.Scan_For=scanner.ID
            rootview.Lft.isFocusable=false
            rootview.Rrt.isFocusable=false
            rootview.Rft.isFocusable=false
            rootview.Lrt.isFocusable=false
            rootview.lrt3.isFocusable=false
            rootview.Lft.setOnClickListener {
                scanner.place=scanner.LF
                scanner.edit=rootview.Lft
                RequestPermission() }
            rootview.Rrt.setOnClickListener {
                scanner.place=scanner.Rr
                scanner.edit=rootview.Rrt
                RequestPermission()
            }
            rootview.Rft.setOnClickListener {
                scanner.place=scanner.Rf
                scanner.edit=rootview.Rft
                RequestPermission()
            }
            rootview.Lrt.setOnClickListener {
                scanner.place=scanner.Lr
                scanner.edit=rootview.Lrt
                RequestPermission() }
            rootview.lrt3.setOnClickListener {
                scanner.place=scanner.Sp
                scanner.edit=rootview.lrt3
                RequestPermission() }
        }
        var make=mainPeace.SelectMake
        var model=mainPeace.SelectModel
        var year=mainPeace.SelectYear
        directfit=utilMmySqlItemDAO.getPart(make, model, year).directfit
        rootview.mmy_text.text="$make/$model/$year"
        scanner.idcopy=this
        scanner.need=need
        rootview.Lft.addTextChangedListener(Util_CustomTextWatcher(rootview.Lft, need))
        rootview.Rft.addTextChangedListener(Util_CustomTextWatcher(rootview.Rft, need))
        rootview.Lrt.addTextChangedListener(Util_CustomTextWatcher(rootview.Lrt, need))
        rootview.Rrt.addTextChangedListener(Util_CustomTextWatcher(rootview.Rrt, need))
        rootview.lrt3.addTextChangedListener(Util_CustomTextWatcher(rootview.lrt3, need))
        rootview.Lft.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(need)))
        rootview.lrt3.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(need)))
        rootview.Rft.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(need)))
        rootview.Lrt.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(need)))
        rootview.Rrt.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(need)))
        rootview.program.setOnClickListener {
            val write = ArrayList<String>()
            if (rootview.Lft.getText().length < 6 || rootview.Lft.getText().length > need) {
                return@setOnClickListener
            }
            if (rootview.Rft.getText().length < 6 || rootview.Rft.getText().length > need) {
                return@setOnClickListener
            }
            if (rootview.Lrt.getText().length < 6 || rootview.Lrt.getText().length > need) {
                return@setOnClickListener
            }
            if (rootview.Rrt.getText().length < 6 || rootview.Rrt.getText().length > need) {
                return@setOnClickListener
            }
            write.add(rootview.Rft.getText().toString())
            write.add(rootview.Rrt.getText().toString())
            write.add(rootview.Lrt.getText().toString())
            write.add(rootview.Lft.getText().toString())
            if (rootview.lrt3.getText().length >= 6 && rootview.lrt3.getText().length <= need) {
                write.add(rootview.lrt3.getText().toString())
            }
            act.ShowDaiLog(R.layout.dataloading,false,false, object : Dailog_SetUp_C(){
                override fun SetUP(root: Dialog, act: RootActivity) {
                    root.findViewById<TextView>(R.id.title).text=resources.getString(R.string.Programming)
                }
            })
            Thread{
                val iscuss=mainPeace.command.setTireId(write)
                handler.post {
                    act.DaiLogDismiss()
                    if(iscuss){
                        ProgranFinsh=true
                        updateui(SUCCESS)
                        ScanLf=Lft.text.toString()
                        ScanRr=Rrt.text.toString()
                        ScanRf=Rft.text.toString()
                        ScanLr=Lrt.text.toString()
                        ScanSp=lrt3.text.toString()
                        act.rootview.back.setImageResource(R.mipmap.btn_menu)
                        act.rootview.back.setOnClickListener { act.GoMenu() }
                        act.ShowDaiLog(R.layout.activity_take_out,true,false, Dailog_SetUp_C())
                    }else{
                        act.rootview.back.setImageResource(R.mipmap.btn_menu)
                        act.rootview.back.setOnClickListener { act.GoMenu() }
                        updateui(FAIL)
                    }
                }
            }.start()
        }
        rootview.scaner.setOnClickListener{
            rootview.Select_Key.visibility=View.GONE
            scanner.Scan_For=scanner.ID
            rootview.Lft.isFocusable=false
            rootview.Rrt.isFocusable=false
            rootview.Rft.isFocusable=false
            rootview.Lrt.isFocusable=false
            rootview.lrt3.isFocusable=false
            rootview.Lft.setOnClickListener {
                scanner.place=scanner.LF
                scanner.edit=rootview.Lft
                RequestPermission() }
            rootview.Rrt.setOnClickListener {
                scanner.place=scanner.Rr
                scanner.edit=rootview.Rrt
                RequestPermission()
            }
            rootview.Rft.setOnClickListener {
                scanner.place=scanner.Rf
                scanner.edit=rootview.Rft
                RequestPermission()
            }
            rootview.Lrt.setOnClickListener {
                scanner.place=scanner.Lr
                scanner.edit=rootview.Lrt
                RequestPermission() }
            rootview.lrt3.setOnClickListener {
                scanner.place=scanner.Sp
                scanner.edit=rootview.lrt3
                RequestPermission() }
            SCAN_OR_KEY=0
        }
        rootview.keyin.setOnClickListener {
            rootview.Select_Key.visibility=View.GONE
            rootview.Lft.setText("1234567")
            rootview.Rft.setText("1234567")
            rootview.Lrt.setText("1234567")
            rootview.Rrt.setText("1234567")
        }
        updateui(WAIT)
        rootview.Lft.setText(ScanLf)
        rootview.Rrt.setText(ScanRr)
        rootview.Rft.setText(ScanRf)
        rootview.Lrt.setText(ScanLr)
        rootview.lrt3.setText(ScanSp)
        if(mainPeace.utilMmySqlItemDAO.IsFiveTire(directfit)){rootview.lrt3.visibility=View.VISIBLE}else{rootview.lrt3.visibility=View.GONE}

    }

    override fun dispatchKeyEvent(event: KeyEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val SUCCESS=0
        val FAIL=1
        val WAIT=2
        var s19=""
    }

    override fun onResume() {
        super.onResume()
        first=true
        rootview.Lft.setText(ScanLf)
        rootview.Rrt.setText(ScanRr)
        rootview.Rft.setText(ScanRf)
        rootview.Lrt.setText(ScanLr)
        rootview.lrt3.setText(ScanSp)
        if(ProgranFinsh){
            updateui(SUCCESS)
        }else{
        rootview.Lft.setBackgroundResource( if (ScanLf.length == need) R.mipmap.icon_input_box_write else R.mipmap.icon_input_box_locked)
        rootview.Rrt.setBackgroundResource( if (ScanRr.length == need) R.mipmap.icon_input_box_write else R.mipmap.icon_input_box_locked)
        rootview.Rft.setBackgroundResource( if (ScanRf.length == need) R.mipmap.icon_input_box_write else R.mipmap.icon_input_box_locked)
        rootview.Lrt.setBackgroundResource( if (ScanLr.length == need) R.mipmap.icon_input_box_write else R.mipmap.icon_input_box_locked)
        rootview.lrt3.setBackgroundResource( if (ScanSp.length == need) R.mipmap.icon_input_box_write else R.mipmap.icon_input_box_locked)}
    }
    override fun onDestroy() {
        super.onDestroy()
        first=false
    }

    fun Downs19(){
        if(s19 ==directfit){
            act.DaiLogDismiss()
            return}
        handler.post { act.rootview.back.isEnabled=false }
        Thread{
           val a= DownS19(directfit,act)
                if(a){
                    if(!mainPeace.command.HandShake()){
                        mainPeace.command.Reboot()
                    }
                    val Pro=mainPeace.command.HandShake()&& mainPeace.command.WriteFlash(act,directfit,296,mainPeace)
                    handler.post {
                        act.rootview.back.isEnabled=true
                        if(Pro){
                            s19 =directfit
                        }else{
                            act.ShowDaiLog(R.layout.activity_re_program,true,true, Dailog_SetUp_C())
                            mainPeace.bleServiceControl.disconnect()
                            updateui(FAIL)
                        }
                        act.DaiLogDismiss()
                    }
                }else{
                    handler.post {
                        act.DaiLogDismiss()
                        act.rootview.back.isEnabled=true
                        act.ShowDaiLog(R.layout.activity_re_program,true,true, Dailog_SetUp_C())
                        mainPeace.bleServiceControl.disconnect()
                    }

                }
        }.start()
    }

    fun updateui(condition:Int){
        rootview.repr.visibility=View.GONE
        rootview.program.visibility=View.GONE
        when(condition){
            SUCCESS ->{
                rootview.condition.text=resources.getString(R.string.Programming_completed)
                rootview.condition.setTextColor(resources.getColor(R.color.buttoncolor))
                rootview.Lft.setBackgroundResource(R.mipmap.icon_input_box_ok)
                rootview.Rft.setBackgroundResource(R.mipmap.icon_input_box_ok)
                rootview.Lrt.setBackgroundResource(R.mipmap.icon_input_box_ok)
                rootview.Rrt.setBackgroundResource(R.mipmap.icon_input_box_ok)
                rootview.Lf.setBackgroundResource(R.mipmap.icon_tire_ok)
                rootview.Rf.setBackgroundResource(R.mipmap.icon_tire_ok)
                rootview.Lr.setBackgroundResource(R.mipmap.icon_tire_ok)
                rootview.Rr.setBackgroundResource(R.mipmap.icon_tire_ok)
                rootview.program.visibility=View.VISIBLE
            }
            FAIL ->{
                rootview.condition.text=resources.getString(R.string.Programming_failed)
                rootview.condition.setTextColor(resources.getColor(R.color.colorPrimary))
                rootview.Lft.setBackgroundResource(R.mipmap.icon_input_box_fail)
                rootview.Rft.setBackgroundResource(R.mipmap.icon_input_box_fail)
                rootview.Lrt.setBackgroundResource(R.mipmap.icon_input_box_fail)
                rootview.Rrt.setBackgroundResource(R.mipmap.icon_input_box_fail)
                rootview.Lf.setBackgroundResource(R.mipmap.icon_tire_fail)
                rootview.Rf.setBackgroundResource(R.mipmap.icon_tire_fail)
                rootview.Lr.setBackgroundResource(R.mipmap.icon_tire_fail)
                rootview.Rr.setBackgroundResource(R.mipmap.icon_tire_fail)
                rootview.program.visibility=View.VISIBLE
            }
            WAIT ->{
                rootview.condition.text=resources.getString(R.string.Key_in_the_original_sensor_ID_number)
                rootview.condition.setTextColor(resources.getColor(R.color.buttoncolor))
                rootview.Lft.setBackgroundResource(R.mipmap.icon_input_box_locked)
                rootview.Rft.setBackgroundResource(R.mipmap.icon_input_box_locked)
                rootview.Lrt.setBackgroundResource(R.mipmap.icon_input_box_locked)
                rootview.Rrt.setBackgroundResource(R.mipmap.icon_input_box_locked)
                rootview.Lf.setBackgroundResource(R.mipmap.icon_tire_normal)
                rootview.Rf.setBackgroundResource(R.mipmap.icon_tire_normal)
                rootview.Lr.setBackgroundResource(R.mipmap.icon_tire_normal)
                rootview.Rr.setBackgroundResource(R.mipmap.icon_tire_normal)
                rootview.program.visibility=View.VISIBLE
            }
        }
    }
    fun RequestPermission() {
        if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity!!,
                            Manifest.permission.CAMERA
                    )
            ) {
                AlertDialog.Builder(activity!!)
                        .setCancelable(false)
                        .setTitle("需要相機權限")
                        .setMessage("需要相機權限才能掃描BARCODE!")
                        .setPositiveButton(
                                "確認"
                        ) { dialogInterface, i ->
                            ActivityCompat.requestPermissions(
                                    activity!!,
                                    arrayOf(Manifest.permission.CAMERA),
                                    1
                            )
                        }
                        .show()
            } else {
                ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.CAMERA), 1)
            }
        }else{
            first=false
            act.ChangePage(scanner,R.id.frage,"Scanner",true)
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 ->
                if (grantResults.isNotEmpty()) {
                    for (i in grantResults.indices) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            first=false
                            act.ChangePage(scanner,R.id.frage,"Scanner",true)
                        }
                    }
                }
        }
    }
}
