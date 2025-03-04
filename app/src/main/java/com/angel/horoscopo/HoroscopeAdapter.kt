package com.angel.horoscopo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class HoroscopeAdapter(val items: List<Horoscope>, val onClick: (Int) -> Unit) : Adapter<HoroscopeViewFolder>() {

    override fun onBindViewHolder(holder: HoroscopeViewFolder, position: Int) {
        val horoscope = items[position]
        holder.render(horoscope)
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoroscopeViewFolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horoscope, parent,false)
        return  HoroscopeViewFolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class  HoroscopeViewFolder (view: View): ViewHolder (view) {

    val iconImageView: ImageView = view.findViewById(R.id.iconImageView)
    val nameTextView: TextView = view.findViewById(R.id.nameTextView)
    val datesTextView: TextView = view.findViewById(R.id.datesTextView)


    fun render(horoscope: Horoscope){
    iconImageView.setImageResource(horoscope.icon)
    nameTextView.setText(horoscope.name)
    datesTextView.setText(horoscope.date)
    }
}