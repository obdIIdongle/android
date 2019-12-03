package com.example.obd.Frag

import android.view.KeyEvent
import android.widget.Toast
import com.example.obd.util.Util_Http_Command_Function.ResetPassword
import com.example.obd.MainPeace
import com.orange.blelibrary.blelibrary.CallBack.Dailog_SetUp_C
import com.orange.blelibrary.blelibrary.RootFragement
import com.orange.obd.R
import kotlinx.android.synthetic.main.activity_reset_pass.*
import kotlinx.android.synthetic.main.activity_reset_pass.view.*

class Frag_SettingPager_ResetPass : RootFragement(R.layout.activity_reset_pass) {

    var run=false
    //lateinit var programAnimator: LottieAnimationView
    lateinit var mainPeace: MainPeace

    override fun ViewInit()
    {
        mainPeace=activity!! as MainPeace

        //programAnimator=rootview.findViewById(R.id.animation_view8)
        rootview.button14.setOnClickListener {
            if(run){
                return@setOnClickListener
            }
            act.ShowDaiLog(R.layout.dataloading,false,false, Dailog_SetUp_C())
            var email=edit.text.toString()
            Thread{
                var isok= ResetPassword(email)
                handler.post {
                    run=false
                    act.DaiLogDismiss()
                    if(isok){
                        (activity as MainPeace).ChangePage(Frag_SettingPager_Sign_in(),R.id.frage,"Frag_SettingPager_Sign_in",false)
                    }else{
                        Toast.makeText(act,R.string.nointernet, Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent?) {

    }

    }



