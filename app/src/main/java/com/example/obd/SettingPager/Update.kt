package com.orango.electronic.orangetxusb.SettingPagr


import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.obd.MainPeace
import com.orange.obd.R


class Update : Fragment() {
lateinit var rootview:View
    lateinit var act: MainPeace
    var progress=false
    var mcpass="0"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       rootview=inflater.inflate(R.layout.fragment_update, container, false)
        act=activity!! as MainPeace
        return rootview
    }

var handler=Handler()
    override fun onResume() {
        super.onResume()
    }

}
