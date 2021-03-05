package com.yliu.myapplication.common

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

object Utils {

    fun alert(context: Context, msg: String?){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }


}