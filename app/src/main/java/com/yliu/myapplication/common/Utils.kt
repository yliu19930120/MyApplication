package com.yliu.myapplication.common

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.widget.Toast

object Utils {


    fun alert(context: Context, msg: String?){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    fun alert(msg: String?){
        val context = ActivityManager.lastActivity()
        if (context !=null){
            alert(context,msg)
        }
    }

    fun getloadingDialog():AlertDialog?{
        val context = ActivityManager.lastActivity()
        if (context == null) return null
        return AlertDialog.Builder(context)
                .setMessage("请稍等...")
                .create()
    }
}