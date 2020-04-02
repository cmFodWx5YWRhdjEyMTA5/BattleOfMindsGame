package com.bonusgaming.battleofmindskotlin.ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Paint
import android.graphics.Color
import android.graphics.Canvas
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.LinearGradient
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.Size
import androidx.core.animation.doOnEnd

private const val SIZE_HEIGHT_PROGRESS_DP = 5
private const val SIZE_WIDTH_DESIRE_DP = 50
private const val SIZE_HEIGHT_DESIRE_DP = 35
private const val SIZE_MARGIN_DP = 10
private const val CORNER_ROUND_VALUE = 20f
private const val DURATION_INTERPOLATION_MILLISECONDS = 500L
private const val TEXT_STROKE_WIDTH = 3f
private const val TEXT_SIZE = 15
private const val MAX_PROGRESS = 100
private const val OFFSET_LINE_1 = 3


class LoadingAssetsBar : View {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

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
        strokeWidth = TEXT_STROKE_WIDTH
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

    fun setProgress(value: Int) {
        println("setprogress real")
        _progress = value
    }

    private var lengthWay: Float = -1f
    private val paintProgress = Paint().apply {
        style = Paint.Style.FILL
        shader = LinearGradient(
                0f,
                0f,
                dpToPx(SIZE_WIDTH_DESIRE_DP), dpToPx(SIZE_HEIGHT_DESIRE_DP),
                Color.parseColor(COLOR_LIGHT_TEXT),
                Color.parseColor(COLOR_DARK_TEXT),
                Shader.TileMode.MIRROR
        )
    }

    private val sizeHeightProgress = dpToPx(SIZE_HEIGHT_PROGRESS_DP)
    private val sizeWidthDesire = dpToPx(SIZE_WIDTH_DESIRE_DP)
    private val sizeHeightDesire = dpToPx(SIZE_HEIGHT_DESIRE_DP)
    private val sizeMargin = dpToPx(SIZE_MARGIN_DP)

    init {
        valueAnimator.doOnEnd {
            Log.e("init block", "doOnEnd")
            when (lastProgressInterpolation) {
                MAX_PROGRESS.toFloat() -> if (::downloadListener.isInitialized) downloadListener.onDownload()
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

        percentPx = lengthWay / MAX_PROGRESS
        textLine2X = measuredWidth / 2f
        textLine2Y = rectF.top - sizeMargin
        textLine1X = measuredWidth / 2f
        textLine1Y = rectF.top - OFFSET_LINE_1 * sizeMargin
        paintText.textSize = dpToPx(TEXT_SIZE)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(textStatusLine1, textLine1X, textLine1Y, paintText)
        canvas.drawText(textStatusLine2, textLine2X, textLine2Y, paintText)
        Log.e("ondraw", " sizes l ${rectF.left} t ${rectF.top} r ${rectF.right} b ${rectF.bottom}")
        canvas.drawRoundRect(rectF, CORNER_ROUND_VALUE, CORNER_ROUND_VALUE, paintProgress)
    }
}
