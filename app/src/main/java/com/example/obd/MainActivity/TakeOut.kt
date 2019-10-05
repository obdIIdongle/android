package com.example.obd.MainActivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.orange.obd.R

class TakeOut : AppCompatActivity() {
companion object{
   var DS_OR_CO=0
}
    lateinit var titleText:TextView
    lateinit var Content:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_out)
        titleText=findViewById(R.id.textView16)
        Content=findViewById(R.id.textView13)
        when(DS_OR_CO){
            0->{
                titleText.text="藍芽斷線"
                Content.text="請確認"
            }
            1->{
                titleText.text="寫入完成"
                Content.text="請將車輛OBD II DONGLE取下"
            }
        }

    }
    fun onclick(view:View){
        when(view.id){
            R.id.textView15->{
                this.finish()
            }
        }
    }
}
