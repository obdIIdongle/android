package com.example.obd.Frag


import android.view.KeyEvent
import android.view.ViewGroup

import androidx.fragment.app.FragmentStatePagerAdapter
import android.widget.ImageView
import com.example.obd.MainPeace
import com.example.obd.UserManual.Page.*
import com.orange.blelibrary.blelibrary.RootFragement
import com.orange.obd.R

import kotlinx.android.synthetic.main.fragment_manual_detail.view.*
import java.util.*

class Frag_UserManual_Detail : RootFragement(R.layout.fragment_manual_detail) {
    lateinit var fragments: ArrayList<androidx.fragment.app.Fragment>
    lateinit var ImageViews: ArrayList<ImageView>
    override fun ViewInit() {
        fragments=ArrayList<androidx.fragment.app.Fragment>()
        ImageViews=ArrayList<ImageView>()
        act=activity!! as MainPeace
        rootview.button11.setOnClickListener {
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
            rootview.li.addView(a)
            ImageViews.add(a)
        }

        rootview.viewpager.adapter=MyPagerAdapter(fragmentManager!!)
        rootview.viewpager.setOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.OnPageChangeListener{
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

    }

    override fun dispatchKeyEvent(event: KeyEvent) {
    }
    private inner class MyPagerAdapter(fm: androidx.fragment.app.FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(i: Int): androidx.fragment.app.Fragment {
            return fragments[i]
        }

        override fun getCount(): Int {
            return fragments.size
        }
    }
}
