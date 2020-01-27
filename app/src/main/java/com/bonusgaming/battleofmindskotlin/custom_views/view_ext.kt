package com.bonusgaming.battleofmindskotlin.custom_views

import android.view.View

public fun View.dpToPx(size: Int) = size * resources.displayMetrics.density
public fun FloatArray.changeAll(value: Float) {
    for (i in 0 until size) {
        this[i] = value
    }
}

