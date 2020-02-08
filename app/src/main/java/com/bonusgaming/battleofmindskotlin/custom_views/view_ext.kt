package com.bonusgaming.battleofmindskotlin.custom_views

import android.view.View

fun View.dpToPx(size: Int) = size * resources.displayMetrics.density
fun FloatArray.changeAllTo(value: Float) {
    for (i in 0 until size) {
        this[i] = value
    }
}

