package com.example.obd.Frag

import android.view.KeyEvent
import com.example.obd.MainPeace
import com.orange.blelibrary.blelibrary.RootFragement
import com.orange.obd.R


class Frag_SettingPager_Update : RootFragement(R.layout.fragment_update) {

    lateinit var mainPeace: MainPeace

    override fun ViewInit() {
        mainPeace=activity!! as MainPeace
    }

    override fun dispatchKeyEvent(event: KeyEvent?) {

    }

}
