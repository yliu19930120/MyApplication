package com.yliu.myapplication.common

import com.yliu.myapplication.BaseActivity
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque

object ActivityManager {

    val activityDeque:Deque<BaseActivity> = ConcurrentLinkedDeque()

    fun lastActivity() = activityDeque.peekLast()

    fun addActivity(activity: BaseActivity) = activityDeque.addLast(activity)

    fun remove(activity: BaseActivity){
        activityDeque.remove(activity)
    }

    fun destroiedAll(){
        activityDeque.forEach{ it.finish() }
        activityDeque.clear()
    }
}