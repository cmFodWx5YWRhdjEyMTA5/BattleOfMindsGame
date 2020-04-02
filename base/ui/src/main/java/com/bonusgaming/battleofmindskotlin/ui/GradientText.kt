package com.bonusgaming.battleofmindskotlin.ui

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView

class GradientText : AppCompatTextView {


    constructor(context: Context) : super(context, null, -1)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, -1)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.e("123", "onMeasure: widthMeasureSpec $widthMeasureSpec")
        Log.e("123", "onMeasure: heightMeasureSpec $heightMeasureSpec")

    }

    override fun onLayout(
        changed: Boolean, left: Int, top: Int, right: Int,
        bottom: Int
    ) {
        super.onLayout(changed, left, top, right, bottom)
        Log.e("123", "onLayout: simple $height")
        if (changed) {
            Log.e("123", "onLayout: gh $height")
            Log.e("123", "onLayout: gw $width")
            paint.shader = LinearGradient(
                0f, 0f, width.toFloat(), 0f, Color.parseColor("#F27A54"),
                Color.parseColor("#A154F2"), Shader.TileMode.MIRROR
            )
        }
    }
}
