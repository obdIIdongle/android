package com.example.obd.FunctionPage


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.obd.MainActivity.MainPeace
import com.example.obd.tool.FtpManager

import com.orange.obd.R
import kotlinx.android.synthetic.main.fragment_key__id.view.*
import kotlinx.android.synthetic.main.fragment_show__read.view.*
import kotlinx.android.synthetic.main.fragment_show__read.view.program
import kotlinx.android.synthetic.main.fragment_key__id.view.Lft as Lft1
import kotlinx.android.synthetic.main.fragment_key__id.view.Rft as Rft1
import kotlinx.android.synthetic.main.fragment_show__read.view.Lrt as Lrt1
import kotlinx.android.synthetic.main.fragment_show__read.view.Rrt as Rrt1

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
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
        act.LoadingUI(act.getResources().getString(R.string.Programming),0)
        Downs19()

        return rootView
    }
    fun Downs19(){
        if(Key_ID.s19 ==directfit){
            act.LoadingSuccessUI()
            SetId()
            return}
        handler.post { act.back.isEnabled=false }
        Thread{
            val a= FtpManager.DownS19(directfit, act)
            if(a){
                if(!act.command.HandShake()){
                    act.command.Reboot()
                }
                val Pro=act.command.HandShake()&& act.command.WriteFlash(act,directfit,296,act)
                handler.post {
                    act.back.isEnabled=true
                    act.LoadingSuccessUI()
                    if(Pro){
//                            Toast.makeText(activity,"燒錄成功",Toast.LENGTH_SHORT).show();
                        Key_ID.s19 =directfit
                        SetId()
                    }else{
//                            Toast.makeText(activity,"燒錄失敗",Toast.LENGTH_SHORT).show();
                        ReProgram.position=0
                        val intent = Intent(act,ReProgram::class.java)
                        startActivity(intent)

                    }
                }
            }else{
                handler.post {
                    act.LoadingSuccessUI()
                    act.back.isEnabled=true
                    ReProgram.position=0
                    val intent = Intent(act,ReProgram::class.java)
                    startActivity(intent)}

            }
        }.start()
    }
    var handler = Handler()
    fun SetId() {
        act.LoadingUI("讀取中 ", 0);
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
                    ReProgram.position=0
                    val intent = Intent(act,ReProgram::class.java)
                    startActivity(intent)
                }
                act.LoadingSuccessUI()
            }
        }.start()
    }

}
