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
    companion object{
        var position=0;
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_re_program)

    }
    fun onclick(view: View){
        when(view.id){
            R.id.textView15->{
                this.finish()
                when(position){
                    0->{
                        (application as Myapp).act.GoBack()
                        (application as Myapp).act.ChangePage(Show_Read(),R.id.frage,"Show_Read",true)
                    }
                    1->{
                        (application as Myapp).act.GoBack()
                        (application as Myapp).act.ChangePage(Key_ID(),R.id.frage,"Key_ID",true)
                    }
                }

            }
            R.id.textView14->{
                this.finish()
                (application as Myapp).act.supportFragmentManager.popBackStack(null,1)
                (application as Myapp).act.bleServiceControl.disconnect()
            }
        }
    }
}
