package com.example.obd.MainActivity


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.obd.MainPeace
import com.example.obd.SelectMmyPage.MakeFragement

import com.orange.obd.R
import kotlinx.android.synthetic.main.fragment_selection.view.*


class Selection : Fragment() {
lateinit var rootView:View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView=inflater.inflate(R.layout.fragment_selection, container, false)
        rootView.imageView24.setOnClickListener {
            (activity as MainPeace).ChangePage(MyFavorite(),R.id.frage,"MyFavorite",true)
        }
rootView.imageView17.setOnClickListener {
    (activity as MainPeace).ChangePage(MakeFragement(),R.id.frage,"MakeFragement",true)
}
        rootView.imageView16.setOnClickListener {
            (activity as MainPeace).ChangePage(QrcodeScanner(),R.id.frage,"QrcodeScanner",true)
        }
        return rootView
    }


}
