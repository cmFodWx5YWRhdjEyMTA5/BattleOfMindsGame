package com.bonusgaming.battleofmindskotlin.core.main

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

private const val NAME_PREFS = "app.settings"


class Prefs @Inject constructor(private val context: Context) {

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
