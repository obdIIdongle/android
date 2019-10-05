package com.example.obd.MainActivity


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast

import com.orange.obd.R
import kotlinx.android.synthetic.main.fragment_add_favorite.view.*
import java.util.ArrayList
import com.example.obd.Demo.MainActivity



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AddFavorite : Fragment() {
    var data= ArrayList<String>()
    var MakeList= ArrayList<String>()
    var ModelList= ArrayList<String>()
    var YearList= ArrayList<String>()
    lateinit var act:MainPeace
    lateinit var rootview:View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootview=inflater.inflate(R.layout.fragment_add_favorite, container, false)
        act=activity!!as MainPeace
        GetMake()
        GetFav()
        rootview.make.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
           override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
               GetModel()
            }

            override  fun onNothingSelected(parent: AdapterView<*>) {
                // TODO Auto-generated method stub
            }
        })
        rootview.model.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                GetYear()
            }

            override  fun onNothingSelected(parent: AdapterView<*>) {
                // TODO Auto-generated method stub
            }
        })
        rootview.year.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            }

            override  fun onNothingSelected(parent: AdapterView<*>) {
                // TODO Auto-generated method stub
            }
        })
        rootview.button7.setOnClickListener {
            if(rootview.model.selectedItem==null){  return@setOnClickListener}
            if(rootview.make.selectedItem==null){  return@setOnClickListener}
            if(rootview.year.selectedItem==null){ return@setOnClickListener }
            if(!data.contains("${rootview.make.selectedItem}☆${rootview.model.selectedItem}☆${rootview.year.selectedItem}")){ data.add("${rootview.make.selectedItem}☆${rootview.model.selectedItem}☆${rootview.year.selectedItem}")}
            SetFav()
            act.goback()
        }
        rootview.button3.setOnClickListener {
            act.goback()
        }
        return rootview
    }
    fun GetYear(){
        YearList.clear()
        if(rootview.model.selectedItem==null){return}
        if(rootview.make.selectedItem==null){return}
        for(i in act.itemDAO.getYear(rootview.make.selectedItem.toString(),rootview.model.selectedItem.toString())!!){YearList.add(i.year)}
        val arrayAdapter = ArrayAdapter<String>(activity!!, android.R.layout.simple_spinner_item, YearList)
        rootview.year.adapter=arrayAdapter
    }
    fun GetModel(){
        ModelList.clear()
        if(rootview.make.selectedItem==null){return}
        for(i in act.itemDAO.getmodel(rootview.make.selectedItem.toString())!!){ModelList.add(i.modele)}
        val arrayAdapter = ArrayAdapter<String>(activity!!, android.R.layout.simple_spinner_item, ModelList)
        rootview.model.adapter=arrayAdapter
    }
    fun GetMake(){
        MakeList.clear()
        for(i in act.itemDAO.getMake()!!){MakeList.add(i.make)}
        val arrayAdapter = ArrayAdapter<String>(activity!!, android.R.layout.simple_spinner_item, MakeList)
        rootview.make.adapter=arrayAdapter
    }
    fun SetFav(){
        val profilePreferences = activity!!.getSharedPreferences("Favorite", Context.MODE_PRIVATE)
        profilePreferences.edit().putInt("count",data.size).commit()
        for(i in 0 until data.size){
            profilePreferences.edit().putString("$i",data[i]).commit()
        }
    }
    fun GetFav(){
        data.clear()
        val profilePreferences = activity!!.getSharedPreferences("Favorite", Context.MODE_PRIVATE)
        val a= profilePreferences.getInt("count",0)
        for(i in 0 until a){
            var tmpdata=profilePreferences.getString("$i","nodata")
            if (!tmpdata.equals("nodata")){   data.add(tmpdata)}
        }
    }

}
