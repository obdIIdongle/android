package com.example.obd.Frag


import android.view.KeyEvent
import com.example.obd.MainPeace
import com.orange.blelibrary.blelibrary.RootFragement

import com.orange.obd.R
import kotlinx.android.synthetic.main.fragment_user_manual.view.*


class Frag_UserManual : RootFragement(R.layout.fragment_user_manual) {
    override fun ViewInit() {
        rootview.button8.setOnClickListener {
            (activity as MainPeace).ChangePage(Frag_UserManual_Detail(),R.id.frage,"Frag_UserManual_Detail",true)
        }
    }
    override fun dispatchKeyEvent(event: KeyEvent) {
    }
}
