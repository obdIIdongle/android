package com.orango.electronic.orangetxusb.SettingPagr


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.obd.MainPeace
import com.example.obd.SettingPager.Sign_in
import com.orange.obd.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 *
 */
class PrivaryPolicy : Fragment() {
    companion object{
        var place=0
    }
lateinit  var rootview:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview=inflater.inflate(R.layout.fragment_privary_policy, container, false)
        (rootview.findViewById(R.id.button5) as Button).setOnClickListener {
            (activity as MainPeace).finish()
        }
        (rootview.findViewById(R.id.button6) as Button).setOnClickListener {
            if(place==0){
                (activity as MainPeace).ChangePage(Sign_in(),R.id.frage,"Sign_in",false)
            }else{
                (activity as MainPeace).GoBack()
            }

        }
        return rootview
    }
    override fun onResume() {
        super.onResume()
//        (activity as MainPeace).setActionBarTitle(activity!!.resources.getString(R.string.Privacy_Policy))
//        (activity as MainPeace).back.visibility=View.VISIBLE
//        (activity as MainPeace).RightTop.visibility=View.GONE
    }

}
