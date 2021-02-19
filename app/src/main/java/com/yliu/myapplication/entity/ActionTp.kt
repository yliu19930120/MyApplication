package com.yliu.myapplication.entity

import java.time.LocalDate

class ActionTp(val nums: Int?, val date:LocalDate, val topic:String) {
    constructor(date:LocalDate, topic: String):this(null,date,topic)
    constructor(topic: String):this(null, LocalDate.now(),topic)
}