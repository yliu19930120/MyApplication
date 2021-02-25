package com.yliu.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CalendarView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.yliu.myapplication.ActionActivity
import com.yliu.myapplication.ActionListAdapter
import com.yliu.myapplication.common.Global
import com.yliu.myapplication.common.Utils
import com.yliu.myapplication.req.ActionReq
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import req.ReqUtils
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

class ActionListActivity : AppCompatActivity() {

    val tag = ActionListActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_action)
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val traningDate = LocalDate.now()
        calendarView.date = traningDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        buildActiosList(traningDate)


        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->

            val traningDate = LocalDate.of(year, month+1, dayOfMonth)

            buildActiosList(traningDate)

        }

        findViewById<Button>(R.id.add_button).setOnClickListener {
            val intent = Intent(this, ActionActivity::class.java)
            intent.putExtra("traningDate",
                LocalDateTime.ofInstant(Instant.ofEpochMilli(calendarView.date), ZoneId.systemDefault()).toLocalDate())
            startActivity(intent)
        }
    }

    fun buildActiosList(traningDate :LocalDate){

        val userId = Global.loginUser!!.id!!

        ReqUtils.retrofit.create(ActionReq::class.java).getActions(userId,traningDate)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
            if (it.code == 0){
                val actionAdapter = ActionListAdapter(this, R.layout.action_list, it.data)
                val listView = findViewById<ListView>(R.id.actions)
                Log.e(tag,"查询到记录数 ${it.data.size}")
                listView.adapter = actionAdapter
            }else{
                Utils.alert(this,"${it.message}")
            }
        }

    }

}