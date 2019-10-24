package com.example.obd


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.obd.MainActivity.MainPeace
import com.example.obd.tool.Command.GetXOR
import com.orange.blelibrary.blelibrary.BleActivity
import com.orange.blelibrary.blelibrary.tool.FormatConvert.StringHexToByte
import com.orange.blelibrary.blelibrary.tool.FormatConvert.bytesToHex

import com.orange.obd.R
import kotlinx.android.synthetic.main.activity_scan_ble.view.*
import kotlinx.android.synthetic.main.fragment_setting.view.*
import kotlinx.android.synthetic.main.fragment_test_fragement.view.*
import kotlinx.android.synthetic.main.fragment_test_fragement.view.button

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TestFragement : Fragment() {
lateinit var rootview:View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootview=inflater.inflate(R.layout.fragment_test_fragement, container, false)
        rootview.button.setOnClickListener {
            (activity!! as MainPeace).bleServiceControl.WriteCmd(GetXOR("0ADF000100FFF5"),0)
        }
        rootview.button9.setOnClickListener {
            (activity!! as MainPeace).bleServiceControl.WriteCmd(GetXOR("60BF000104FF0A"),0)
        }
        rootview.button12.setOnClickListener {
            (activity!! as MainPeace).bleServiceControl.WriteCmd(GetXOR("60BF000105FF0A"),0)
        }
        rootview.button15.setOnClickListener {
            (activity!! as MainPeace).bleServiceControl.WriteCmd(GetXOR("0ACF000100FFF5"),0)
        }
        rootview.button16.setOnClickListener {
            (activity!! as MainPeace).bleServiceControl.WriteCmd(GetXOR("0ACA0015DDFFF5".replace("DD",bytesToHex("OBD_BLE_DR001_190815".toByteArray()))),0)
        }
        rootview.button17.setOnClickListener {
            (activity!! as MainPeace).bleServiceControl.WriteCmd(GetXOR("0ACD000100FFF5"),0)
        }
        rootview.button18.setOnClickListener {
            (activity!! as MainPeace).bleServiceControl.WriteCmd(GetXOR("0ACD010100FFF5"),0)
        }
        return rootview
    }


}
