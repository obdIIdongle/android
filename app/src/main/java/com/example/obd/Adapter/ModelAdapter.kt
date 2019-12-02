package com.example.obd.Adapter


import android.app.Activity
import android.os.Bundle

import android.view.LayoutInflater

import android.view.View

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.obd.MainPeace
import com.example.obd.SelectMmyPage.YearFragement
import com.orange.obd.R
import com.orange_electronic.orangeobd.mmySql.module
import java.util.ArrayList


class ModelAdapter(private val a:ArrayList<module>,val act: Activity,private val fragmentManager: androidx.fragment.app.FragmentManager)
    : androidx.recyclerview.widget.RecyclerView.Adapter<ModelAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.select_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text=a[position].modele
        holder.selectbutton.setOnClickListener {
            val args = Bundle()
            (act as MainPeace).SelectMake=a[position].make
             act.SelectModel= a[position].modele
            act.ChangePage(YearFragement(),R.id.frage,"YearFragement",true)
        }
    }

    override fun getItemCount(): Int = a.size

    inner class ViewHolder(val mView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mView) {
        val text: TextView = mView.findViewById(R.id.text_item)
        val selectbutton:ImageView=mView.findViewById(R.id.select_img)
        override fun toString(): String {
            return super.toString() + " ''"
        }
    }
}