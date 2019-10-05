package com.example.obd.FunctionPage


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.obd.MainActivity.MainPeace

import com.orange.obd.R
import kotlinx.android.synthetic.main.fragment_obdii_relearn.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class OBDII_relearn : Fragment() {
    lateinit var rootView: View
    lateinit var act:MainPeace
    lateinit var text:TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView= inflater.inflate(R.layout.fragment_obdii_relearn, container, false)
        text=rootView.findViewById(R.id.textView10)
        act=activity!!as MainPeace
        text.text=act.itemDAO.GetreLarm(act.SelectMake,act.SelectModel,act.SelectYear,act)
        rootView.toper.text="${act.SelectMake}/${act.SelectModel}/${act.SelectYear}"
        rootView.next.setOnClickListener {
            val transaction = fragmentManager!!.beginTransaction()
            val fragement= Key_ID()
            transaction.replace(R.id.frage,fragement )
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                    .addToBackStack(null)
                    .commit()
        }
        return rootView
    }


}
