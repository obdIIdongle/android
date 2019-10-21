package com.example.obd.MainActivity


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.obd.Adapter.FavAdapter

import com.orange.obd.R
import kotlinx.android.synthetic.main.fragment_my_favorite.view.*
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MyFavorite : Fragment() {
lateinit var RootView:View
    lateinit var re:RecyclerView
    lateinit var adapter: FavAdapter
    var data= ArrayList<String>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        RootView=inflater.inflate(R.layout.fragment_my_favorite, container, false)
        RootView.menu.setOnClickListener {
            (activity as MainPeace).ChangePage(HomeFragement(),R.id.frage,"Home",false)
        }
        RootView.add.setOnClickListener {
            (activity as MainPeace).ChangePage(AddFavorite(),R.id.frage,"AddFavorite",true)
        }
//        SetModel()
        Getmodel()
        adapter=FavAdapter(data,activity!!,fragmentManager!!)
        re=RootView.findViewById(R.id.adapter)
        re.layoutManager=LinearLayoutManager(activity)
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
        (activity as MainPeace).back.visibility=View.VISIBLE
//        (activity as MainPeace).RightTop.visibility=View.GONE
    }

}
