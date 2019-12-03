package com.example.obd.Adapter


import android.app.Activity
import android.os.Bundle

import com.example.obd.MainPeace
import com.example.obd.Frag.Frag_SelectMmyPage_YearFragement
import com.orange.blelibrary.blelibrary.RootAdapter
import com.orange.obd.R
import com.orange_electronic.orangeobd.mmySql.Util_MmySql_module
import kotlinx.android.synthetic.main.select_item.view.*
import java.util.ArrayList

class Ad_Model(private val name:ArrayList<Util_MmySql_module>, val act: Activity)
    : RootAdapter(R.layout.select_item) {

    override fun SizeInit(): Int {
        return name.size
    }

    override fun onBindViewHolder(holder: RootAdapter.ViewHolder, position: Int)
    {
        holder.mView.text_item.text=name[position].modele
        holder.mView.select_img.setOnClickListener {
            val args = Bundle()
            (act as MainPeace).SelectMake=name[position].make
            act.SelectModel= name[position].modele
            act.ChangePage(Frag_SelectMmyPage_YearFragement(),R.id.frage,"Frag_SelectMmyPage_YearFragement",true)
        }
    }

}