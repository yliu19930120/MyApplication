package com.yliu.myapplication.entity

import java.time.LocalDate

class Action(
    val id:String?,
    val actionName:String,
             val typeL1:String?,
             val typeL2:String?,
             val weight:Double?,
             val nums:Int?,
             val groupsTimes:Int?,
             val unilateral:String?,
             val traningDate:LocalDate,
             val speed:Double?,
             val times:Double?) {
    constructor(actionName: String, traningDate:LocalDate):this(null,actionName,null,null,null,null,null,null,traningDate,null,null)
    constructor(actionName: String, typeL1:String?,weight:Double?,nums:Int?,traningDate:LocalDate):this(null,actionName,typeL1,null,weight,nums,null,null,traningDate,null,null)
}