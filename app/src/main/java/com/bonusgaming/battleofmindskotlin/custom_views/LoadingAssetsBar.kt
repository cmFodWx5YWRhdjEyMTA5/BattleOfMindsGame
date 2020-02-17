package com.bonusgaming.battleofmindskotlin.custom_views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnEnd

class LoadingAssetsBar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defAttrStyle: Int = 0,
    defResStyle: Int = 0
) : View(context, attributeSet, defAttrStyle, defResStyle) {

    var textStatus: String = "загрузка"
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


    private var textX = 0f
    private var textY = 0f

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
    var progress = 0
        set(newValue) {
            field = newValue
            if (valueAnimator.isRunning) valueAnimator.cancel()
            else {
                valueAnimator.setFloatValues(0f, newValue.toFloat())
                valueAnimator.start()
            }
        }


    private var lengthWay: Float = -1f
    private val paintProgress = Paint().apply {
        style = Paint.Style.FILL
//        color = Color.YELLOW
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
        // valueAnimator.removeAllListeners()
        valueAnimator.doOnEnd {
            Log.e("init block", "doOnEnd")
            when (lastProgressInterpolation) {
                100f -> if (::downloadListener.isInitialized) downloadListener.onDownload()
                else -> {
                    valueAnimator.setFloatValues(lastProgressInterpolation, progress.toFloat())
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
        textX = measuredWidth / 2f
        textY = rectF.top - sizeMargin
        paintText.textSize = dpToPx(15)

    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(textStatus, textX, textY, paintText)
        Log.e("ondraw", " sizes l ${rectF.left} t ${rectF.top} r ${rectF.right} b ${rectF.bottom}")
        canvas.drawRoundRect(rectF, CORNER_ROUND_VALUE, CORNER_ROUND_VALUE, paintProgress)
    }
}
