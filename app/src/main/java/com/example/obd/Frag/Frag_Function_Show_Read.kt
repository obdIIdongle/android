package com.example.obd.Frag


import android.app.Dialog
import android.view.KeyEvent
import android.view.View
import com.example.obd.MainPeace
import com.example.obd.util.Util_FtpManager
import com.orange.blelibrary.blelibrary.CallBack.Dailog_SetUp_C
import com.orange.blelibrary.blelibrary.RootActivity
import com.orange.blelibrary.blelibrary.RootFragement
import com.orange.blelibrary.blelibrary.tool.FormatConvert.bytesToHex

import com.orange.obd.R
import kotlinx.android.synthetic.main.activity_main_peace.view.*
import kotlinx.android.synthetic.main.dataloading.view.*
import kotlinx.android.synthetic.main.fragment_show__read.view.*

class Frag_Function_Show_Read : RootFragement(R.layout.fragment_show__read) {

    lateinit var mainPeace: MainPeace
    lateinit var directfit: String
    var tire = "04"

    override fun ViewInit() {

        mainPeace = activity as MainPeace
        val make = mainPeace.SelectMake
        val model = mainPeace.SelectModel
        val year = mainPeace.SelectYear
        rootview.mmy_text2.text="$make/$model/$year"

        directfit = mainPeace.utilMmySqlItemDAO.getPart(make, model, year).directfit
        if (mainPeace.utilMmySqlItemDAO.IsFiveTire(directfit)) {
            rootview.lrt.visibility = View.VISIBLE
            tire = "05"
        } else {
            rootview.lrt.visibility = View.GONE
            tire = "04"
        }
        act.ShowDaiLog(R.layout.dataloading,false,true, object :Dailog_SetUp_C(){
            override fun SetUP(root: Dialog, act: RootActivity) {
                rootview.title.text=resources.getString(R.string.Data_Loading)
            }
        })
        Downs19()
    }

    override fun dispatchKeyEvent(event: KeyEvent?) {

    }

    fun Downs19(){
        handler.post { act.rootview.back.isEnabled=false }
        Thread{
            if(!mainPeace.command.HandShake()){mainPeace.command.Reboot()}
            val a= Util_FtpManager.DownS19(directfit, mainPeace)
            if(a){
                mainPeace.command.AskVersion()
                if (mainPeace.AppVersion == bytesToHex(Util_FtpManager.donloads19.replace(".srec", "").toByteArray())) {
                    if(mainPeace.command.GoApp()){
                        handler.post { Frag_Fcnction_Key_ID.s19 =directfit
                            SetId()
                            act.rootview.back.isEnabled=true
                        }
                        return@Thread
                        }
                } else {
//                    act.command.HandShake()
                    if (!mainPeace.command.WriteVersion()||!mainPeace.command.GoBootloader()) {
                        handler.post {
                            act.ShowDaiLog(R.layout.activity_re_program,false,true, object :Dailog_SetUp_C(){
                                override fun SetUP(root: Dialog, act: RootActivity) {
                                    rootview.title.text=resources.getString(R.string.Programming)
                                }
                            })
                            mainPeace.bleServiceControl.disconnect()
                        }
                        handler.post { mainPeace.rootview.back.isEnabled=true }
                        return@Thread
                    }
                }
//                if(!act.command.HandShake()){
//                    act.command.Reboot()
//                }
//                val Pro=act.command.HandShake()&& act.command.WriteFlash(act,directfit,296,act)
                Thread.sleep(2000)
                val Pro=mainPeace.command.WriteFlash(act,directfit,296,mainPeace)
                handler.post {
                    act.rootview.back.isEnabled=true
                    act.DaiLogDismiss()
                    if(Pro){
//                            Toast.makeText(activity,"燒錄成功",Toast.LENGTH_SHORT).show();
                        Frag_Fcnction_Key_ID.s19 =directfit
                        SetId()
                    }else{
//                            Toast.makeText(activity,"燒錄失敗",Toast.LENGTH_SHORT).show();
                        act.ShowDaiLog(R.layout.activity_re_program,true,false, Dailog_SetUp_C())
                        mainPeace.bleServiceControl.disconnect()
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
    //var handler = Handler()
    fun SetId() {
        act.ShowDaiLog(R.layout.dataloading,false,true, object :Dailog_SetUp_C() {
            override fun SetUP(root: Dialog, act: RootActivity) {
                rootview.title.text=resources.getString(R.string.app_data_reading)
            }
        })
        Thread {
            val a = mainPeace.command.GetId(tire);
            handler.post {
                if (a.success) {
                    rootview.Lft.text = a.LF
                    rootview.Rft.text = a.RF
                    rootview.Lrt.text = a.LR
                    rootview.Rrt.text = a.RR
                    rootview.lrt.text = a.SP
                    rootview.program.setOnClickListener {    act.ChangePage(Frag_Fcnction_Key_ID(),R.id.frage,"Frag_Fcnction_Key_ID",true)}
                }else{
                    act.ChangePage(Frag_SelectMmyPage_MakeFragement(),R.id.frage,"Frag_SelectMmyPage_MakeFragement",false)
                    act.Toast("車種選擇錯誤")
                }
                act.DaiLogDismiss()
            }
        }.start()
    }

}
