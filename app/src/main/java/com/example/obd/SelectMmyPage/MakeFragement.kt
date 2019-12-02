package com.example.obd.SelectMmyPage


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.obd.MainPeace
import com.orange.obd.R
import com.orange_electronic.orangeobd.Adapter.makeadapter
import com.orango.electronic.orangetxusb.mmySql.ItemDAO


class MakeFragement : Fragment() {
    lateinit var rootView: View
    lateinit var re: androidx.recyclerview.widget.RecyclerView
    val itemDAO: ItemDAO by lazy { ItemDAO(activity!!) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView= inflater.inflate(R.layout.activity_select_mmy, container, false)
        re=rootView.findViewById(R.id.re)
        re.layoutManager= androidx.recyclerview.widget.GridLayoutManager(activity, 3)
        re.adapter= makeadapter(itemDAO.getMake()!!,activity!!)
        return rootView
    }

    override fun onResume() {
        super.onResume()
    }

}
