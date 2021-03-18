package com.yliu.myapplication

import androidx.appcompat.app.AppCompatActivity
import com.yliu.myapplication.common.ActivityManager
import com.yliu.myapplication.common.Global

open class BaseActivity() : AppCompatActivity() {

    override fun onStart() {
        ActivityManager.addActivity(this)
        super.onStart()
    }

    override fun onStop() {
//        val shardUser = Global.getShardUser(this)
//        if (shardUser !=null){
//            Global.commitUser(this)
//        }
        ActivityManager.remove(this)
        super.onStop()
    }
}