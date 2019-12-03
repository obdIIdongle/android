package com.example.obd.Adapter

import android.app.Activity

import com.example.obd.OBD_Relearn.Frag_Function_OBDII_relearn
import com.example.obd.MainPeace
import com.orange.blelibrary.blelibrary.RootAdapter
import com.orange.obd.R
import com.orange_electronic.orangeobd.mmySql.Util_MmySql_module
import com.orango.electronic.orangetxusb.mmySql.Util_MmySql_ItemDAO
import kotlinx.android.synthetic.main.select_item.view.*
import java.util.ArrayList


class Ad_Year(private val a:ArrayList<Util_MmySql_module>, val act: Activity)
    : RootAdapter(R.layout.select_item){
    override fun SizeInit(): Int {
        return a.size
    }

    override fun onBindViewHolder(holder: RootAdapter.ViewHolder, position: Int) {
        holder.mView.text_item.text=a[position].year
        holder.mView.select_img.setOnClickListener {
            (act as MainPeace).SelectYear=a[position].year
            act.SelectModel=a[position].modele
            act.SelectMake=a[position].make
            act.ChangePage(Frag_Function_OBDII_relearn(),R.id.frage,"Frag_Function_OBDII_relearn",true)
        }
    }

    val utilMmySqlItemDAO: Util_MmySql_ItemDAO by lazy { Util_MmySql_ItemDAO(act.applicationContext) }

}