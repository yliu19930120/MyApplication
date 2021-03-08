package com.yliu.myapplication

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import com.yliu.myapplication.common.DateUtils
import java.time.LocalDate

class ActionDayViewDecorator(val color:Int,val dates: Set<LocalDate>):DayViewDecorator {


    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return if (day==null) false else dates.contains(DateUtils.toLocalDate(day!!.date.time))
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(5f,color))
    }
}