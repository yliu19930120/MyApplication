package com.yliu.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.yliu.app.R
import com.yliu.myapplication.common.Global
import com.yliu.myapplication.entity.Action
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ActionActivity(val traningDate:LocalDate) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action)

        val traningDateTV = findViewById<TextView>(R.id.traning_date_tv)
        traningDateTV.setText(traningDate.format(DateTimeFormatter.ofPattern("yyyy年M月d日")))

        val typeL1Spinner = findViewById<Spinner>(R.id.type_l1_spinner)


        val actionTypel1s = listOf("胸部","背部","肩部","腿部","手臂","腹部","体能","有氧","核心肌群","其他")

        typeL1Spinner.adapter = ArrayAdapter<String>(this, R.layout.action_typel1s,actionTypel1s)


    }

    fun submit(){
        val traningDateTV = findViewById<TextView>(R.id.traning_date_tv)
        val typeL1Spinner = findViewById<Spinner>(R.id.type_l1_spinner)
        val actionNameTV = findViewById<TextView>(R.id.action_name_tv)
        val numsTV = findViewById<TextView>(R.id.nums_tv)
        val speedNB = findViewById<TextView>(R.id.speed_number)
        val weightNB = findViewById<TextView>(R.id.weight_number)
        val groupNB = findViewById<TextView>(R.id.group_times_number)
        val timsNB = findViewById<TextView>(R.id.times_number)
        val unilateralSW = findViewById<TextView>(R.id.unilateral_switch)
        val sureButton = findViewById<Button>(R.id.sure_button)

//        Action(Global.loginUser!!.id!!,actionNameTV.text.toString(),typeL1Spinner.text,null,weightNB.text,numsTV.text,groupNB.text,unilateralSW.text,traningDate,speedNB.text,timsNB.text)
    }
}