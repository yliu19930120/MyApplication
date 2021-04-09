package com.yliu.app

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.yliu.myapplication.ActionActivity
import com.yliu.myapplication.ActionDayViewDecorator
import com.yliu.myapplication.ActionListAdapter
import com.yliu.myapplication.BaseActivity
import com.yliu.myapplication.common.DateUtils
import com.yliu.myapplication.common.Global
import com.yliu.myapplication.common.GsonConfig
import com.yliu.myapplication.common.ResCode
import com.yliu.myapplication.entity.Action
import com.yliu.myapplication.req.ActionReq
import com.yliu.myapplication.req.BaseObserver
import req.ReqUtils
import req.Result
import java.time.LocalDate
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashSet
import kotlin.collections.LinkedHashSet


class ActionListActivity : BaseActivity() {

    val TAG = ActionListActivity::class.java.name
    var dates:LinkedHashSet<LocalDate> = LinkedHashSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_action)

        val calendarView = findViewById<MaterialCalendarView>(R.id.calendarView)

        calendarView.state().edit()
            .setFirstDayOfWeek(Calendar.MONDAY)
            .setMaximumDate(CalendarDay.today())
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit();

        calendarView.selectedDate = CalendarDay.today()


        ReqUtils.executeSync(Global.loginUser!!.id!!,ActionReq::actionDates).subscribe(
            BaseObserver {
                dates = LinkedHashSet(it.data)
                calendarView.addDecorator(ActionDayViewDecorator(Color.BLUE, dates))
            }
        )


        val traningDate = DateUtils.toLocalDate(calendarView.selectedDate.getDate().time)

        buildActiosList(traningDate)

        calendarView.setOnDateChangedListener{ widget, date, selected ->

            val traningDate = DateUtils.toLocalDate(date.getDate().time)
            buildActiosList(traningDate)
        }

        findViewById<Button>(R.id.add_button).setOnClickListener {
            val intent = Intent(this, ActionActivity::class.java)
            intent.putExtra("action",GsonConfig.toJson(Action(DateUtils.toLocalDate(calendarView.selectedDate.date.time)!!)))
            intent.putExtra("op","add")
            startActivityForResult(intent,ResCode.succ)
        }

        deleteOrUpdateListener()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ResCode.succ){
            val get = data?.extras?.getLong("traningDate")
            buildActiosList(DateUtils.toLocalDate(get))
        }
    }

    fun buildActiosList(traningDate :LocalDate?){
        val userId = Global.loginUser!!.id!!
        val calendarView = findViewById<MaterialCalendarView>(R.id.calendarView)

        ReqUtils.getReq(ActionReq::class.java).getActions(userId,traningDate?: DateUtils.toLocalDate(calendarView.selectedDate.date.time)!!)
            .compose(ReqUtils.schedules())
            .subscribe(BaseObserver{
                val actionAdapter = ActionListAdapter(this, R.layout.action_list, it.data)
                val listView = findViewById<ListView>(R.id.actions)
                listView.adapter = actionAdapter
            })

    }

    fun deleteOrUpdateListener(){
        val listView = findViewById<ListView>(R.id.actions)

        listView.onItemLongClickListener = object:AdapterView.OnItemLongClickListener{
            override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
                val action = parent!!.getItemAtPosition(position) as Action

                AlertDialog.Builder(this@ActionListActivity)
                        .setNeutralButton("删除"){ dialog, _ -> deleteAction(action.id!!)}
                        .setNegativeButton("复制") { dialog, _ -> copy(action!!) }
                        .create()
                        .show()
                return true
            }
        }

        listView.onItemClickListener = object:AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val action = parent!!.getItemAtPosition(position) as Action
                updateAction(action!!)
            }

        }
    }

    fun deleteAction(actionId:String){

        ReqUtils.getReq(ActionReq::class.java)
                .deleteAction(Global.loginUser!!.id!!,actionId)
                .compose(ReqUtils.schedules())
                .subscribe (BaseObserver{this::refreshList})

    }

    fun updateAction(action: Action){
        Log.i(TAG,"更新 ${action.actionName}")
        val intent = Intent(this, ActionActivity::class.java)
        intent.putExtra("op","update")
        intent.putExtra("action",GsonConfig.gson.toJson(action))
        startActivityForResult(intent,ResCode.succ)

    }

    fun copy(action: Action){
        Log.i(TAG,"复制动作 ${action.actionName}")
        val newAction = Action(action)
        ReqUtils.executeSync(newAction,ActionReq::addAction).subscribe (this::refreshList)
        buildActiosList(action.traningDate)
    }

    fun refreshList(it: Result<String>) {
        val calendarView = findViewById<MaterialCalendarView>(R.id.calendarView)
        buildActiosList(DateUtils.toLocalDate(calendarView.selectedDate.getDate().time))
    }

    override fun onStop() {
        Global.commitUser(this)
        super.onStop()
    }
}