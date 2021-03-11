package com.yliu.app

import UserReq
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yliu.myapplication.LoginActivity
import com.yliu.myapplication.common.Global
import req.ReqUtils

class MainActivity : AppCompatActivity() {

    val tag = MainActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = Global.getShardUser(this)
        if (user!=null){
            ReqUtils.executeSync(user, UserReq::valid)
                .subscribe {
                    if (it.code == 0){
                        startActivity(Intent(this, ActionListActivity::class.java))
                        Global.loginUser = it.data
                        finish()
                    }else{
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                }
        }else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

}