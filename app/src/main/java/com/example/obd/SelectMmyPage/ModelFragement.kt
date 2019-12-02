package com.example.obd.SelectMmyPage


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.obd.MainPeace
import com.orange.obd.R
import com.example.obd.Adapter.ModelAdapter
import com.orango.electronic.orangetxusb.mmySql.ItemDAO


class ModelFragement : Fragment() {
    lateinit var rootView: View
    lateinit var  re: androidx.recyclerview.widget.RecyclerView
    val itemDAO: ItemDAO by lazy { ItemDAO(activity!!) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView= inflater.inflate(R.layout.activity_select_model, container, false)
        re=rootView.findViewById(R.id.re)
        re.layoutManager= androidx.recyclerview.widget.GridLayoutManager(activity, 2)
        re.adapter= ModelAdapter(itemDAO.getmodel((activity as MainPeace).SelectMake)!!,activity!!,fragmentManager!!)
        return rootView
    }


}
