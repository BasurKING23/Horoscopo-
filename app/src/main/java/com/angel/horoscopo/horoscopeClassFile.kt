package com.angel.horoscopo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class HoroscopeAdapter(val items: List<Horoscope>) : Adapter<horoscopeViewFolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): horoscopeViewFolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horoscope, parent,false)
        return  horoscopeViewFolder(view)
    }

    override fun getItemCount(): Int {
    return items.size
    }

    override fun onBindViewHolder(holder: horoscopeViewFolder, position: Int) {
        val horoscope = items[position]
    }
}

class  horoscopeViewFolder (View: View): ViewHolder (View) {

    val iconImageView: ImageView = view.findViewByID(R.id.IconImageView)
    val nameTextView: ImageView = view.findViewByID(R.id.nameTextView)
    val dateTextView: ImageView = view.findViewByID(R.id.dateTextView)


    fun render(horoscope: Horoscope){
    iconImageView.setImageIcon(horoscope.icon)
    nameTextView.setText(horoscope.name)
    dateTextView.setText(horoscope.date)
    }
}