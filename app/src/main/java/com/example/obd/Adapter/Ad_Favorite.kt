package com.example.obd.Adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.obd.OBD_Relearn.Frag_Function_OBDII_relearn
import com.example.obd.MainPeace
import com.orange.blelibrary.blelibrary.RootAdapter
import com.orange.obd.R
import kotlinx.android.synthetic.main.favorite_adapter.view.*
import java.util.ArrayList

class Ad_Favorite(private val name: ArrayList<String>, val act: Activity)
    : RootAdapter(R.layout.favorite_adapter) {
    override fun SizeInit(): Int {
        return name.size
    }
    override fun onBindViewHolder(holder: RootAdapter.ViewHolder, position: Int) {
        holder.mView.name.text=name.get(position).replace("☆","/")
        holder.mView.setOnClickListener {
            (act as MainPeace).SelectYear=name[position].split("☆")[2]
            act.SelectModel=name[position].split("☆")[1]
            act.SelectMake=name[position].split("☆")[0]
            act.GoScanner(Frag_Function_OBDII_relearn(),10,R.id.frage,"Frag_Function_OBDII_relearn")
        }
        holder.mView.delete.setOnClickListener {
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
}