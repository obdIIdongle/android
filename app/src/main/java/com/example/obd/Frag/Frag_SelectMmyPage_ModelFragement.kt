package com.example.obd.Frag

import android.view.KeyEvent
import com.example.obd.MainPeace
import com.orange.obd.R
import com.example.obd.Adapter.Ad_Model
import com.orange.blelibrary.blelibrary.RootFragement
import com.orango.electronic.orangetxusb.mmySql.Util_MmySql_ItemDAO
import kotlinx.android.synthetic.main.activity_select_model.view.*

class Frag_SelectMmyPage_ModelFragement : RootFragement(R.layout.activity_select_model) {

    val utilMmySqlItemDAO: Util_MmySql_ItemDAO by lazy { Util_MmySql_ItemDAO(activity!!) }

    override fun ViewInit() {
        rootview.re.layoutManager= androidx.recyclerview.widget.GridLayoutManager(activity, 2)
        rootview.re.adapter= Ad_Model(utilMmySqlItemDAO.getmodel((activity as MainPeace).SelectMake)!!,activity!!)
    }

    override fun dispatchKeyEvent(event: KeyEvent?) {

    }
    //lateinit var  re: androidx.recyclerview.widget.RecyclerView

}
