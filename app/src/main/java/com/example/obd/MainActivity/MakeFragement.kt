package com.example.obd.MainActivity


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orange.obd.R
import com.orange_electronic.orangeobd.Adapter.makeadapter
import com.orango.electronic.orangetxusb.mmySql.ItemDAO

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MakeFragement : Fragment() {
    lateinit var rootView: View
    lateinit var re: RecyclerView
    val itemDAO: ItemDAO by lazy { ItemDAO(activity!!) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView= inflater.inflate(R.layout.activity_select_mmy, container, false)
        re=rootView.findViewById(R.id.re)
        re.layoutManager= GridLayoutManager(activity,3)
        re.adapter= makeadapter(itemDAO.getMake()!!,activity!!,fragmentManager!!)
        (activity as MainPeace).back.visibility=View.VISIBLE
        return rootView
    }

    override fun onResume() {
        super.onResume()
        (activity as MainPeace).back.visibility=View.VISIBLE
    }

}
