package com.yliu.app

import User
import UserReq
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.yliu.myapplication.LoginActivity
import com.yliu.myapplication.common.Global
import com.yliu.myapplication.common.GsonConfig
import com.yliu.myapplication.common.Utils
import req.ReqUtils

class MainActivity : AppCompatActivity() {

    val loginUserKey = "loginUser"

    val tag = MainActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userJson = getPreferences(Activity.MODE_PRIVATE)?.getString(loginUserKey,null)
        if(userJson!=null){
            val user = GsonConfig.gson.fromJson(userJson,User::class.java)
            ReqUtils.executeSync(user, UserReq::valid)
                .subscribe {
                    if (it.code == 0) {
                        Global.loginUser = it.data
                        startActivity(Intent(this, ActionListActivity::class.java))
                        finish()
                    }else {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                }
        }else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onStop() {
        getPreferences(Activity.MODE_PRIVATE)?.
        edit()?.
        putString(loginUserKey, GsonConfig.gson.toJson(Global.loginUser))?.
        commit()
        super.onStop()
    }
}