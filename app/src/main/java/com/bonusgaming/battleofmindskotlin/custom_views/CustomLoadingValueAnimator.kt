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
        const val SIZE_DESIRABLE = 100
        const val ONE_STEP_DURATION_MILLISECONDS = 1000L
        const val ROUND_CORNER_VALUE = 30F
        const val ROTATE_VALUE = 90F
    }

    private var lengthWay: Float = -1F
    private var diameterElem: Float = -1F
    private var mainAnimatorSet = AnimatorSet()

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

    //  private var pathRect = Path().apply { addRect(rect1, Path.Direction.CW) }


    //   var roundedCorners = floatArrayOf(20f, 20f, 20f, 20f, 20F, 20F, 20F, 20F)


    //   private var roundRectShape: RoundRectShape = RoundRectShape(roundedCorners, rect1, roundedCorners)

    init {
        // matrixRotate.set(100F, rect1.centerX(), rect1.centerY())
        //matrixRotate.setTranslate(0.1F, 0.1F)
        var elem = Elem(RectF(100F, 100F, 200F, 200F),)

    }


    private fun calculateSizes() {
        diameterElem = measuredHeight / 5.0F
        lengthWay = measuredHeight - diameterElem
    }

    private fun setAnimatorsFor(elem: Elem) {
        val roundCornerAnimator: ValueAnimator = ValueAnimator.ofFloat(0F, ROUND_CORNER_VALUE)
        val valueAnimator: ValueAnimator = ValueAnimator.ofFloat(0F, lengthWay)
        val degreeAnimator: ValueAnimator = ValueAnimator.ofFloat(0F, ROTATE_VALUE)

        valueAnimator.interpolator = FastOutSlowInInterpolator()
        degreeAnimator.interpolator = FastOutSlowInInterpolator()
        roundCornerAnimator.interpolator = FastOutSlowInInterpolator()


        //сглаживаем углы
        roundCornerAnimator.setDuration(ONE_STEP_DURATION_MILLISECONDS).addUpdateListener {
            elem.corners.changeAllTo(it.animatedValue as Float)
        }

        valueAnimator.setDuration(ONE_STEP_DURATION_MILLISECONDS).addUpdateListener {
            elem.apply {
                rectF.left = elem.offsetLeft + it.animatedValue as Float
                rectF.right = elem.offsetRight + it.animatedValue as Float

                path.reset()
                path.addRoundRect(elem.rectF, elem.corners, Path.Direction.CW)
            }
        }

        degreeAnimator.setDuration(ONE_STEP_DURATION_MILLISECONDS).addUpdateListener {
            matrixRotate.setRotate(it.animatedValue as Float, rect1.centerX(), rect1.centerY())
            elem.path.transform(matrixRotate)
        }

        mainAnimatorSet.playTogether(valueAnimator, degreeAnimator, roundCornerAnimator)

        // mainAnimatorSet.start()
    }

    private fun getExceptSize(measureSpec: Int): Int {

        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        return when (mode) {
            MeasureSpec.AT_MOST -> {
                minOf(size, SIZE_DESIRABLE)
            }
            MeasureSpec.EXACTLY -> {
                size
            }
            else -> SIZE_DESIRABLE
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val mainSize = minOf(getExceptSize(widthMeasureSpec), getExceptSize(heightMeasureSpec))

        setMeasuredDimension(mainSize, mainSize)
        calculateSizes()
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
        //canvas.drawPath(pathRect, paintFPS)


        //invalidate()

    }


    data class Elem(
        val rectF: RectF,
        var offsetLeft: Int = 0,
        var offsetRight: Int = 0,
        var offsetTop: Int = 0,
        var offsetBottom: Int = 0,
        val corners: FloatArray = floatArrayOf(0f, 0f, 0f, 0f, 0F, 0F, 0F, 0F)
    ) {
        val path: Path = Path().apply { addRect(rectF, Path.Direction.CW) }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Elem

            if (rectF != other.rectF) return false
            if (offsetLeft != other.offsetLeft) return false
            if (offsetRight != other.offsetRight) return false
            if (offsetTop != other.offsetTop) return false
            if (offsetBottom != other.offsetBottom) return false
            if (!corners.contentEquals(other.corners)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = rectF.hashCode()
            result = 31 * result + offsetLeft
            result = 31 * result + offsetRight
            result = 31 * result + offsetTop
            result = 31 * result + offsetBottom
            result = 31 * result + corners.contentHashCode()
            return result
        }
    }

}


