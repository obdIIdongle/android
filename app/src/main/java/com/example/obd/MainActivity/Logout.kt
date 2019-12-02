package com.example.obd.MainActivity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.orange.obd.R

class Logout : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logout)
    }
    fun onclick(view: View){
        when(view.id){
            R.id.ok->{
                this.finish()}
            R.id.textView15->{
                val profilePreferences = getSharedPreferences("Setting", Context.MODE_PRIVATE)
                profilePreferences.edit().putString("admin","nodata").commit()
                android.os.Process.killProcess(android.os.Process.myPid())
            }
        }
    }
}
