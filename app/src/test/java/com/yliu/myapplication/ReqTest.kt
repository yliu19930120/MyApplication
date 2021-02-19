package com.yliu.myapplication

import User
import UserReq
import req.ReqUtils

fun main() {
    val user = User("soinbbzn","123")
    val post = ReqUtils.post(user, UserReq::login2)

    println(ReqUtils.gson.toJson(post))

}