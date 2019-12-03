package com.example.obd.Adapter

import android.app.Activity

import com.example.obd.MainPeace
import com.example.obd.Frag.Frag_SelectMmyPage_ModelFragement
import com.orange.blelibrary.blelibrary.RootAdapter
import com.orange.obd.R
import com.orange_electronic.orangeobd.mmySql.Util_MmySql_module
import kotlinx.android.synthetic.main.make_grid_item.view.*
import java.util.ArrayList

class Ad_Make(private val name:ArrayList<Util_MmySql_module>, val act: Activity)
    : RootAdapter(R.layout.make_grid_item) {
    override fun SizeInit(): Int {
        return name.size
    }

    override fun onBindViewHolder(holder: RootAdapter.ViewHolder, position: Int) {
        holder.mView.image.setImageResource(holder.itemView.resources.getIdentifier(name[position].image,"mipmap",holder.itemView.context.packageName))
        holder.mView.image.setOnClickListener {
            (act as MainPeace).SelectMake=name[position].make
            act.ChangePage(Frag_SelectMmyPage_ModelFragement(),R.id.frage,"Frag_SelectMmyPage_ModelFragement",true)
        }
    }

}