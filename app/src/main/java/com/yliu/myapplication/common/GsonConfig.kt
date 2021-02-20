package com.yliu.myapplication.common

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object GsonConfig {

    val dateFormat = "yyyy-MM-dd"
    val dateTimeFormatter = "yyyy-MM-dd HH:MM:SS"
    val dateDeserializer = JsonDeserializer<LocalDate>{json, typeOfT, context ->
        LocalDate.parse(json.asString, DateTimeFormatter.ofPattern(dateFormat))
    }

    val dateTimeDeserializer = JsonDeserializer<LocalDateTime>{json, typeOfT, context ->
        LocalDateTime.parse(json.asString, DateTimeFormatter.ofPattern(dateTimeFormatter))
    }

    val dateSerializable = JsonSerializer<LocalDate>{ src, typeOfSrc, context ->
         JsonPrimitive(src.format(DateTimeFormatter.ofPattern(dateFormat)))
    }

    val dateTimeSerializable = JsonSerializer<LocalDateTime>{ src, typeOfSrc, context ->
        JsonPrimitive(src.format(DateTimeFormatter.ofPattern(dateTimeFormatter)))
    }

    val gson = GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java,dateDeserializer)
            .registerTypeAdapter(LocalDateTime::class.java,dateTimeDeserializer)
            .registerTypeAdapter(LocalDate::class.java,dateSerializable)
            .registerTypeAdapter(LocalDateTime::class.java,dateTimeSerializable)
            .setDateFormat("yyyy-MM-dd")
            .create()
}