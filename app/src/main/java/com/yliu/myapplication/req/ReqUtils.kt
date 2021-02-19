package req

import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.yliu.app.ActionActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ReqUtils {

    val url = "http://106.52.133.51:10001"

    val gson = Gson()

    val retrofit = Retrofit.Builder().baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    inline fun<reified S,T> get(reqFunc :S.()-> Call<Result<T>>):Result<T>{
        val create = retrofit.create(S::class.java)

        val call = create.reqFunc()

        return getBody(call)
    }

    inline fun<reified S,P,T> post(p:P, reqFunc:S.(p :P)-> Call<Result<T>>):Result<T>{
        val create = retrofit.create(S::class.java)

        val call = create.reqFunc(p)

        return getBody(call)
    }


    inline fun<reified S,P,T> postSync(p:P, reqFunc:S.(p :P)-> Observable<Result<T>>): Observable<Result<T>>{
        val create = retrofit.create(S::class.java)

        return create.reqFunc(p)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun <T> getBody(call :Call<T>):T{

        val execute = call.execute()

        val body = try {
            val body = execute.body()
            return body!!
        } catch (e: Exception) {
            throw Exception()
        }
        return body!!
    }

}
