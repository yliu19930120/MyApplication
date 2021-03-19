package com.yliu.myapplication.common

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateUtils {

    fun parse(str:String) = LocalDate.parse(str, DateTimeFormatter.ofPattern(GsonConfig.dateFormat))

    fun format(date: LocalDate) = date.format(DateTimeFormatter.ofPattern(GsonConfig.dateFormat))

    fun format(l: Long) = toLocalDate(l)?.format(DateTimeFormatter.ofPattern(GsonConfig.dateFormat))

    fun toLong(date: LocalDate) = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    fun toLong(str: String) = toLong(parse(str))

    fun toLocalDate(l :Long?)  = if (l==null) null else Instant.ofEpochMilli(l).atZone(ZoneId.systemDefault()).toLocalDate()


}