package com.orango.electronic.orangetxusb.SettingPagr


import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.obd.MainActivity.MainPeace
import com.orange.obd.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


//
///**
// * A simple [Fragment] subclass.
// *
// */
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
        (activity as MainPeace).back.visibility=View.VISIBLE
    }

}
