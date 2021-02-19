package com.yliu.myapplication.req

import com.yliu.myapplication.common.Global
import okhttp3.Interceptor
import okhttp3.Response
import req.ReqUtils

class ReqInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        //加入登录的token
        if(Global.loginUser!=null){
            request = request.newBuilder().addHeader("X-AUTH-TOKEN", Global.loginUser!!.token!!).build()
        }

        val response = chain.proceed(request)

        return response
    }
}