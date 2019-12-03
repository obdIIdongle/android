package com.example.obd.Frag


import android.Manifest
import android.app.Instrumentation
import android.content.pm.PackageManager
import android.os.Handler
import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AlertDialog
import android.util.Log
import android.view.KeyEvent
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.obd.MainPeace
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.orange.blelibrary.blelibrary.RootFragement
import com.orange.obd.R
import kotlinx.android.synthetic.main.fragment_qrcode_scanner.view.*

import me.dm7.barcodescanner.zxing.ZXingScannerView

class Frag_Function_QrcodeScanner : RootFragement(R.layout.fragment_qrcode_scanner), ZXingScannerView.ResultHandler{

    var S19=0
    var ID=1
    var need=8
    var Scan_For=S19
    val LF=1
    val Rf=2
    val Rr=3
    val Lr=4
    val Sp=5
    var place=0
    lateinit var idcopy: Frag_Fcnction_Key_ID
    lateinit var edit:EditText

    private var mScannerView: ZXingScannerView? = null
    var ALL_FORMATS: ArrayList<BarcodeFormat> = ArrayList(1)

    lateinit var frame: ViewGroup
    lateinit var mainPeace: MainPeace
    internal var formats: List<BarcodeFormat>? = null

    override fun ViewInit() {
        RequestPermission()
        mainPeace=activity!! as MainPeace
        mScannerView = ZXingScannerView(activity)
        ALL_FORMATS.add(BarcodeFormat.DATA_MATRIX)
        mScannerView!!.setFormats(ALL_FORMATS)
        mScannerView!!.resumeCameraPreview(this)
        mScannerView!!.setAutoFocus(true)
        mScannerView!!.setAspectTolerance(0.0f)

        rootview.frame.addView(mScannerView)
    }

    override fun dispatchKeyEvent(event: KeyEvent?) {
        
    }
    
    override fun handleResult(rawResult: Result?) {
//        rootview.textView12.text="Contents = " + rawResult!!.getText()
        if(Scan_For==ID){
            val contents=rawResult!!.text.split("*")
            if(contents.size==3&&contents[1].length== need){
                edit.setText(contents[1])
                Log.d("place",""+place)
                when(place){
                    LF->{idcopy.ScanLf=contents[1]}
                    Rf->{idcopy.ScanRf=contents[1]}
                    Rr->{idcopy.ScanRr=contents[1]}
                    Lr->{idcopy.ScanLr=contents[1]}
                    Sp->{idcopy.ScanSp=contents[1]}
                }
                idcopy.ShowSelect=false
                Thread{
                    try{
                        var inst = Instrumentation()
                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK)
                    }
                    catch ( e:java.lang.Exception) {
                        Log.e("Exception when onBack", e.toString())
                    }
                }.start()
            }else{
                val handler = Handler()
                Toast.makeText(act,act.resources.getString(R.string.Please_scan_the_correct_QR_code),Toast.LENGTH_SHORT).show()
                handler.postDelayed({ mScannerView!!.resumeCameraPreview(this) }, 2000)
            }
        }else{
            val contents=rawResult!!.text.split("*")
            if(contents.size==3){
                mainPeace.utilMmySqlItemDAO.GoOk(contents[0],fragmentManager!!,mainPeace)
            }

        }

    }

    override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this)
        mScannerView!!.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()
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
        }
    }
}
