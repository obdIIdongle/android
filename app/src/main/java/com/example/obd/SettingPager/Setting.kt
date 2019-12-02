package com.example.obd.SettingPager


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.obd.MainPeace
import com.orange.obd.R
import com.orango.electronic.orangetxusb.SettingPagr.PrivaryPolicy
import com.orango.electronic.orangetxusb.SettingPagr.Set_Languages
import com.orango.electronic.orangetxusb.SettingPagr.Update
import kotlinx.android.synthetic.main.fragment_setting.view.*


class Setting : Fragment() {
lateinit var rootView:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView=inflater.inflate(R.layout.fragment_setting, container, false)
        rootView.area.setOnClickListener{
            Set_Languages.place =1
            (activity as MainPeace).ChangePage(Set_Languages(),R.id.frage,"SetArea",true)
        }
        rootView.policy.setOnClickListener{
            PrivaryPolicy.place =1
            (activity as MainPeace).ChangePage(PrivaryPolicy(),R.id.frage,"policy",true)
        }
        rootView.version.setOnClickListener{
            (activity as MainPeace).ChangePage(Update(),R.id.frage,"Update",true)
        }
        return rootView
    }
    override fun onResume() {
        super.onResume()
//        (activity as MainPeace).setActionBarTitle(activity!!.resources.getString(R.string.Users_manual))
//        (activity as MainPeace).RightTop.visibility=View.GONE
    }

}
