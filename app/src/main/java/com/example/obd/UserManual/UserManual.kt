package com.example.obd.UserManual


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.obd.MainPeace

import com.orange.obd.R
import kotlinx.android.synthetic.main.fragment_user_manual.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class UserManual : Fragment() {
lateinit var rootview:View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootview=inflater.inflate(R.layout.fragment_user_manual, container, false)
        rootview.button8.setOnClickListener {
            (activity as MainPeace).ChangePage(ManualDetail(),R.id.frage,"ManualDetail",true)
        }
        return rootview
    }


}
