package com.yliu.myapplication.req

import com.yliu.myapplication.entity.Action
import io.reactivex.Observable
import retrofit2.http.POST
import java.time.LocalDate
import req.Result
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface ActionReq {

    @GET("/yliu/action/action")
    fun getActions(@Query("userId") userId:String, @Query("traningDate") traningDate:LocalDate):Observable<Result<List<Action>>>

    @POST("/yliu/action/save")
    fun addAction(@Body action: Action):Observable<Result<String>>

    @POST("/yliu/action/update")
    fun updateAction(@Body action: Action):Observable<Result<String>>

    @GET("/yliu/action/delete")
    fun deleteAction(@Query("userId") userId:String,@Query("actionIds")vararg actionIds:String):Observable<Result<String>>

    @GET("/yliu/action/dates")
    fun actionDates(@Query("userId") userId: String):Observable<Result<List<LocalDate>>>
}