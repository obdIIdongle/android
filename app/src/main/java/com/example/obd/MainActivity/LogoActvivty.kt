package com.example.obd.MainActivity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.obd.MainPeace
import com.example.obd.tool.LanguageUtil
import com.orange.obd.R
import java.util.ArrayList

class LogoActvivty : AppCompatActivity() {
    companion object {
        private val permissionRequestCode = 10
        private val Permissions = arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo_actvivty)
    }
    override fun onResume() {
        super.onResume()
        checkPermissions()
    }
    private fun checkPermissions() {
        val permissionDeniedList = ArrayList<String>()
        for (permission in LogoActvivty.Permissions) {
            val permissionCheck = ContextCompat.checkSelfPermission(this, permission)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted(permission)
            } else {
                permissionDeniedList.add(permission)
            }
        }
        if (!permissionDeniedList.isEmpty()) {
            val deniedPermissions = permissionDeniedList.toTypedArray()
            ActivityCompat.requestPermissions(this, deniedPermissions, LogoActvivty.permissionRequestCode)
        }
    }

    private fun onPermissionGranted(permission: String) {
        when (permission) {
            Manifest.permission.READ_EXTERNAL_STORAGE -> {
            }
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                val profilePreferences = getSharedPreferences("Setting", Context.MODE_PRIVATE)
                val a= profilePreferences.getString("Language","English")
                when(a){
                    "繁體中文"->{ LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_TAIWAIN);}
                    "简体中文"->{ LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_CHINESE);}
                    "Deutsch"->{ LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_DE);}
                    "English"->{ LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_ENGLISH);}
                    "Italiano"->{ LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_ITALIANO);}
                }
                val handler = Handler()
                handler.postDelayed(Runnable {
                    val intent = Intent(this, MainPeace::class.java)
                    startActivity(intent)
                    finish()
                },2000)

            }
        }
    }
}
