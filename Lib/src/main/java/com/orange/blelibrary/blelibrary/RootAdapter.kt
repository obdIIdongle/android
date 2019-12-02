package com.orange.blelibrary.blelibrary

import android.bluetooth.BluetoothDevice
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.orange.blelibrary.R
import org.greenrobot.eventbus.EventBus
import java.util.ArrayList

 abstract class RootAdapter(val layout:Int) : RecyclerView.Adapter<RootAdapter.ViewHolder>() {
     var handler= Handler()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(layout, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = SizeInit()

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        override fun toString(): String {
            return super.toString() + " ''"
        }
    }
     abstract fun SizeInit():Int;
     abstract override fun onBindViewHolder(holder: ViewHolder, position: Int) ;
}