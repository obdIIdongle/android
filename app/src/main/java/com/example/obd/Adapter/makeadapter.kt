package com.orange_electronic.orangeobd.Adapter


import android.app.Activity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater

import android.view.View

import android.view.ViewGroup
import android.widget.ImageView
import com.example.obd.MainPeace
import com.example.obd.SelectMmyPage.ModelFragement
import com.orange.obd.R
import com.orange_electronic.orangeobd.mmySql.module
import java.util.ArrayList


class makeadapter(private val a:ArrayList<module>,val act: Activity)
    : androidx.recyclerview.widget.RecyclerView.Adapter<makeadapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.make_grid_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
holder.image.setImageResource(holder.itemView.resources.getIdentifier(a[position].image,"mipmap",holder.itemView.context.packageName))
        holder.image.setOnClickListener {
            (act as MainPeace).SelectMake=a[position].make
            act.ChangePage(ModelFragement(),R.id.frage,"ModelFragement",true)
        }
    }

    override fun getItemCount(): Int = a.size

    inner class ViewHolder(val mView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mView) {
        val image: ImageView = mView.findViewById(R.id.image)

        override fun toString(): String {
            return super.toString() + " ''"
        }
    }
}