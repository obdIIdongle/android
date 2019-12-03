package com.example.obd.Frag

import android.view.KeyEvent
import com.example.obd.Adapter.Ad_Year
import com.example.obd.MainPeace
import com.orange.blelibrary.blelibrary.RootFragement
import com.orange.obd.R
import com.orango.electronic.orangetxusb.mmySql.Util_MmySql_ItemDAO
import kotlinx.android.synthetic.main.activity_year.view.*


class Frag_SelectMmyPage_YearFragement : RootFragement(R.layout.activity_year) {

    val utilMmySqlItemDAO: Util_MmySql_ItemDAO by lazy { Util_MmySql_ItemDAO(activity!!) }
    override fun ViewInit() {
        var make=(activity as MainPeace).SelectMake
        var model=(activity as MainPeace).SelectModel
        //re=rootView.findViewById(R.id.re)
        rootview.re.layoutManager= androidx.recyclerview.widget.GridLayoutManager(activity, 2)
        rootview.re.adapter= Ad_Year(utilMmySqlItemDAO.getYear(make,model)!!,activity!!)
    }

    override fun dispatchKeyEvent(event: KeyEvent?) {

    }

    //lateinit var re: androidx.recyclerview.widget.RecyclerView

}
