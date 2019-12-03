package com.example.obd.Frag


import android.view.KeyEvent
import com.example.obd.MainPeace
import com.orange.blelibrary.blelibrary.RootFragement

import com.orange.obd.R
import kotlinx.android.synthetic.main.fragment_selection.view.*

class Frag_Function_Selection : RootFragement(R.layout.fragment_selection) {
    override fun ViewInit() {
        rootview.imageView24.setOnClickListener {
            (activity as MainPeace).ChangePage(Frag_MainActivity_MyFavorite(),R.id.frage,"Frag_MainActivity_MyFavorite",true)
        }
        rootview.imageView17.setOnClickListener {
            (activity as MainPeace).ChangePage(Frag_SelectMmyPage_MakeFragement(),R.id.frage,"Frag_SelectMmyPage_MakeFragement",true)
        }
        rootview.imageView16.setOnClickListener {
            (activity as MainPeace).ChangePage(Frag_Function_QrcodeScanner(),R.id.frage,"Frag_Function_QrcodeScanner",true)
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent?) {

    }

}
