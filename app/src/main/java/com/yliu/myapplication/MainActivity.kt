package com.yliu.app

import User
import UserReq
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.yliu.myapplication.common.Global
import com.yliu.myapplication.common.Utils
import req.ReqUtils

class MainActivity : AppCompatActivity() {

    var tag = MainActivity::class.java.name

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

                ReqUtils.postSync(user,UserReq::login)
                    .subscribe {
                        if (it.code == 0){
                            Global.loginUser = it.data
                            val intent = Intent(this, ActionActivity::class.java)
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
    }
}