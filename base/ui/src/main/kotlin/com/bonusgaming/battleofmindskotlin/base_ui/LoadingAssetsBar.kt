package com.bonusgaming.battleofmindskotlin.base_ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.Size
import androidx.core.animation.doOnEnd

class LoadingAssetsBar : View {
    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }



    @Size(max = 23)
    var textStatusLine2: String = ""
        set(value) {
            field = value
            invalidate()
        }

    @Size(max = 23)
    var textStatusLine1: String = ""
        set(value) {
            field = value
            invalidate()
        }

    private val paintText = Paint().apply {
        color = Color.GRAY
        strokeWidth = 3f
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
    }

    private var textLine2X = 0f
    private var textLine2Y = 0f

    private var textLine1X = 0f
    private var textLine1Y = 0f

    private var percentPx = 0f

    private val rectF = RectF()

    lateinit var downloadListener: DownloadListener

    interface DownloadListener {
        fun onDownload()
    }

    private val valueAnimator = ValueAnimator.ofFloat().apply {
        duration = DURATION_INTERPOLATION_MILLISECONDS
        interpolator = LinearInterpolator()
        addUpdateListener {
            lastProgressInterpolation = it.animatedValue as Float
            rectF.right = percentPx * lastProgressInterpolation + rectF.left
            invalidate()
        }
    }

    private var lastProgressInterpolation = 0f

    /**
     * private - чтобы нельзя было
     * опираться на состояние progress,
     * так как нельзя строить логику относительно
     * состояния данного параметра, он отвечает только
     * за отображение прогресса
     */
    private var _progress = 0
        set(newValue) {
            field = newValue
            if (valueAnimator.isRunning) valueAnimator.cancel()
            else {
                valueAnimator.setFloatValues(lastProgressInterpolation, newValue.toFloat())
                valueAnimator.start()
            }
        }

    public fun setProgress(value: Int) {
        println("setprogress real")
        _progress = value
    }

    private var lengthWay: Float = -1f
    private val paintProgress = Paint().apply {
        style = Paint.Style.FILL
        shader = LinearGradient(
                0f,
                0f,
                dpToPx(100), dpToPx(100),
                Color.parseColor(COLOR_LIGHT_TEXT),
                Color.parseColor(COLOR_DARK_TEXT),
                Shader.TileMode.MIRROR
        )
    }

    private val sizeHeightProgress = dpToPx(5)
    private val sizeWidthDesire = dpToPx(50)
    private val sizeHeightDesire = dpToPx(35)
    private val sizeMargin = dpToPx(10)

    companion object {
        private const val CORNER_ROUND_VALUE = 20f
        private const val DURATION_INTERPOLATION_MILLISECONDS = 500L

    }

    init {
        valueAnimator.doOnEnd {
            Log.e("init block", "doOnEnd")
            when (lastProgressInterpolation) {
                100f -> if (::downloadListener.isInitialized) downloadListener.onDownload()
                else -> {
                    valueAnimator.setFloatValues(lastProgressInterpolation, _progress.toFloat())
                    if (lastProgressInterpolation != _progress.toFloat())
                        valueAnimator.start()
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val resultWidth = resolveSize(sizeWidthDesire.toInt(), widthMeasureSpec)
        val resultHeight = resolveSize(sizeHeightDesire.toInt(), heightMeasureSpec)
        setMeasuredDimension(resultWidth, resultHeight)

        if (lastProgressInterpolation == 0f) {
            calculateSizes()
        }
    }

    private fun calculateSizes() {
        rectF.top = measuredHeight - sizeMargin - sizeHeightProgress
        rectF.bottom = measuredHeight - sizeMargin
        rectF.left = sizeMargin
        rectF.right = sizeMargin

        lengthWay = measuredWidth - rectF.left - rectF.right

        percentPx = lengthWay / 100F
        textLine2X = measuredWidth / 2f
        textLine2Y = rectF.top - sizeMargin
        textLine1X = measuredWidth / 2f
        textLine1Y = rectF.top - 3 * sizeMargin
        paintText.textSize = dpToPx(15)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(textStatusLine1, textLine1X, textLine1Y, paintText)
        canvas.drawText(textStatusLine2, textLine2X, textLine2Y, paintText)
        Log.e("ondraw", " sizes l ${rectF.left} t ${rectF.top} r ${rectF.right} b ${rectF.bottom}")
        canvas.drawRoundRect(rectF, CORNER_ROUND_VALUE, CORNER_ROUND_VALUE, paintProgress)
    }
}
