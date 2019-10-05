package com.example.obd.Demo.Adapter


import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.support.v7.widget.RecyclerView

import android.view.LayoutInflater

import android.view.View

import android.view.ViewGroup
import android.widget.TextView
import com.example.obd.Demo.MainActivity
import com.example.obd.Demo.MainActivity.*
import com.orange.obd.R
import java.util.ArrayList


class SelectBle(private val a:ArrayList<BluetoothDevice>,private val activity: Activity)
    : RecyclerView.Adapter<SelectBle.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.selectble, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(a[position].name==null){holder.device.text="未知的裝置"}else{holder.device.text=a[position].name}

        holder.address.text=a[position].address
        holder.mView.setOnClickListener(View.OnClickListener {
            scan.scanLeDevice(false)
            bleServiceControl.connect(a[position].address, MainActivity.activity)
            message.add("Trying to connect  for "+a[position].name)
            adapter.notifyDataSetChanged()
            blename=a[position].name
            MainActivity.re.scrollToPosition(message.size - 1)
            activity.finish()
        })
    }

    override fun getItemCount(): Int = a.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val device: TextView = mView.findViewById(R.id.textView)
        val address: TextView = mView.findViewById(R.id.textView2)
        override fun toString(): String {
            return super.toString() + " ''"
        }
    }
}