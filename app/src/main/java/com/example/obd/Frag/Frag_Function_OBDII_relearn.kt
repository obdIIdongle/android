package com.example.obd.OBD_Relearn


import android.view.KeyEvent
import com.example.obd.Beans.Bs_Write_and_Read
import com.example.obd.Frag.Frag_Function_Key_ID
import com.example.obd.Frag.Frag_Function_Show_Read
import com.example.obd.MainPeace
import com.orange.blelibrary.blelibrary.RootFragement

import com.orange.obd.R
import kotlinx.android.synthetic.main.fragment_obdii_relearn.view.*

class Frag_Function_OBDII_relearn : RootFragement(R.layout.fragment_obdii_relearn) {
    lateinit var mainPeace: MainPeace

    override fun ViewInit() {
        mainPeace=activity!!as MainPeace
        rootview.text_content.text=mainPeace.utilMmySqlItemDAO.GetreLarm(mainPeace.SelectMake,mainPeace.SelectModel,mainPeace.SelectYear,mainPeace)
        rootview.toper.text="${mainPeace.SelectMake}/${mainPeace.SelectModel}/${mainPeace.SelectYear}"

        rootview.next.setOnClickListener {
            //            act.ChangePage(Frag_Function_Key_ID(),R.id.frage,"Frag_Function_Key_ID",true)
            when(Bs_Write_and_Read.Trun)
            {
                Bs_Write_and_Read.讀取 ->
                {act.ChangePage(Frag_Function_Show_Read(),R.id.frage,"Frag_Function_Show_Read",true)}
                Bs_Write_and_Read.寫入 ->
                {act.ChangePage(Frag_Function_Key_ID(),R.id.frage,"Frag_Function_Key_ID",true)}
            }

        }
    }

    override fun dispatchKeyEvent(event: KeyEvent?) {
        
    }

}
