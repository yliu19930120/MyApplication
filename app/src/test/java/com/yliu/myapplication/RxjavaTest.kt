package com.yliu.myapplication

import User
import UserReq
import android.os.Handler
import android.os.Looper
import com.yliu.myapplication.req.BaseObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import req.ReqUtils


fun main() {

    val user = User("soinbbzn","5716597Asd@")

    ReqUtils.retrofit.create(UserReq::class.java)
            .login(user)
            .subscribe(BaseObserver{
                println(it.data.token)
            })


}