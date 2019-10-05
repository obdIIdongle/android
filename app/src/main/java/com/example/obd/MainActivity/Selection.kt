package com.example.obd.MainActivity


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.orange.obd.R
import kotlinx.android.synthetic.main.fragment_home_fragement.view.*
import kotlinx.android.synthetic.main.fragment_selection.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Selection : Fragment() {
lateinit var rootView:View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView=inflater.inflate(R.layout.fragment_selection, container, false)
        rootView.imageView24.setOnClickListener {
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.frage, MyFavorite())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                    .addToBackStack(null)
                    .commit()
        }
rootView.imageView17.setOnClickListener {
                    val transaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.frage, MakeFragement())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                        .addToBackStack(null)
                        .commit()
}
        rootView.imageView16.setOnClickListener {
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.frage, QrcodeScanner())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                    .addToBackStack(null)
                    .commit()
        }
        return rootView
    }


}
