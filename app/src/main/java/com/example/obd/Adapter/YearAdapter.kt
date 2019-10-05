package com.orange_electronic.orangeobd.Adapter


import android.app.Activity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.RecyclerView

import android.view.LayoutInflater

import android.view.View

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.obd.FunctionPage.Key_ID
import com.example.obd.FunctionPage.OBDII_relearn
import com.example.obd.MainActivity.HomeFragement
import com.example.obd.MainActivity.MainPeace
import com.orange.obd.R
import com.orange_electronic.orangeobd.mmySql.module
import com.orango.electronic.orangetxusb.mmySql.ItemDAO
import java.util.ArrayList


class YearAdapter(private val a:ArrayList<module>,val act: Activity,private val fragmentManager: FragmentManager)
    : RecyclerView.Adapter<YearAdapter.ViewHolder>() {
    val itemDAO: ItemDAO by lazy { ItemDAO(act.applicationContext) }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.select_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text=a[position].year
        holder.mView.setOnClickListener {
            (act as MainPeace).SelectYear=a[position].year
            act.SelectModel=a[position].modele
            act.SelectMake=a[position].make
            val transaction = fragmentManager.beginTransaction()
            val fragement= OBDII_relearn()
            transaction.replace(R.id.frage,fragement )
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                    .addToBackStack(null)
                    .commit()
        }
    }
    override fun getItemCount(): Int = a.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val text: TextView = mView.findViewById(R.id.text_item)
        val selectbutton:ImageView=mView.findViewById(R.id.select_img)
        override fun toString(): String {
            return super.toString() + " ''"
        }
    }
}