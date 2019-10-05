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
import com.example.obd.MainActivity.HomeFragement
import com.example.obd.MainActivity.MainPeace
import com.example.obd.MainActivity.ModelFragement
import com.orange.obd.R
import com.orange_electronic.orangeobd.mmySql.module
import java.util.ArrayList


class makeadapter(private val a:ArrayList<module>,val act: Activity,private val fragmentManager: FragmentManager)
    : RecyclerView.Adapter<makeadapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.make_grid_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
holder.image.setImageResource(holder.itemView.resources.getIdentifier(a[position].image,"mipmap",holder.itemView.context.packageName))
        holder.image.setOnClickListener {
            (act as MainPeace).SelectMake=a[position].make
            val transaction = fragmentManager.beginTransaction()
            val fragement=ModelFragement()
            transaction.replace(R.id.frage, fragement)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun getItemCount(): Int = a.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val image: ImageView = mView.findViewById(R.id.image)

        override fun toString(): String {
            return super.toString() + " ''"
        }
    }
}