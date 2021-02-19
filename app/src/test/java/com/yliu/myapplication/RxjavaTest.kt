package com.yliu.myapplication

import User
import UserReq
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import req.ReqUtils
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


fun main() {

    val user = User("soinbbzn","5716597Asd@")

    val retrofit = Retrofit.Builder().baseUrl(ReqUtils.url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    val login = retrofit.create(UserReq::class.java).login(user)

    login.map { rep->rep.message }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                println(it)
            }
}