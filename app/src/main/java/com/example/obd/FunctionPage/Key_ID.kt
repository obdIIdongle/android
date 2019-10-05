package com.example.obd.FunctionPage


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.obd.MainActivity.HomeFragement
import com.example.obd.MainActivity.MainPeace
import com.example.obd.MainActivity.QrcodeScanner
import com.example.obd.MainActivity.TakeOut
import com.example.obd.Myapp

import com.orange.obd.R
import com.example.obd.tool.CustomTextWatcher
import com.example.obd.tool.FtpManager.DownS19
import com.orango.electronic.orangetxusb.mmySql.ItemDAO
import kotlinx.android.synthetic.main.fragment_key__id.*
import kotlinx.android.synthetic.main.fragment_key__id.view.*
import kotlinx.android.synthetic.main.fragment_key__id.view.Lft
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Key_ID : Fragment() {
    lateinit var rootView: View
    lateinit var act: MainPeace
    companion object {
        val SUCCESS=0
        val FAIL=1
        val WAIT=2
        var s19=""
    }
    var first=true
    var ScanLf=""
    var ScanRf=""
    var ScanRr=""
    var ScanLr=""
    var ScanSp=""
    var scanner= QrcodeScanner()
    val itemDAO: ItemDAO by lazy { ItemDAO(activity!!) }
    lateinit var directfit:String
    var ShowSelect=true
    var SCAN_OR_KEY=1
    var need=8
    var ProgranFinsh=false
    override fun onResume() {
        super.onResume()
        first=true
        rootView.Lft.setText(ScanLf)
        rootView.Rrt.setText(ScanRr)
        rootView.Rft.setText(ScanRf)
        rootView.Lrt.setText(ScanLr)
        rootView.lrt3.setText(ScanSp)
        if(ProgranFinsh){
            updateui(SUCCESS)
        }else{
        rootView.Lft.setBackgroundResource( if (ScanLf.length == need) R.mipmap.icon_input_box_write else R.mipmap.icon_input_box_locked)
        rootView.Rrt.setBackgroundResource( if (ScanRr.length == need) R.mipmap.icon_input_box_write else R.mipmap.icon_input_box_locked)
        rootView.Rft.setBackgroundResource( if (ScanRf.length == need) R.mipmap.icon_input_box_write else R.mipmap.icon_input_box_locked)
        rootView.Lrt.setBackgroundResource( if (ScanLr.length == need) R.mipmap.icon_input_box_write else R.mipmap.icon_input_box_locked)
        rootView.lrt3.setBackgroundResource( if (ScanSp.length == need) R.mipmap.icon_input_box_write else R.mipmap.icon_input_box_locked)}
    }
    override fun onDestroy() {
        super.onDestroy()
        first=false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        act=activity!!as MainPeace
        retainInstance=true
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView=inflater.inflate(R.layout.fragment_key__id, container, false)
        if(ShowSelect){rootView.Select_Key.visibility=View.VISIBLE}else{rootView.Select_Key.visibility=View.GONE}
        if(SCAN_OR_KEY==0){
            scanner.Scan_For=scanner.ID
            rootView.Lft.isFocusable=false
            rootView.Rrt.isFocusable=false
            rootView.Rft.isFocusable=false
            rootView.Lrt.isFocusable=false
            rootView.lrt3.isFocusable=false
            rootView.Lft.setOnClickListener {
                scanner.place=scanner.LF
                scanner.edit=rootView.Lft
                RequestPermission() }
            rootView.Rrt.setOnClickListener {
                scanner.place=scanner.Rr
                scanner.edit=rootView.Rrt
                RequestPermission()
            }
            rootView.Rft.setOnClickListener {
                scanner.place=scanner.Rf
                scanner.edit=rootView.Rft
                RequestPermission()
            }
            rootView.Lrt.setOnClickListener {
                scanner.place=scanner.Lr
                scanner.edit=rootView.Lrt
                RequestPermission() }
            rootView.lrt3.setOnClickListener {
                scanner.place=scanner.Sp
                scanner.edit=rootView.lrt3
                RequestPermission() }
        }
        var make=act.SelectMake
        var model=act.SelectModel
        var year=act.SelectYear
         directfit=itemDAO.getPart(make, model, year).directfit
        rootView.mmy_text.text="$make/$model/$year"
        scanner.Idcopy=this

        scanner.need=need
        rootView.Lft.addTextChangedListener(CustomTextWatcher(rootView.Lft,need))
        rootView.Rft.addTextChangedListener(CustomTextWatcher(rootView.Rft,need))
        rootView.Lrt.addTextChangedListener(CustomTextWatcher(rootView.Lrt,need))
        rootView.Rrt.addTextChangedListener(CustomTextWatcher(rootView.Rrt,need))
        rootView.lrt3.addTextChangedListener(CustomTextWatcher(rootView.lrt3,need))
        rootView.Lft.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(need)))
        rootView.lrt3.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(need)))
        rootView.Rft.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(need)))
        rootView.Lrt.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(need)))
        rootView.Rrt.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(need)))
        rootView.program.setOnClickListener {
            val write = ArrayList<String>()
            if (rootView.Lft.getText().length < 6 || rootView.Lft.getText().length > need) {
                return@setOnClickListener
            }
            if (rootView.Rft.getText().length < 6 || rootView.Rft.getText().length > need) {
                return@setOnClickListener
            }
            if (rootView.Lrt.getText().length < 6 || rootView.Lrt.getText().length > need) {
                return@setOnClickListener
            }
            if (rootView.Rrt.getText().length < 6 || rootView.Rrt.getText().length > need) {
                return@setOnClickListener
            }
            write.add(rootView.Rft.getText().toString())
            write.add(rootView.Rrt.getText().toString())
            write.add(rootView.Lrt.getText().toString())
            write.add(rootView.Lft.getText().toString())
            if (rootView.lrt3.getText().length >= 6 && rootView.lrt3.getText().length <= need) {
                write.add(rootView.lrt3.getText().toString())
            }
            act.LoadBleUI(resources.getString(R.string.Programming))
            Thread{
                val iscuss=act.command.setTireId(write)
                handler.post {
                    act.LoadingSuccessUI()
                    if(iscuss){
                        ProgranFinsh=true
                        updateui(SUCCESS)
                        ScanLf=Lft.text.toString()
                        ScanRr=Rrt.text.toString()
                        ScanRf=Rft.text.toString()
                        ScanLr=Lrt.text.toString()
                        ScanSp=lrt3.text.toString()
                        TakeOut.DS_OR_CO=1
                        act.startActivity(Intent(act,TakeOut::class.java))
                    }else{
                        updateui(FAIL)
                    }
                }
            }.start()
        }
        rootView.scaner.setOnClickListener{
            act.LoadBleUI(resources.getString(R.string.Data_Loading))
            Downs19()
            rootView.Select_Key.visibility=View.GONE
            scanner.Scan_For=scanner.ID
            rootView.Lft.isFocusable=false
            rootView.Rrt.isFocusable=false
            rootView.Rft.isFocusable=false
            rootView.Lrt.isFocusable=false
            rootView.lrt3.isFocusable=false
            rootView.Lft.setOnClickListener {
                scanner.place=scanner.LF
                scanner.edit=rootView.Lft
                RequestPermission() }
            rootView.Rrt.setOnClickListener {
                scanner.place=scanner.Rr
                scanner.edit=rootView.Rrt
                RequestPermission()
            }
            rootView.Rft.setOnClickListener {
                scanner.place=scanner.Rf
                scanner.edit=rootView.Rft
                RequestPermission()
            }
            rootView.Lrt.setOnClickListener {
                scanner.place=scanner.Lr
                scanner.edit=rootView.Lrt
                RequestPermission() }
            rootView.lrt3.setOnClickListener {
                scanner.place=scanner.Sp
                scanner.edit=rootView.lrt3
                RequestPermission() }
            SCAN_OR_KEY=0
        }
        rootView.keyin.setOnClickListener {     act.LoadBleUI(resources.getString(R.string.Data_Loading))
            Downs19()
            rootView.Select_Key.visibility=View.GONE}
        updateui(WAIT)
        rootView.Lft.setText(ScanLf)
        rootView.Rrt.setText(ScanRr)
        rootView.Rft.setText(ScanRf)
        rootView.Lrt.setText(ScanLr)
        rootView.lrt3.setText(ScanSp)
        if(act.itemDAO.IsFiveTire(directfit)){rootView.lrt3.visibility=View.VISIBLE}else{rootView.lrt3.visibility=View.GONE}
        rootView.repr.setOnClickListener {
           act.supportFragmentManager.popBackStack()
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.frage,Key_ID() )
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                    .addToBackStack(null)
                    .commit()
        }
        return rootView
    }
    fun Downs19(){
        if(s19==directfit){
            act.LoadingSuccessUI()
            return}
        handler.post { act.back.isEnabled=false }
        Thread{
           val a= DownS19(directfit,act)
                if(a){

                    if(!act.command.HandShake()){
                        act.command.Reboot()
                    }
                    val Pro=act.command.HandShake()&& act.command.WriteFlash(act,directfit,296,act)
                    handler.post {
                        act.back.isEnabled=true
                        if(Pro){
//                            Toast.makeText(activity,"燒錄成功",Toast.LENGTH_SHORT).show();
                            s19=directfit
                        }else{
//                            Toast.makeText(activity,"燒錄失敗",Toast.LENGTH_SHORT).show();
                            val intent = Intent(act,ReProgram::class.java)
                            startActivity(intent)
                            updateui(FAIL)
                        }
                        act.LoadingSuccessUI()
                    }
                }else{
                    handler.post {
                        act.LoadingSuccessUI()
                        act.back.isEnabled=true
                        val intent = Intent(act,ReProgram::class.java)
                        startActivity(intent)}

                }
        }.start()
    }
var handler=Handler()
    fun updateui(condition:Int){
        rootView.repr.visibility=View.GONE
        when(condition){
            SUCCESS->{
                rootView.condition.text=resources.getString(R.string.Programming_completed)
                rootView.condition.setTextColor(resources.getColor(R.color.buttoncolor))
                rootView.Lft.setBackgroundResource(R.mipmap.icon_input_box_ok)
                rootView.Rft.setBackgroundResource(R.mipmap.icon_input_box_ok)
                rootView.Lrt.setBackgroundResource(R.mipmap.icon_input_box_ok)
                rootView.Rrt.setBackgroundResource(R.mipmap.icon_input_box_ok)
                rootView.Lf.setBackgroundResource(R.mipmap.icon_tire_ok)
                rootView.Rf.setBackgroundResource(R.mipmap.icon_tire_ok)
                rootView.Lr.setBackgroundResource(R.mipmap.icon_tire_ok)
                rootView.Rr.setBackgroundResource(R.mipmap.icon_tire_ok)
                rootView.repr.visibility=View.VISIBLE
            }
            FAIL->{
                rootView.condition.text=resources.getString(R.string.Programming_failed)
                rootView.condition.setTextColor(resources.getColor(R.color.colorPrimary))
                rootView.Lft.setBackgroundResource(R.mipmap.icon_input_box_fail)
                rootView.Rft.setBackgroundResource(R.mipmap.icon_input_box_fail)
                rootView.Lrt.setBackgroundResource(R.mipmap.icon_input_box_fail)
                rootView.Rrt.setBackgroundResource(R.mipmap.icon_input_box_fail)
                rootView.Lf.setBackgroundResource(R.mipmap.icon_tire_fail)
                rootView.Rf.setBackgroundResource(R.mipmap.icon_tire_fail)
                rootView.Lr.setBackgroundResource(R.mipmap.icon_tire_fail)
                rootView.Rr.setBackgroundResource(R.mipmap.icon_tire_fail)
            }
            WAIT->{
                rootView.condition.text=resources.getString(R.string.Key_in_the_original_sensor_ID_number)
                rootView.condition.setTextColor(resources.getColor(R.color.buttoncolor))
                rootView.Lft.setBackgroundResource(R.mipmap.icon_input_box_locked)
                rootView.Rft.setBackgroundResource(R.mipmap.icon_input_box_locked)
                rootView.Lrt.setBackgroundResource(R.mipmap.icon_input_box_locked)
                rootView.Rrt.setBackgroundResource(R.mipmap.icon_input_box_locked)
                rootView.Lf.setBackgroundResource(R.mipmap.icon_tire_normal)
                rootView.Rf.setBackgroundResource(R.mipmap.icon_tire_normal)
                rootView.Lr.setBackgroundResource(R.mipmap.icon_tire_normal)
                rootView.Rr.setBackgroundResource(R.mipmap.icon_tire_normal)
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
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.frage, scanner, "Scanner")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                    .addToBackStack("Scanner")
                    // 提交事務
                    .commit()}
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
                            val transaction = fragmentManager!!.beginTransaction()
                            transaction.replace(R.id.frage, scanner, "Scanner")
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                                    .addToBackStack("Scanner")
                                    // 提交事務
                                    .commit()

                        }
                    }
                }
        }
    }
}
