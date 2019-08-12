package com.bonusgaming.battleofmindskotlin

import android.content.Context

private const val NAME_PREFS = "app.settings"

class Prefs(context: Context) {

    private val prefs = context.getSharedPreferences(NAME_PREFS, Context.MODE_PRIVATE)

    fun putBoolean(name: String, value: Boolean) {
        prefs.edit().putBoolean(name, value).apply()
    }

    fun putInt(name: String, value: Int) {
        prefs.edit().putInt(name, value).apply()
    }

    fun getBoolean(name: String): Boolean {
        return prefs.getBoolean(name, false)
    }

    fun getInt(name: String): Int {
        return prefs.getInt(name, -1)
    }
}
