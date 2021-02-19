package com.yliu.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.yliu.app.R
import com.yliu.myapplication.entity.ActionTp

class ActionTpAdapter(context: Context, resource: Int, objects: List<ActionTp>) :
    ArrayAdapter<ActionTp>(context, resource, objects) {

    var resourceID: Int = resource


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val actionTp = getItem(position)

        val view = LayoutInflater.from(context).inflate(resourceID, null)
        val date = view.findViewById<TextView>(R.id.date)
        val topic = view.findViewById<TextView>(R.id.topic)
            date.setText(actionTp!!.date.toString())
        topic.setText(actionTp.topic)
        return view
    }
}