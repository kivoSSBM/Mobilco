package com.example.fitlane

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

class my_meals_adapter(var mCtx: Context, var resources:Int, var items:List<my_meals_model>):ArrayAdapter<my_meals_model>(mCtx, resources, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater:LayoutInflater = LayoutInflater.from(mCtx)
        val view:View = layoutInflater.inflate(resources, null)
        val meal_title:TextView = view.findViewById(R.id.meal_title)
        val meal_desc:TextView = view.findViewById(R.id.meal_description)


        val myItems:my_meals_model = items[position]
        meal_title.text = myItems.title
        meal_desc.text = myItems.description.toString()

        return view
    }
}