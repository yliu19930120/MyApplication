package com.yliu.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.yliu.app.R
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
        actionName.setText(action!!.actionName)
        weight.setText("${action!!.weight} kg")
        nums.setText("${action!!.nums} 次")
        return view
    }

}