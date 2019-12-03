package com.example.obd.Frag

import android.view.KeyEvent
import com.example.obd.MainPeace
import com.orange.blelibrary.blelibrary.RootFragement
import com.orange.obd.R
import kotlinx.android.synthetic.main.fragment_setting.view.*

class Frag_SettingPager_Setting : RootFragement(R.layout.fragment_setting) {
    
    override fun ViewInit() {
        rootview.area.setOnClickListener{
            Frag_SettingPager_Set_Languages.place =1
            (activity as MainPeace).ChangePage(Frag_SettingPager_Set_Languages(),R.id.frage,"SetArea",true)
        }
        rootview.policy.setOnClickListener{
            Frag_SettingPager_PrivaryPolicy.place =1
            (activity as MainPeace).ChangePage(Frag_SettingPager_PrivaryPolicy(),R.id.frage,"policy",true)
        }
        rootview.version.setOnClickListener{
            (activity as MainPeace).ChangePage(Frag_SettingPager_Update(),R.id.frage,"Frag_SettingPager_Update",true)
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent?) {
        
    }

}
