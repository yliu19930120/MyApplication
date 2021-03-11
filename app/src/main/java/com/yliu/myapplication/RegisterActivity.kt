package com.yliu.app

import User
import UserReq
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.yliu.myapplication.common.Global
import com.yliu.myapplication.common.Utils
import req.ReqUtils

class RegisterActivity : AppCompatActivity() {


    var tag = RegisterActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<Button>(R.id.cancel_button).setOnClickListener {
            this.finish()
        }

        findViewById<Button>(R.id.sure_button).setOnClickListener {
            register()
        }
    }

    fun register(){
        val name = findViewById<EditText>(R.id.name_text).text.toString().trim()
        val username = findViewById<EditText>(R.id.username_text).text.toString().trim()
        val password = findViewById<EditText>(R.id.password_text).text.toString().trim()
        val phone = findViewById<EditText>(R.id.phone_text).text.toString().trim()

        val user = User(name,password,username,phone)
        try {
            ReqUtils.executeSync(user,UserReq::reqgister).subscribe {
                if (it.code == 0){
                    Global.loginUser = it.data
                    val intent = Intent(this, ActionListActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Utils.alert(this,"${it.message}")
                }
            }
        }catch (e :Exception){
            Log.e(tag,"异常",e)
            Utils.alert(this,e.message)
        }

    }
}