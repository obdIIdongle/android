package com.example.obd.SettingPager


import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.example.obd.HttpCommand.Fuction.ResetPassword
import com.example.obd.MainPeace
import com.orange.obd.R
import kotlinx.android.synthetic.main.activity_reset_pass.view.*


class ResetPass : Fragment() {
    lateinit var rootview:View
    var run=false
    lateinit var edit: EditText
    var handler= Handler()
    lateinit var load: RelativeLayout
    lateinit var programAnimator: LottieAnimationView
    lateinit var act: MainPeace
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootview=inflater.inflate(R.layout.activity_reset_pass, container, false)
        act=activity!! as MainPeace
        edit=rootview.findViewById(R.id.editText2)
        load=rootview.findViewById(R.id.load)
        programAnimator=rootview.findViewById(R.id.animation_view8)
        rootview.button14.setOnClickListener {
            if(run){
                return@setOnClickListener
            }
            act.LoadingUI("Data Loading",0)
            var email=edit.text.toString()
            Thread{
                var isok= ResetPassword(email)
                handler.post {
                    run=false
                    act.LoadingSuccessUI()
                    if(isok){
                        (activity as MainPeace).ChangePage(Sign_in(),R.id.frage,"Sign_in",false)
                    }else{
                        Toast.makeText(act,R.string.nointernet, Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }
        return rootview
        }
    }



