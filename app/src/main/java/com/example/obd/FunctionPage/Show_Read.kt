package com.example.obd.FunctionPage


import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.obd.MainPeace
import com.example.obd.SelectMmyPage.MakeFragement
import com.example.obd.tool.FtpManager
import com.orange.blelibrary.blelibrary.CallBack.Dailog_SetUp_C
import com.orange.blelibrary.blelibrary.RootActivity
import com.orange.blelibrary.blelibrary.tool.FormatConvert.bytesToHex

import com.orange.obd.R
import kotlinx.android.synthetic.main.activity_main_peace.view.*
import kotlinx.android.synthetic.main.fragment_show__read.view.*
import kotlinx.android.synthetic.main.fragment_show__read.view.program
import kotlinx.android.synthetic.main.fragment_show__read.view.Lrt as Lrt1
import kotlinx.android.synthetic.main.fragment_show__read.view.Rrt as Rrt1

class Show_Read : Fragment() {
    lateinit var rootView: View
    lateinit var directfit: String
    lateinit var act: MainPeace
    var tire = "04"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_show__read, container, false)
        act = activity as MainPeace
        val make = act.SelectMake
        val model = act.SelectModel
        val year = act.SelectYear
        rootView.mmy_text2.text="$make/$model/$year"
        directfit = act.itemDAO.getPart(make, model, year).directfit
        if (act.itemDAO.IsFiveTire(directfit)) {
            rootView.lrt.visibility = View.VISIBLE
            tire = "05"
        } else {
            rootView.lrt.visibility = View.GONE
            tire = "04"
        }
        act.ShowDaiLog(R.layout.dataloading,false,true, object :Dailog_SetUp_C(){
            override fun SetUP(root: Dialog, act: RootActivity) {
                root.findViewById<TextView>(R.id.tit).text=resources.getString(R.string.Data_Loading)
            }
        })
        Downs19()
        return rootView
    }
    fun Downs19(){
        handler.post { act.rootview.back.isEnabled=false }
        Thread{
            if(!act.command.HandShake()){act.command.Reboot()}
            val a= FtpManager.DownS19(directfit, act)
            if(a){
                act.command.AskVersion()
                if (act.AppVersion == bytesToHex(FtpManager.donloads19.replace(".srec", "").toByteArray())) {
                    if(act.command.GoApp()){
                        handler.post { Key_ID.s19 =directfit
                            SetId()
                            act.rootview.back.isEnabled=true
                        }
                        return@Thread
                        }
                } else {
//                    act.command.HandShake()
                    if (!act.command.WriteVersion()||!act.command.GoBootloader()) {
                        handler.post {
                            act.ShowDaiLog(R.layout.activity_re_program,false,true, object :Dailog_SetUp_C(){
                                override fun SetUP(root: Dialog, act: RootActivity) {
                                    root.findViewById<TextView>(R.id.tit).text=resources.getString(R.string.Programming)
                                }
                            })
                            act.bleServiceControl.disconnect()
                        }
                        handler.post { act.rootview.back.isEnabled=true }
                        return@Thread
                    }
                }
//                if(!act.command.HandShake()){
//                    act.command.Reboot()
//                }
//                val Pro=act.command.HandShake()&& act.command.WriteFlash(act,directfit,296,act)
                Thread.sleep(2000)
                val Pro=act.command.WriteFlash(act,directfit,296,act)
                handler.post {
                    act.rootview.back.isEnabled=true
                    act.DaiLogDismiss()
                    if(Pro){
//                            Toast.makeText(activity,"燒錄成功",Toast.LENGTH_SHORT).show();
                        Key_ID.s19 =directfit
                        SetId()
                    }else{
//                            Toast.makeText(activity,"燒錄失敗",Toast.LENGTH_SHORT).show();
                        act.ShowDaiLog(R.layout.activity_re_program,true,false, Dailog_SetUp_C())
                        act.bleServiceControl.disconnect()
                    }
                }
            }else{
                handler.post {
                    act.DaiLogDismiss()
                    act.rootview.back.isEnabled=true
                act.ShowDaiLog(R.layout.internet_error,false,true, Dailog_SetUp_C())
                    act.supportFragmentManager.popBackStack(null,1)
                }

            }
        }.start()
    }
    var handler = Handler()
    fun SetId() {
        act.ShowDaiLog(R.layout.dataloading,false,true, object :Dailog_SetUp_C() {
            override fun SetUP(root: Dialog, act: RootActivity) {
                root.findViewById<TextView>(R.id.tit).text=resources.getString(R.string.app_data_reading)
            }
        })
        Thread {
            val a = act.command.GetId(tire);
            handler.post {
                if (a.success) {
                    rootView.Lft.text = a.LF
                    rootView.Rft.text = a.RF
                    rootView.Lrt1.text = a.LR
                    rootView.Rrt1.text = a.RR
                    rootView.lrt.text = a.SP
                    rootView.program.setOnClickListener {    act.ChangePage(Key_ID(),R.id.frage,"Key_ID",true)}
                }else{
                    act.ChangePage(MakeFragement(),R.id.frage,"MakeFragement",false)
                    act.Toast("車種選擇錯誤")
                }
                act.DaiLogDismiss()
            }
        }.start()
    }

}
