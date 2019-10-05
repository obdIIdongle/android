package com.example.obd.UserManual


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.obd.MainActivity.MainPeace
import com.example.obd.UserManual.Page.*
import com.orange.obd.R

import kotlinx.android.synthetic.main.fragment_manual_detail.view.*
import java.util.*
import android.support.v4.view.ViewPager.OnPageChangeListener as OnPageChangeListener1


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ManualDetail : Fragment() {
lateinit var rootView:View
   lateinit var fragments: ArrayList<Fragment>
    lateinit var ImageViews: ArrayList<ImageView>
    lateinit var li:LinearLayout
    lateinit var act:MainPeace
    lateinit var viewpager:ViewPager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_manual_detail, container, false)
        viewpager=rootView.findViewById(R.id.viewpager)
        li=rootView.findViewById(R.id.li)
        fragments=ArrayList<Fragment>()
        ImageViews=ArrayList<ImageView>()
        act=activity!! as MainPeace
        rootView.button11.setOnClickListener {
            act.onBackPressed()
        }
        fragments.add(F1())
        fragments.add(F2())
        fragments.add(F3())
        fragments.add(F4())
        fragments.add(F5())
        fragments.add(F6())
        fragments.add(F7())
        fragments.add(F8())
        fragments.add(F9())
        fragments.add(F10())
        fragments.add(F11())
        fragments.add(F12())
        for(i in 0 until fragments.size){
            var a= ImageView(activity)
            a.layoutParams= ViewGroup.LayoutParams(40,40)
            if(i==0){
                a.setImageDrawable(activity!!.getDrawable(R.drawable.circlespot))
            }else{   a.setImageDrawable(activity!!.getDrawable(R.drawable.circlespot2))}
            a.setPadding(10,10,10,10)
            li.addView(a)
            ImageViews.add(a)
        }
        viewpager.adapter=MyPagerAdapter(fragmentManager!!)
        viewpager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                ImageViews.get(position).setImageDrawable(activity!!.getDrawable(R.drawable.circlespot))
           if(position-1>=0){ImageViews.get(position-1).setImageDrawable(activity!!.getDrawable(R.drawable.circlespot2))}
                if(position+1<ImageViews.size){  ImageViews.get(position+1).setImageDrawable(activity!!.getDrawable(R.drawable.circlespot2))}
            }
        })

            return rootView
    }
    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(i: Int): Fragment {
            return fragments[i]
        }

        override fun getCount(): Int {
            return fragments.size
        }
    }
    override fun onResume() {
        super.onResume()

       }
}
