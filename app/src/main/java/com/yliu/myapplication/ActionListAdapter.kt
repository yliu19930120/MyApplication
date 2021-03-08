package com.yliu.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.yliu.app.R
import com.yliu.myapplication.common.Cost
import com.yliu.myapplication.entity.Action
import com.yliu.myapplication.entity.ActionTp

class ActionListAdapter(context: Context, resource: Int, objects: List<Action>) :ArrayAdapter<Action>(context, resource, objects) {

    var resourceID: Int = resource

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val action = getItem(position)

        val view = LayoutInflater.from(context).inflate(resourceID, null)
        val actionName = view.findViewById<TextView>(R.id.actionName)
        val weight = view.findViewById<TextView>(R.id.weight)
        val nums = view.findViewById<TextView>(R.id.nums)
        val typel1 = action!!.typeL1
        val times = view.findViewById<TextView>(R.id.times)
        val speed = view.findViewById<TextView>(R.id.speed)

        actionName.setText(action!!.actionName)
        weight.setText("${action!!.weight} kg")
        nums.setText("${action!!.nums} 次")
        times.setText("${action!!.times} 分钟")
        speed.setText("${action!!.speed} km/h")

        if ( typel1 in Cost.AEROBIC_TYPES){
            weight.visibility = View.GONE
            nums.visibility = View.GONE
        }else{
            times.visibility = View.GONE
            speed.visibility = View.GONE
        }

        return view
    }

}