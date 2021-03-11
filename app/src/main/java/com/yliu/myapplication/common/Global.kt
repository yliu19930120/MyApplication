package com.yliu.myapplication.common

import User
import UserReq
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.yliu.app.ActionListActivity
import com.yliu.myapplication.LoginActivity
import req.ReqUtils

object Global {
    //全局的登录用户
    var loginUser: User? = null

    val loginUserKey = "loginUser"

    var tag = Global::class.java.name

    fun commitUser(context: Context){
        val json = GsonConfig.gson.toJson(Global.loginUser)
        val preferences = context.getSharedPreferences(loginUserKey,Activity.MODE_PRIVATE)
        preferences?.
        edit()?.
        putString(loginUserKey, json)?.
        commit()
    }

    fun getShardUser(context: Context):User? {
        val sharedPreferences = context.getSharedPreferences(loginUserKey, Activity.MODE_PRIVATE)
        val userJson = sharedPreferences?.getString(Global.loginUserKey,null)
        return GsonConfig.fromJson(userJson)
    }
}