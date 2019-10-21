package com.example.obd.MainActivity


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.obd.TestFragement
import com.example.obd.UserManual.UserManual

import com.orange.obd.R
import com.orango.electronic.orangetxusb.SettingPagr.Setting
import kotlinx.android.synthetic.main.fragment_home_fragement.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragement : Fragment() {

    lateinit var act:MainPeace
    lateinit var rootView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
      rootView= inflater.inflate(R.layout.fragment_home_fragement, container, false)
        act=activity!! as MainPeace
        rootView.imageView4.setOnClickListener {
            act.ChangePage(MyFavorite(),R.id.frage,"MyFavorite",true)
        }
        rootView.imageView5.setOnClickListener {
            act.ChangePage(Setting(),R.id.frage,"Setting",true)
        }
        rootView.selectmmy.setOnClickListener {
//            val transaction = fragmentManager!!.beginTransaction()
//                transaction.replace(R.ID_Beans.frage, Selection())
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
//                        .addToBackStack(null)
//                        .commit()
            act.GoScanner(Selection(),10,R.id.frage,"Selection")
          act.back.visibility=View.VISIBLE
        }
        rootView.imageView6.setOnClickListener {
            act.ChangePage(UserManual(),R.id.frage,"UserManual",true)
        }
        act.back.visibility=View.GONE
        return rootView
    }

    override fun onResume() {
        act.back.visibility=View.GONE
        super.onResume()
    }

}
