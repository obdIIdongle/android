package com.example.obd.Frag


import android.content.Context
import android.view.KeyEvent
import com.example.obd.Adapter.Ad_Favorite
import com.example.obd.MainPeace
import com.orange.blelibrary.blelibrary.RootFragement

import com.orange.obd.R
import kotlinx.android.synthetic.main.fragment_my_favorite.view.*
import java.util.ArrayList

class Frag_MainActivity_MyFavorite : RootFragement(R.layout.fragment_my_favorite) {


    lateinit var adapter: Ad_Favorite
    var data = ArrayList<String>()

    override fun ViewInit() {

        rootview.menu.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack(null, 1)
        }
        rootview.add.setOnClickListener {
            (activity as MainPeace).ChangePage(Frag_MainActivity_AddFavorite(), R.id.frage, "Frag_MainActivity_AddFavorite", true)
        }
//        SetModel()
        Getmodel()
        adapter = Ad_Favorite(data, activity!!)
        rootview.adapter.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        rootview.adapter.adapter = adapter
    }

    override fun dispatchKeyEvent(event: KeyEvent?) {

    }


    fun Getmodel() {
        data.clear()
        val profilePreferences = activity!!.getSharedPreferences("Favorite", Context.MODE_PRIVATE)
        val a = profilePreferences.getInt("count", 0)
        for (i in 0 until a) {
            var tmpdata = profilePreferences.getString("$i", "nodata")
            if (!tmpdata.equals("nodata")) {
                data.add(tmpdata)
            }
        }
    }


}
