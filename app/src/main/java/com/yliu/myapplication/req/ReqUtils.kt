package req

import com.yliu.myapplication.common.GsonConfig
import com.yliu.myapplication.req.ReqInterceptor
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.function.Consumer

object ReqUtils {

    val url = "http://106.52.133.51:10001"

    val client = OkHttpClient.Builder().addInterceptor(ReqInterceptor()).build()

    val retrofit = Retrofit.Builder().baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(GsonConfig.gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()

    private val REQ_MAP = mapOf<String,Object>()


    inline fun<reified S,P,T> executeSync(p:P, reqFunc:S.(p :P)-> Observable<Result<T>>): Observable<Result<T>>{
        val create = getReq(S::class.java)
        return create.reqFunc(p)
            .compose(schedules())

    }



    inline fun<reified S,T> executeSync(reqFunc:S.()-> Observable<Result<T>>): Observable<Result<T>>{
        val create = getReq(S::class.java)
        return create.reqFunc().compose(schedules())
    }

    fun <T> schedules() = ObservableTransformer<T, T> {
        it.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun<T> getReq(clazz: Class<T>):T{
        return REQ_MAP.getOrDefault(clazz.name, retrofit.create(clazz) as Object) as T
    }
}
