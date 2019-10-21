package com.orango.electronic.orangetxusb.SettingPagr


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.obd.MainActivity.MainPeace
import com.orange.obd.R
import kotlinx.android.synthetic.main.fragment_setting.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Setting : Fragment() {
lateinit var rootView:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView=inflater.inflate(R.layout.fragment_setting, container, false)
        rootView.area.setOnClickListener{
            Set_Languages.place=1
            (activity as MainPeace).ChangePage(Set_Languages(),R.id.frage,"SetArea",true)
        }
        rootView.policy.setOnClickListener{
            PrivaryPolicy.place=1
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
        (activity as MainPeace).back.visibility=View.VISIBLE
//        (activity as MainPeace).RightTop.visibility=View.GONE
    }

}
