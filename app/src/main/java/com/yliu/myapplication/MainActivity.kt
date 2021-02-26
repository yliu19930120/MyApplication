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
import com.yliu.myapplication.common.Global
import com.yliu.myapplication.common.GsonConfig
import com.yliu.myapplication.common.Utils
import req.ReqUtils

class MainActivity : AppCompatActivity() {

    var tag = MainActivity::class.java.name

    val loginUserKey = "loginUser"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.register_button).setOnClickListener {

           val intent = Intent(this, RegisterActivity::class.java)

            startActivity(intent)
        }

        findViewById<Button>(R.id.login_button).setOnClickListener {

            val username = findViewById<EditText>(R.id.username_text).text.toString().trim()
            val password = findViewById<EditText>(R.id.password_text).text.toString().trim()

            try {
                val user = User(username,password)

                ReqUtils.executeSync(user,UserReq::login)
                    .subscribe {
                        if (it.code == 0){
                            Global.loginUser = it.data
                            val intent = Intent(this, ActionListActivity::class.java)
                            startActivity(intent)
                        }else{
                            Utils.alert(this,"${it.message}")
                        }
                    }

            } catch (e: Exception) {
                Log.e(tag,"异常",e)
                Utils.alert(this,e.message)
            }

        }
        val userJson = getPreferences(Activity.MODE_PRIVATE)?.getString(loginUserKey,null)

        if(userJson!=null){
            val user = GsonConfig.gson.fromJson(userJson,User::class.java)
            ReqUtils.executeSync(user,UserReq::valid)
                    .subscribe {
                        if (it.code == 0){
                            Global.loginUser = it.data
                            val intent = Intent(this, ActionListActivity::class.java)
                            startActivity(intent)
                        }
                    }

        }

    }

    override fun onStop() {
        getPreferences(Activity.MODE_PRIVATE)?.
        edit()?.
        putString(loginUserKey,GsonConfig.gson.toJson(Global.loginUser))?.
        commit()
        super.onStop()
    }
}