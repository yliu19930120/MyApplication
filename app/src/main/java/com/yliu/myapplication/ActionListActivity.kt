package com.yliu.app

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.CalendarView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.yliu.myapplication.ActionActivity
import com.yliu.myapplication.ActionListAdapter
import com.yliu.myapplication.common.*
import com.yliu.myapplication.entity.Action
import com.yliu.myapplication.req.ActionReq
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import req.ReqUtils
import java.time.LocalDate
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
            calendarView.date = DateUtils.toLong(traningDate)
            buildActiosList(traningDate)

        }

        findViewById<Button>(R.id.add_button).setOnClickListener {
            val intent = Intent(this, ActionActivity::class.java)
            intent.putExtra("traningDate",calendarView.date)
            startActivityForResult(intent,ResCode.succ)
        }

        deleteOrUpdateListener()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ResCode.succ){
            val get = data?.extras?.getLong("traningDate")
            buildActiosList(DateUtils.toLocalDate(get!!))
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

    fun deleteOrUpdateListener(){
        val listView = findViewById<ListView>(R.id.actions)

        listView.onItemLongClickListener = object:AdapterView.OnItemLongClickListener{
            override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
                val action = parent!!.getItemAtPosition(position) as Action

                AlertDialog.Builder(this@ActionListActivity)
                        .setPositiveButton("编辑") { dialog, which -> updateAction(action.id!!) }
                        .setNegativeButton("删除") { dialog, which -> deleteAction(action.id!!) }
                        .create()
                        .show()
                return false
            }
        }
    }

    fun deleteAction(actionId:String){

        ReqUtils.retrofit.create(ActionReq::class.java)
                .deleteAction(Global.loginUser!!.id!!,actionId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.code == 0){
                        val calendarView = findViewById<CalendarView>(R.id.calendarView)
                        buildActiosList(DateUtils.toLocalDate(calendarView.date))
                    }else{
                        Utils.alert(this,"${it.message}")
                    }
                }

    }

    fun updateAction(actionId: String){
        Log.i(tag,"更新 ${actionId}")
    }
}