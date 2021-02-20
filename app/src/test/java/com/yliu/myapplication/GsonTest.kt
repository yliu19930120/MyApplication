package com.yliu.myapplication

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yliu.myapplication.common.GsonConfig
import com.yliu.myapplication.entity.Action
import java.time.LocalDate


fun main() {
    val json = "{\n" +
            "      \"userId\": \"6023d0cb29f42e0a6b10f1ef\",\n" +
            "      \"actionName\": \"坐姿腿部屈伸\",\n" +
            "      \"typeL1\": \"腿部\",\n" +
            "      \"typeL2\": \"\",\n" +
            "      \"weight\": 25,\n" +
            "      \"nums\": 15,\n" +
            "      \"groupsTimes\": null,\n" +
            "      \"unilateral\": \"0\",\n" +
            "      \"traningDate\": \"2019-02-18\",\n" +
            "      \"speed\": null,\n" +
            "      \"times\": null\n" +
            "    }"

    val gson = GsonConfig.gson
    val fromJson = gson.fromJson(json, Action::class.java)
    println(fromJson.actionName)

//    val action = Action("卧推", LocalDate.of(2019,2,18))
//
//    val json = gson.toJson(action)
//
//    println(json)
}