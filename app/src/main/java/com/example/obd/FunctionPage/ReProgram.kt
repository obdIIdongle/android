package com.example.obd.FunctionPage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.view.View
import com.example.obd.MainActivity.HomeFragement
import com.example.obd.MainActivity.MainPeace
import com.example.obd.Myapp
import com.orange.obd.R

class ReProgram : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_re_program)

    }
    fun onclick(view: View){
        when(view.id){
            R.id.textView15->{
                this.finish()
                val fragement= Key_ID()
                val transaction =  (application as Myapp).act.supportFragmentManager.beginTransaction()
                (application as Myapp).act.supportFragmentManager.fragments.removeAt((application as Myapp).act.supportFragmentManager.fragments.size-1)
                transaction.replace(R.id.frage,fragement )
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                        .addToBackStack(null)
                        .commit()
            }
            R.id.textView14->{
                this.finish()
                val transaction =  (application as Myapp).act.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frage, HomeFragement(),"Home")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                        .commit()
            }
        }
    }
}
