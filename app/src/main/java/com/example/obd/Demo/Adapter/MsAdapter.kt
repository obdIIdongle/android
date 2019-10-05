package com.example.obd.Demo.Adapter


import android.support.v7.widget.RecyclerView

import android.view.LayoutInflater

import android.view.View

import android.view.ViewGroup
import android.widget.TextView
import com.orange.obd.R
import java.util.ArrayList


class MsAdapter(private val a:ArrayList<String>)
    : RecyclerView.Adapter<MsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
holder.text.text=a[position]
    }

    override fun getItemCount(): Int = a.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val text: TextView = mView.findViewById(R.id.msgtext)
        override fun toString(): String {
            return super.toString() + " ''"
        }
    }
}