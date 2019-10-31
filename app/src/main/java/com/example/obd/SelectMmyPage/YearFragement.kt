package com.example.obd.SelectMmyPage


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.obd.MainPeace
import com.orange.obd.R
import com.orange_electronic.orangeobd.Adapter.YearAdapter
import com.orango.electronic.orangetxusb.mmySql.ItemDAO


class YearFragement : Fragment() {
    lateinit var rootView: View
    lateinit var re: RecyclerView
    val itemDAO: ItemDAO by lazy { ItemDAO(activity!!) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView= inflater.inflate(R.layout.activity_year, container, false)
        var make=(activity as MainPeace).SelectMake
        var model=(activity as MainPeace).SelectModel
        re=rootView.findViewById(R.id.re)
        re.layoutManager= GridLayoutManager(activity,2)
        re.adapter= YearAdapter(itemDAO.getYear(make,model)!!,activity!!,fragmentManager!!)
        return rootView
    }


}
