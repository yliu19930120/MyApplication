package req

import com.yliu.myapplication.common.GsonConfig
import com.yliu.myapplication.req.ReqInterceptor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ReqUtils {

    val url = "http://106.52.133.51:10001"

    val client = OkHttpClient.Builder().addInterceptor(ReqInterceptor()).build()

    val retrofit = Retrofit.Builder().baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(GsonConfig.gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()

    inline fun<reified S,P,T> executeSync(p:P, reqFunc:S.(p :P)-> Observable<Result<T>>): Observable<Result<T>>{
        val create = retrofit.create(S::class.java)

        return create.reqFunc(p)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())

    }

}
