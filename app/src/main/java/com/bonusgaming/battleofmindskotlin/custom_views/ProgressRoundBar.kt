package com.bonusgaming.battleofmindskotlin.custom_views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View


class ProgressRoundBar : View {

    companion object {
        const val STOP_STATE = 1
        const val LOADING_STATE = 0
    }

    private var stopDraw: Boolean = false
    public lateinit var timeOffListener: TimeOffListener

    interface TimeOffListener {
        fun timeOf()
    }

    var currentState = LOADING_STATE

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    private var deltaT: Long = 0
    private var lastT: Long = 0
    private var timeStart: Long = 0
    private var deltaSumm: Long = 0
    var rX: Float = 0.0f
    var rY: Float = 0.0f

    var maxHeight: Int = 0
    var maxWidth: Int = 0

    val paint: Paint = Paint()

    val rectF: RectF

    lateinit var linearGradient: LinearGradient

    init {
        Log.e("123123123213", "blabla $height")
        paint.color = Color.CYAN
        rectF = RectF()
        rectF.left = 0F
        rectF.top = 0F
        rectF.right = 0F
        rectF.bottom = 0F

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        maxHeight = MeasureSpec.getSize(heightMeasureSpec)
        maxWidth = MeasureSpec.getSize(widthMeasureSpec)
        Log.e("123123123213", "onMeasure $maxHeight")
        Log.e("123123123213", "onMeasure $maxWidth")
        rX = maxWidth.toFloat()
        rY = maxHeight.toFloat()
        //992 пикслея (S), 15000(T)
        lastT = System.currentTimeMillis()
        timeStart = lastT

        rectF.right = maxWidth.toFloat()
        rectF.bottom = maxHeight.toFloat()


    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        linearGradient = LinearGradient(
            0f,
            0f,
            w.toFloat(), h.toFloat(),
            Color.parseColor("#F27A54"),
            Color.parseColor("#A154F2"),
            Shader.TileMode.MIRROR
        )
        paint.shader = linearGradient;
    }

    fun stopAnimate() {
        Log.e("1144", "STOP ANIMATE")
        currentState = STOP_STATE
    }

    private fun calculateTime() {
        deltaT = (System.currentTimeMillis() - lastT)
        lastT = System.currentTimeMillis()
    }

    private fun calculateSides() {
        rectF.right -= maxWidth / 30000.0F * deltaT
        rectF.left += maxWidth / 30000.0F * deltaT
    }


    override fun onDraw(canvas: Canvas) {
        if (stopDraw) return
        calculateTime()
        calculateSides()

        canvas.drawRoundRect(rectF, 30f, 30f, paint)

        if (rectF.right < rectF.left && ::timeOffListener.isInitialized) {
            Log.e("Called once timeOff", "BU")
            timeOffListener.timeOf()
            stopDraw = true

        } else if (currentState != STOP_STATE) invalidate()

    }


}


