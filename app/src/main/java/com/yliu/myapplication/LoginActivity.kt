package com.yliu.myapplication

import User
import UserReq
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.yliu.app.ActionListActivity
import com.yliu.app.MainActivity
import com.yliu.app.R
import com.yliu.app.RegisterActivity
import com.yliu.myapplication.common.Global
import com.yliu.myapplication.common.GsonConfig
import com.yliu.myapplication.common.Utils
import req.ReqUtils


class LoginActivity : AppCompatActivity() {

    var tag = MainActivity::class.java.name
    val loginUserKey = "loginUser"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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
                            finish()
                        }else{
                            Utils.alert(this,"${it.message}")
                        }
                    }

            } catch (e: Exception) {
                Log.e(tag,"异常",e)
                Utils.alert(this,e.message)
            }

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