package com.bonusgaming.battleofmindskotlin.custom_views

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.interpolator.view.animation.FastOutSlowInInterpolator


class CustomLoadingValueAnimator @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defAttrStyle: Int = 0,
    defResStyle: Int = 0
) : View(context, attributeSet, defAttrStyle, defResStyle) {


    companion object {
        const val sizeDesirable = 100
    }

    private val paintFPS: Paint = Paint().apply {
        color = Color.RED
        strokeWidth = 1F
        textSize = 100F
        style = Paint.Style.FILL
    }
    private var _lastTime: Long = 0
    private var frames = 0
    private var fps = 0
    private var isStopping = false
    private var onStopCallback: CustomLoadingValueAnimator.LoadingOnStop? = null

    private var rect1 = RectF(100F, 100F, 200F, 200F)
    private var matrixRotate = Matrix()
    private var matrixTranslate = Matrix()


    private var pathRect = Path().apply { addRect(rect1, Path.Direction.CW) }
    private var valueAnimator: ValueAnimator = ValueAnimator.ofFloat(0F, 200F)
    private var degreeAnimator: ValueAnimator = ValueAnimator.ofFloat(0F, 90F)

    var roundedCorners = floatArrayOf(20f, 20f, 20f, 20f, 20F, 20F, 20F, 20F)

    private var roundCornerAnimator: ValueAnimator = ValueAnimator.ofFloat(0F, 30F)


    //   private var roundRectShape: RoundRectShape = RoundRectShape(roundedCorners, rect1, roundedCorners)

    init {
        // matrixRotate.set(100F, rect1.centerX(), rect1.centerY())
        //matrixRotate.setTranslate(0.1F, 0.1F)

        var animatorSet = AnimatorSet()

        roundCornerAnimator.setDuration(1000).addUpdateListener {
            roundedCorners.changeAll(it.animatedValue as Float)
        }

        valueAnimator.setDuration(1000).addUpdateListener {
            rect1.left = 100 + it.animatedValue as Float
            rect1.right = 200 + it.animatedValue as Float

            pathRect.reset()
            pathRect.addRoundRect(rect1, roundedCorners, Path.Direction.CW)
        }

        degreeAnimator.setDuration(1000).addUpdateListener {
            matrixRotate.setRotate(it.animatedValue as Float, rect1.centerX(), rect1.centerY())
            pathRect.transform(matrixRotate)
        }

        //valueAnimator.add
        valueAnimator.interpolator = FastOutSlowInInterpolator()
        degreeAnimator.interpolator = FastOutSlowInInterpolator()

        animatorSet.playTogether(valueAnimator, degreeAnimator, roundCornerAnimator)
        animatorSet.start()
    }


    fun cal

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthExpected = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)

        val heightExpected = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        Log.e("DBE", "widthSize $widthExpected")
        Log.e("DBE", "heightSize $heightExpected")


        var resultSize = 0

        //resolveSize()

        when (heightMode) {
            MeasureSpec.UNSPECIFIED -> {
                Log.e("DBE", "heightMode UNSPECIFIED")
            }
            MeasureSpec.AT_MOST -> {
                Log.e("DBE", "heightMode AT_MOST")

            }
            MeasureSpec.EXACTLY -> {
                Log.e("DBE", "heightMode EXACTLY")
                resultSize = minOf(widthExpected, heightExpected)
            }
        }

        when (widthMode) {
            MeasureSpec.UNSPECIFIED -> {
                Log.e("DBE", "widthMode UNSPECIFIED")
            }
            MeasureSpec.AT_MOST -> {
                Log.e("DBE", "widthMode AT_MOST")

            }
            MeasureSpec.EXACTLY -> {
                Log.e("DBE", "widthMode EXACTLY")
                resultSize = minOf(widthExpected, heightExpected)
            }
        }

        setMeasuredDimension(resultSize,resultSize)

        Log.e("DBE", "measuredHeight m $measuredHeight")
        Log.e("DBE", "measuredWidth m $measuredWidth")

    }

    interface LoadingOnStop {
        fun onStop()
    }

    fun startStopping(callback: LoadingOnStop) {
        onStopCallback = callback
        isStopping = true
    }

    //use for debug
    private fun computeFPS(canvas: Canvas) {
        frames++


        //Log.d("PISHU","work")
        if ((System.currentTimeMillis() - _lastTime) > 1000) {
            Log.d("PISHU", "work")
            fps = frames
            frames = 0
            _lastTime = System.currentTimeMillis()
        }
        canvas.drawText("FPS: $fps", 50.0f, 50.0f, paintFPS)
    }

    override fun onDraw(canvas: Canvas) {

        // matrixRotate.setRotate(2F, 50F, 50F)
        // matrixRotate.(rect1)
        // pathRect.transform(matrixRotate)
        //pathRect.transform(matrixRotate)
        //   pathRect.transform(matrixRotate)
        canvas.drawPath(pathRect, paintFPS)

        Log.e("DBE", "measuredHeight m $measuredHeight")
        Log.e("DBE", "measuredWidth m $measuredWidth")


        invalidate()

    }


}


