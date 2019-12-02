package com.example.obd.MainActivity


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.obd.Adapter.FavAdapter
import com.example.obd.MainPeace

import com.orange.obd.R
import kotlinx.android.synthetic.main.fragment_my_favorite.view.*
import java.util.ArrayList


class MyFavorite : Fragment() {
lateinit var RootView:View
    lateinit var re: androidx.recyclerview.widget.RecyclerView
    lateinit var adapter: FavAdapter
    var data= ArrayList<String>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        RootView=inflater.inflate(R.layout.fragment_my_favorite, container, false)
        RootView.menu.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack(null,1)
        }
        RootView.add.setOnClickListener {
            (activity as MainPeace).ChangePage(AddFavorite(),R.id.frage,"AddFavorite",true)
        }
//        SetModel()
        Getmodel()
        adapter=FavAdapter(data,activity!!,fragmentManager!!)
        re=RootView.findViewById(R.id.adapter)
        re.layoutManager= androidx.recyclerview.widget.LinearLayoutManager(activity)
        re.adapter=adapter
        return RootView
    }
    fun SetModel(){
        val profilePreferences = activity!!.getSharedPreferences("Favorite", Context.MODE_PRIVATE)
        profilePreferences.edit().putInt("count",data.size).commit()
        for(i in 0 until data.size){
            profilePreferences.edit().putString("$i",data[i]).commit()
        }
    }
    fun Getmodel(){
        data.clear()
        val profilePreferences = activity!!.getSharedPreferences("Favorite", Context.MODE_PRIVATE)
        val a= profilePreferences.getInt("count",0)
        for(i in 0 until a){
            var tmpdata=profilePreferences.getString("$i","nodata")
            if (!tmpdata.equals("nodata")){   data.add(tmpdata)}
        }
    }
    override fun onResume() {
        super.onResume()
//        (activity as MainPeace).setActionBarTitle(activity!!.resources.getString(R.string.Users_manual))
//        (activity as MainPeace).RightTop.visibility=View.GONE
    }

}
