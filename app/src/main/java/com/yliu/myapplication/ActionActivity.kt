package com.yliu.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.yliu.app.ActionListActivity
import com.yliu.app.R
import com.yliu.myapplication.common.Global
import com.yliu.myapplication.common.Utils
import com.yliu.myapplication.entity.Action
import com.yliu.myapplication.req.ActionReq
import req.ReqUtils
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ActionActivity() : AppCompatActivity() {

    val traningDate:LocalDate = LocalDate.now()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action)

        val traningDateTV = findViewById<TextView>(R.id.traning_date_tv)
        traningDateTV.setText(traningDate.format(DateTimeFormatter.ofPattern("yyyy年M月d日")))

        val typeL1Spinner = findViewById<Spinner>(R.id.type_l1_spinner)

        val typel1TextView = findViewById<TextView>(R.id.typel1_text)

        val actionTypel1s = listOf("胸部","背部","肩部","腿部","手臂","腹部","体能","有氧","核心肌群","其他")

        typeL1Spinner.adapter = ArrayAdapter<String>(this, R.layout.action_typel1s,actionTypel1s)

        typeL1Spinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position:        Int, id: Long) {
                typel1TextView.setText(actionTypel1s[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }



        findViewById<Button>(R.id.sure_button).setOnClickListener {submit()}

        findViewById<Button>(R.id.cancel_button).setOnClickListener{finish()}


    }

    fun submit(){
        val traningDateTV = findViewById<TextView>(R.id.traning_date_tv)
        val typel1TextView = findViewById<TextView>(R.id.typel1_text)
        val actionNameTV = findViewById<TextView>(R.id.action_name_tv)
        val numsTV = findViewById<TextView>(R.id.nums_tv)
        val speedNB = findViewById<TextView>(R.id.speed_number)
        val weightNB = findViewById<TextView>(R.id.weight_number)
        val groupNB = findViewById<TextView>(R.id.group_times_number)
        val timsNB = findViewById<TextView>(R.id.times_number)
        val unilateralSW = findViewById<TextView>(R.id.unilateral_switch)
        val sureButton = findViewById<Button>(R.id.sure_button)

        val action = Action(Global.loginUser!!.id!!,
            actionNameTV.text.toString(),
            typel1TextView.text.toString(),
            null,
            weightNB.text.toString().toDoubleOrNull(),
            numsTV.text.toString().toIntOrNull(),
            groupNB.text.toString().toIntOrNull(),
            unilateralSW.text.toString(),
            traningDate,
            speedNB.text.toString().toDoubleOrNull(),
            timsNB.text.toString().toDoubleOrNull())

        ReqUtils.postSync(action, ActionReq::addAction).subscribe {
            if (it.code == 0){
                finish()
            }else{
                Utils.alert(this,"${it.message}")
            }
        }
    }
}