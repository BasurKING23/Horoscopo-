package com.angel.horoscopo

import android.content.Context

class SessionManager(context: Context) {

    private val sharedPref = context.getSharedPreferences("zodiac_session", Context.MODE_PRIVATE)

    fun setFavorite(id: String) {
        val editor = sharedPref.edit()
        editor.putString("favorite", id)
        editor.apply()
    }

    fun getFavorite(): String {
        return sharedPref.getString("favorite", "")!!
    }

    fun isFavorite(id: String): Boolean {
        return id == getFavorite()
    }
}