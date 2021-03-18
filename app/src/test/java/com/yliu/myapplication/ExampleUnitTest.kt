package com.yliu.myapplication

import com.yliu.myapplication.common.Cost
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        val typeL1 = "有氧"

        println(typeL1 in Cost.AEROBIC_TYPES)
    }
}