package com.example.obd.Frag

import android.view.KeyEvent
import com.example.obd.MainPeace
import com.orange.blelibrary.blelibrary.RootFragement
import com.orange.obd.R
import kotlinx.android.synthetic.main.fragment_privary_policy.view.*

class Frag_SettingPager_PrivaryPolicy : RootFragement(R.layout.fragment_privary_policy) {
    override fun ViewInit() {
        rootview.button5.setOnClickListener {
            (activity as MainPeace).finish()
        }
        rootview.button6.setOnClickListener {
            if (place == 0) {
                (activity as MainPeace).ChangePage(Frag_SettingPager_Sign_in(), R.id.frage, "Frag_SettingPager_Sign_in", false)
            } else {
                (activity as MainPeace).GoBack()
            }
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent?) {

    }

    companion object {
        var place = 0
    }

}
