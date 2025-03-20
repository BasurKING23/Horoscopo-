package com.angel.horoscopo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class HoroscopeAdapter(var items: List<Horoscope>, val onClick: (Int) -> Unit) : Adapter<HoroscopeViewFolder>() {

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
    fun updateItems(items: List<Horoscope>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class  HoroscopeViewFolder (view: View): ViewHolder (view) {

    val iconImageView: ImageView = view.findViewById(R.id.iconImageView)
    val nameTextView: TextView = view.findViewById(R.id.nameTextView)
    val favorite_active: ImageView = view.findViewById(R.id.favorite_active)


    fun render(horoscope: Horoscope){
    iconImageView.setImageResource(horoscope.icon)
    nameTextView.setText(horoscope.name)

        if (SessionManager(itemView.context).isFavorite(horoscope.id)) {
            favorite_active.visibility = View.VISIBLE
        }else{
            favorite_active.visibility = View.GONE
        }
    }
}