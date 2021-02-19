package com.yliu.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.ListView
import com.yliu.myapplication.entity.ActionTp
import com.yliu.myapplication.ActionTpAdapter
import java.time.LocalDate

class ActionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action)

        val actionTps = listOf(
            ActionTp(LocalDate.of(2021, 2, 1), "背部"),
            ActionTp(LocalDate.of(2021, 2, 2), "胸部"),
            ActionTp(LocalDate.of(2021, 2, 3), "体能+有氧"),
            ActionTp(LocalDate.of(2021, 2, 4), "腿部"),
            ActionTp(LocalDate.of(2021, 2, 5), "胸部")
        )

        val actionTpAdapter = ActionTpAdapter(
            this,
            R.layout.action_tps,
            actionTps
        )
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val listView = findViewById<ListView>(R.id.action_tps)
        listView.adapter = actionTpAdapter
    }
}