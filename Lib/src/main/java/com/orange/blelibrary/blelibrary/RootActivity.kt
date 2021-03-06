package com.orange.blelibrary.blelibrary

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.View
import com.google.android.material.navigation.NavigationView
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.orange.blelibrary.R
import com.orange.blelibrary.blelibrary.CallBack.Dailog_SetUp_C
import com.orange.blelibrary.blelibrary.CallBack.DiapathKey
import com.orange.blelibrary.blelibrary.CallBack.Permission_C
import com.orange.blelibrary.blelibrary.tool.LanguageUtil
import java.security.Permissions
import java.util.*

abstract class RootActivity(val LayoutId: Int, val FragId: Int) : AppCompatActivity(),
        FragmentManager.OnBackStackChangedListener {
    var handler = Handler()
    private var permissionRequestCode = 10
    var Fraging: Fragment? = null
    var FragName = ""
    lateinit var PermissionCaller: Permission_C
    lateinit var rootview: View
    lateinit var NavagationRoot: DrawerLayout
    lateinit var NavaGationFrag: RootFragement
    override fun onBackStackChanged() {
        Fraging = supportFragmentManager.fragments[supportFragmentManager.fragments.size - 1]
        FragName = Fraging!!.tag!!
        ChangePageListener(FragName, Fraging!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LayoutId)
        supportFragmentManager.addOnBackStackChangedListener(this)
        rootview = findViewById<View>(android.R.id.content).rootView
        ViewInit(rootview)
    }

    fun SCREEN_ON() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    fun SCREEN_CLOSE() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public override fun onDestroy() {
        super.onDestroy()
    }

    fun GoBack() {
        supportFragmentManager.popBackStack()
        Log.d("frag", "${supportFragmentManager.backStackEntryCount}");
    }

    fun GoBack(a: Int) {
        supportFragmentManager.popBackStack(a, 1)
        Log.d("frag", "${supportFragmentManager.backStackEntryCount}");
    }

    fun GoBack(a: String) {
        supportFragmentManager.popBackStack(a, 1)
        Log.d("frag", "${supportFragmentManager.backStackEntryCount}");
    }

    fun HideKeyBoard() {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0)
    }

    open fun CloseDrawer() {
        NavagationRoot.closeDrawer(GravityCompat.START)
    }

    open fun CloseDrawer(int: Int) {
        NavagationRoot.closeDrawer(GravityCompat.START)
    }

    open fun SetNavaGation(root: Int, child: Int, frag: RootFragement) {
        NavagationRoot = findViewById(root)
        NavaGationFrag = frag
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(child, frag)
                .commit()
    }

    open fun RefreshNavaGation() {
        NavaGationFrag.ViewInit()
    }

    open fun OpenNavaGation() {
        NavagationRoot.openDrawer(GravityCompat.START)
    }

    open fun OpenNavaGation(int: Int) {
        NavagationRoot.openDrawer(int)
    }

    open fun ChangePage(Translation: Fragment, id: Int, tag: String, goback: Boolean) {
        if (goback) {
            val transaction = supportFragmentManager!!.beginTransaction()
            transaction.replace(id, Translation, tag)
                    .addToBackStack(FragName)
                    .commit()
        } else {
            Fraging = Translation
            FragName = tag
            Log.d("switch", tag)
            ChangePageListener(tag, Translation)
            val transaction = supportFragmentManager!!.beginTransaction()
            transaction.replace(id, Translation, tag)
                    .commit()
        }
    }

    open fun SetHome(Translation: Fragment, tag: String) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ChangePage(Translation, tag, false)
    }

    open fun ChangePage(Translation: Fragment, tag: String, goback: Boolean) {
        if (goback) {
            val transaction = supportFragmentManager!!.beginTransaction()
            transaction.replace(FragId, Translation, tag)
                    .addToBackStack(FragName)
                    .commit()
        } else {
            Fraging = Translation
            FragName = tag
            Log.d("switch", tag)
            ChangePageListener(tag, Translation)
            val transaction = supportFragmentManager!!.beginTransaction()
            transaction.replace(FragId, Translation, tag)
                    .commit()
        }
    }

    open fun Toast(a: String) {
        handler.post { android.widget.Toast.makeText(this, a, android.widget.Toast.LENGTH_SHORT).show() }
    }

    open fun Toast(id: Int) {
        handler.post { android.widget.Toast.makeText(this, getString(id), android.widget.Toast.LENGTH_SHORT).show() }
    }

    var mDialog: Dialog? = null
    var nowDia = -1
    lateinit var DiaCaller: Dailog_SetUp_C
    fun ShowDaiLog(Layout: Int, touchcancel: Boolean, style: Int, caller: Dailog_SetUp_C) {
        try {
            if (mDialog == null) {
                mDialog = Dialog(this, style)
                mDialog!!.setContentView(Layout)
                mDialog!!.getWindow()!!.setLayout(
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                )
                mDialog!!.setCancelable(true)
                mDialog!!.setCanceledOnTouchOutside(touchcancel)
                mDialog!!.show()
                if (touchcancel) {
                    getAllChildViews(mDialog!!.getWindow().getDecorView())
                }
            } else {
                if (!mDialog!!.isShowing()) {
                    mDialog = Dialog(this, style)
                    mDialog!!.setContentView(Layout)
                    mDialog!!.getWindow()!!.setLayout(
                            WindowManager.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.WRAP_CONTENT
                    )
                    mDialog!!.setCancelable(true)
                    mDialog!!.setCanceledOnTouchOutside(touchcancel)
                    mDialog!!.show()
                    if (touchcancel) {
                        getAllChildViews(mDialog!!.getWindow().getDecorView())
                    }
                } else {
                    if (nowDia != Layout) {
                        DaiLogDismiss()
                        mDialog = Dialog(this, style)
                        mDialog!!.setContentView(Layout)
                        mDialog!!.getWindow()!!.setLayout(
                                WindowManager.LayoutParams.WRAP_CONTENT,
                                WindowManager.LayoutParams.WRAP_CONTENT
                        )
                        mDialog!!.setCancelable(true)
                        mDialog!!.setCanceledOnTouchOutside(touchcancel)
                        mDialog!!.show()
                        if (touchcancel) {
                            getAllChildViews(mDialog!!.getWindow().getDecorView())
                        }
                    }
                }
            }
            nowDia = Layout
            caller.SetUP(mDialog!!, this)
            DiaCaller = caller
        } catch (e: Exception) {
            Thread.sleep(1000)
            e.printStackTrace()
        }
    }

    fun ShowDaiLog(Layout: Int, touchcancel: Boolean, swip: Boolean, caller: Dailog_SetUp_C) {
        try {
            if (mDialog == null) {
                mDialog = Dialog(this, if (swip) R.style.SwipTheme else R.style.MyDialog)
                mDialog!!.setContentView(Layout)
                mDialog!!.getWindow()!!.setLayout(
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                )
                mDialog!!.setCancelable(true)
                mDialog!!.setCanceledOnTouchOutside(touchcancel)
                mDialog!!.show()
                if (touchcancel) {
                    getAllChildViews(mDialog!!.getWindow().getDecorView())
                }
            } else {
                if (!mDialog!!.isShowing()) {
                    mDialog = Dialog(this, if (swip) R.style.SwipTheme else R.style.MyDialog)
                    mDialog!!.setContentView(Layout)
                    mDialog!!.getWindow()!!.setLayout(
                            WindowManager.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.WRAP_CONTENT
                    )
                    mDialog!!.setCancelable(true)
                    mDialog!!.setCanceledOnTouchOutside(touchcancel)
                    mDialog!!.show()
                    if (touchcancel) {
                        getAllChildViews(mDialog!!.getWindow().getDecorView())
                    }
                } else {
                    if (nowDia != Layout) {
                        DaiLogDismiss()
                        mDialog = Dialog(this, if (swip) R.style.SwipTheme else R.style.MyDialog)
                        mDialog!!.setContentView(Layout)
                        mDialog!!.getWindow()!!.setLayout(
                                WindowManager.LayoutParams.WRAP_CONTENT,
                                WindowManager.LayoutParams.WRAP_CONTENT
                        )
                        mDialog!!.setCancelable(true)
                        mDialog!!.setCanceledOnTouchOutside(touchcancel)
                        mDialog!!.show()
                        if (touchcancel) {
                            getAllChildViews(mDialog!!.getWindow().getDecorView())
                        }
                    }
                }
            }
            nowDia = Layout
            caller.SetUP(mDialog!!, this)
            DiaCaller = caller
        } catch (e: Exception) {
            Thread.sleep(1000)
            e.printStackTrace()
        }
    }

    private fun getAllChildViews(view: View): List<View> {
        val allchildren = ArrayList<View>()
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val viewchild = view.getChildAt(i)
                allchildren.add(viewchild)
                Log.d("ChildView", "$viewchild")
                allchildren.addAll(getAllChildViews(viewchild))
                if ("$viewchild".contains("RelativeLayout")) {
                    viewchild.setOnClickListener { mDialog!!.dismiss() }
                    return allchildren
                }
            }
        }
        return allchildren
    }

    fun DaiLogDismiss() {
        try {
            DiaCaller.Dismiss()
            mDialog!!.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }

    val LOCALE_ENGLISH = "en"
    val LOCALE_CHINESE = "zh"
    val LOCALE_TAIWAIN = "tw"
    val LOCALE_ITALIANO = "it"
    val LOCALE_DE = "de"
    open fun Laninit() {
        val profilePreferences = getSharedPreferences("Setting", Context.MODE_PRIVATE)
        when (profilePreferences.getString("Lan", LOCALE_ENGLISH)) {
            LOCALE_ENGLISH -> {
                LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_ENGLISH);
            }
            LOCALE_CHINESE -> {
                LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_CHINESE);
            }
            LOCALE_TAIWAIN -> {
                LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_TAIWAIN);
            }
            LOCALE_ITALIANO -> {
                LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_ITALIANO);
            }
            LOCALE_DE -> {
                LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_DE);
            }
        }
    }

    open fun GoMenu() {
        //返回首页,清除栈顶
        supportFragmentManager.popBackStack(null, 1)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        Log.e("event", "" + event)
        if (Fraging != null) {
            (Fraging as DiapathKey).dispatchKeyEvent(event)
        }//按鍵分發
        KeyLinsten(event)
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 請求成功
     */
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permissionRequestCode ->
                if (grantResults.isNotEmpty()) {
                    for (i in grantResults.indices) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            PermissionCaller.RequestSuccess(permissions[i])
                        } else {
                            PermissionCaller.RequestFalse(permissions[i])
                        }
                    }
                }
        }
    }

    /**
     * 權限請求
     */
    open fun GetPermission(Permissions: Array<String>, caller: Permission_C, RequestCode: Int) {
        PermissionCaller = caller
        permissionRequestCode = RequestCode
        val permissionDeniedList = ArrayList<String>()
        for (permission in Permissions) {
            val permissionCheck = ContextCompat.checkSelfPermission(this, permission)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                caller.RequestSuccess(permission)
            } else {
                permissionDeniedList.add(permission)
            }
        }
        if (!permissionDeniedList.isEmpty()) {
            val deniedPermissions = permissionDeniedList.toTypedArray()
            ActivityCompat.requestPermissions(this, deniedPermissions, permissionRequestCode)
        }
    }
    //===============================Abstract Function===============================
    /**
     * 父頁面的載入
     */
    abstract fun ViewInit(rootview: View)

    /**
     * 頁面切換監聽
     */
    abstract fun ChangePageListener(tag: String, frag: Fragment);

    /**
     * 按鍵的監聽
     */
    abstract fun KeyLinsten(event: KeyEvent)
}
