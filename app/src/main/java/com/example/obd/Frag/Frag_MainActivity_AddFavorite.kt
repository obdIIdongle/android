package com.example.obd.Frag


import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.obd.MainPeace
import com.orange.blelibrary.blelibrary.RootFragement

import com.orange.obd.R
import kotlinx.android.synthetic.main.fragment_add_favorite.view.*
import java.util.ArrayList

class Frag_MainActivity_AddFavorite : RootFragement(R.layout.fragment_add_favorite) {

    var data= ArrayList<String>()
    var MakeList= ArrayList<String>()
    var ModelList= ArrayList<String>()
    var YearList= ArrayList<String>()
    lateinit var mainPeace: MainPeace

    override fun ViewInit() {

        mainPeace=activity!!as MainPeace
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
            act.GoBack()
        }

        rootview.button3.setOnClickListener {
            act.GoBack()
        }

    }

    override fun dispatchKeyEvent(event: KeyEvent?) {

    }

    fun GetYear(){
        YearList.clear()
        if(rootview.model.selectedItem==null){return}
        if(rootview.make.selectedItem==null){return}
        for(i in mainPeace.utilMmySqlItemDAO.getYear(rootview.make.selectedItem.toString(),rootview.model.selectedItem.toString())!!){YearList.add(i.year)}
        val arrayAdapter = ArrayAdapter<String>(activity!!, android.R.layout.simple_spinner_item, YearList)
        rootview.year.adapter=arrayAdapter
    }
    fun GetModel(){
        ModelList.clear()
        if(rootview.make.selectedItem==null){return}
        for(i in mainPeace.utilMmySqlItemDAO.getmodel(rootview.make.selectedItem.toString())!!){ModelList.add(i.modele)}
        val arrayAdapter = ArrayAdapter<String>(activity!!, android.R.layout.simple_spinner_item, ModelList)
        rootview.model.adapter=arrayAdapter
    }
    fun GetMake(){
        MakeList.clear()
        for(i in mainPeace.utilMmySqlItemDAO.getMake()!!){MakeList.add(i.make)}
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
