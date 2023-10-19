package com.example.studentgoals

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.studentgoals.Goal

class GoalAdapter(context: Context, goals: List<Goal>) :
    ArrayAdapter<Goal>(context, 0, goals) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        }

        val goal = getItem(position)
        val goalTitle = itemView?.findViewById<TextView>(android.R.id.text1)
        goalTitle?.text = goal?.title

        return itemView!!
    }
}