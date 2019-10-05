package com.example.obd.Adapter

import android.app.Activity
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.obd.FunctionPage.OBDII_relearn
import com.example.obd.MainActivity.MainPeace
import com.example.obd.MainActivity.Selection
import com.orange.obd.R
import java.util.ArrayList

class FavAdapter(private val name: ArrayList<String>, val act: Activity, private val fragmentManager: FragmentManager)
    : RecyclerView.Adapter<FavAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.favorite_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text=name.get(position).replace("☆","/")
        holder.mView.setOnClickListener {
            (act as MainPeace).SelectYear=name[position].split("☆")[2]
            act.SelectModel=name[position].split("☆")[1]
            act.SelectMake=name[position].split("☆")[0]
            act.GoScanner(OBDII_relearn(),10,R.id.frage)
        }
        holder.delete.setOnClickListener {
            name.removeAt(position)
            this.notifyDataSetChanged()
            Log.d("data","$name/${name.size}")
            val profilePreferences =holder.mView.context.getSharedPreferences("Favorite", Context.MODE_PRIVATE)
            profilePreferences.edit().putInt("count",name.size).commit()
            for(i in 0 until name.size){
                profilePreferences.edit().putString("$i",name[i]).commit()
            }
        }
    }

    override fun getItemCount(): Int = name.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val  name:TextView = mView.findViewById(R.id.textView22)
        val delete:ImageView=mView.findViewById(R.id.imageView2)
        override fun toString(): String {
            return super.toString() + " ''"
        }
    }

}