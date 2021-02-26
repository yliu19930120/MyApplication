package com.yliu.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.yliu.app.R
import com.yliu.myapplication.common.*
import com.yliu.myapplication.entity.Action
import com.yliu.myapplication.req.ActionReq
import req.ReqUtils
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ActionActivity() : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val traningDate = DateUtils.toLocalDate(intent.getLongExtra("traningDate",DateUtils.toLong(LocalDate.now())))
        setContentView(R.layout.activity_action)

        val traningDateTV = findViewById<EditText>(R.id.date_text)
        traningDateTV.setText(DateUtils.format(traningDate))

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

        findViewById<Button>(R.id.cancel_button).setOnClickListener{
            setResult(ResCode.succ,intent.putExtra("traningDate",DateUtils.toLong(traningDateTV.text.toString())))
            finish()
        }


    }

    fun submit(){
        val traningDateTV = findViewById<EditText>(R.id.date_text)
        val typel1TextView = findViewById<TextView>(R.id.typel1_text)
        val actionNameTV = findViewById<EditText>(R.id.action_name_text)
        val numsTV = findViewById<EditText>(R.id.nums_number)
        val speedNB = findViewById<EditText>(R.id.speed_number)
        val weightNB = findViewById<EditText>(R.id.weight_number)
        val groupNB = findViewById<EditText>(R.id.group_times_number)
        val timsNB = findViewById<EditText>(R.id.times_number)
        val unilateralSW = findViewById<Switch>(R.id.unilateral_switch)
        val sureButton = findViewById<Button>(R.id.sure_button)

        val action = Action(null,Global.loginUser!!.id!!,
            actionNameTV.text.toString(),
            typel1TextView.text.toString(),
            null,
            weightNB.text.toString().toDoubleOrNull(),
            numsTV.text.toString().toIntOrNull(),
            groupNB.text.toString().toIntOrNull(),
            if ("单".equals(unilateralSW.text.toString())) "1" else "0",
            LocalDate.parse(traningDateTV.text,DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            speedNB.text.toString().toDoubleOrNull(),
            timsNB.text.toString().toDoubleOrNull())

        ReqUtils.executeSync(action, ActionReq::addAction).subscribe {
            if (it.code == 0){
                val bundle = Bundle()
                bundle.putLong("traningDate",DateUtils.toLong(traningDateTV.text.toString()))
                setResult(ResCode.succ,intent.putExtras(bundle))
                finish()
            }else{
                Utils.alert(this,"${it.message}")
            }
        }
    }
}