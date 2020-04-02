package com.bonusgaming.battleofmindskotlin.ui

import android.view.View


const val COLOR_LIGHT_TEXT = "#F27A54"
const val COLOR_DARK_TEXT = "#A154F2"

fun View.dpToPx(size: Int) = size * resources.displayMetrics.density
fun FloatArray.changeAllTo(value: Float) {
    for (i in 0 until size) {
        this[i] = value
    }
}

