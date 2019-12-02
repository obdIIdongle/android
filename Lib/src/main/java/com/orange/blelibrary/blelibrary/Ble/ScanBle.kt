package com.orange.blelibrary.blelibrary.Ble

import android.app.Dialog
import android.bluetooth.BluetoothDevice
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast

import com.orange.blelibrary.R
import com.orange.blelibrary.blelibrary.CallBack.Ble_Callback_C
import com.orange.blelibrary.blelibrary.CallBack.Dailog_SetUp_C
import com.orange.blelibrary.blelibrary.RootActivity
import com.orange.blelibrary.blelibrary.Server.ScanDevice
import kotlinx.android.synthetic.main.activity_scan_ble.*

import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

import java.util.ArrayList


class ScanBle(val caller:Ble_Callback_C) : Dailog_SetUp_C() {

    internal var ble = ArrayList<BluetoothDevice>()
    internal var selectBle = SelectBle(ble, this)
    var scan = ScanDevice()
    lateinit var act: RootActivity

    override fun SetUP(root: Dialog, act: RootActivity) {
        this.act=act
        root.re.layoutManager = LinearLayoutManager(act)
        root.re.adapter = selectBle
        scan.scanBle=this
        scan.setmBluetoothAdapter(act)
        root.close.setOnClickListener {
            act.DaiLogDismiss()
        }
    }

    override fun Dismiss() {
        scan.scanLeDevice(false)
        super.Dismiss()
    }

    fun DataRefresh(a: BluetoothDevice) {
        try {
            if (!ble.contains(a) && a.name != null) {
                ble.add(a)
                selectBle.notifyDataSetChanged()
            }
        } catch (e: Exception) {
            Log.w("error", e.message)
        }

    }


}
