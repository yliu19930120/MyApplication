package com.yliu.myapplication.common

import android.app.AlertDialog
import android.content.Context

object Utils {

    fun alert(context: Context, msg: String?){
        AlertDialog.Builder(context)
            .setMessage(msg)
            .setCancelable(true)
            .create()
            .show()
    }

    fun<T> alert(context: Context, result:Result<T>?){

    }


}