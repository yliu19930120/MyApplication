package com.yliu.myapplication.req

import android.util.Log
import com.yliu.myapplication.common.Utils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import req.Result

class BaseObserver<T>(val action:(p:Result<T>)->Unit,val errorFunc:(p:Result<T>)->Unit):Observer<Result<T>> {

    constructor(action:(p:Result<T>)->Unit):this(action,{result -> Utils.alert(result.message)})

    private val TAG = BaseObserver::class.java.name

    private val loadingDialog = Utils.getloadingDialog()

    override fun onComplete() {
        loadingDialog?.dismiss()
    }

    override fun onSubscribe(d: Disposable) {
        loadingDialog?.show()
    }

    override fun onNext(t: Result<T>) {
        try {
            if (t.code == 0){
                action(t)
            }else {
                errorFunc(t)
            }
        }catch (e:Exception){
            onError(e)
        }

    }

    override fun onError(e: Throwable) {
        loadingDialog?.dismiss()
        Log.e(TAG,"异常:${e.message}")
        Utils.alert("网络请求异常 ${e.message}")
    }

}