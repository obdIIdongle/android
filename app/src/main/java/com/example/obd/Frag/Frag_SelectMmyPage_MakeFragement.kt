package com.example.obd.Frag


import android.view.KeyEvent
import androidx.recyclerview.widget.GridLayoutManager
import com.example.obd.Adapter.Ad_Make
import com.orange.blelibrary.blelibrary.RootFragement
import com.orange.obd.R
import com.orango.electronic.orangetxusb.mmySql.Util_MmySql_ItemDAO
import kotlinx.android.synthetic.main.activity_select_mmy.view.*

class Frag_SelectMmyPage_MakeFragement : RootFragement(R.layout.activity_select_mmy) {

    val utilMmySqlItemDAO: Util_MmySql_ItemDAO by lazy { Util_MmySql_ItemDAO(activity!!) }

    override fun ViewInit() {
        rootview.re.layoutManager= GridLayoutManager(activity, 3)
        rootview.re.adapter= Ad_Make(utilMmySqlItemDAO.getMake()!!,activity!!)

    }

    override fun dispatchKeyEvent(event: KeyEvent?) {

    }

}
