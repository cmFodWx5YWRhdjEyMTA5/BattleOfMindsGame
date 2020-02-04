package com.bonusgaming.battleofmindskotlin.custom_views

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.*
import androidx.core.animation.doOnEnd
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import kotlin.math.sqrt


class CustomLoadingValueAnimator @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defAttrStyle: Int = 0,
    defResStyle: Int = 0
) : View(context, attributeSet, defAttrStyle, defResStyle) {


    companion object {
        const val SIZE_DESIRABLE = 100
        const val ONE_STEP_DURATION_MILLISECONDS = 1000L
        const val ROUND_CORNER_VALUE = 90F
        const val ROUND_CORNER_DEFAULT_VALUE = 10F
        const val ROTATE_VALUE = 180F
        const val SCALE_MIN_VALUE = 0.5F
        const val SCALE_MAX_VALUE = 1.2F
    }

    private lateinit var elemLT: Elem
    private lateinit var elemLB: Elem
    private lateinit var elemRT: Elem
    private lateinit var elemRB: Elem

    private var lengthWay: Float = -1F
    private var diameterElem: Float = -1F
    private var mainAnimatorSet = AnimatorSet()

    private val paintFPS: Paint = Paint().apply {
        color = Color.RED
        strokeWidth = 1F
        textSize = 100F
        style = Paint.Style.FILL
    }

    private val paintLT: Paint = Paint().apply {
        color = Color.YELLOW
        strokeWidth = 1F
        style = Paint.Style.FILL
    }

    private val paintRT: Paint = Paint().apply {
        color = Color.GREEN
        strokeWidth = 1F
        style = Paint.Style.FILL
    }

    private val paintRB: Paint = Paint().apply {
        color = Color.WHITE
        strokeWidth = 1F
        style = Paint.Style.FILL_AND_STROKE
    }

    private val paintLB: Paint = Paint().apply {
        color = Color.RED
        strokeWidth = 1F
        style = Paint.Style.FILL
    }

    private var _lastTime: Long = 0
    private var frames = 0
    private var fps = 0
    private var isStopping = false
    private var onStopCallback: CustomLoadingValueAnimator.LoadingOnStop? = null


    //  private var pathRect = Path().apply { addRect(rect1, Path.Direction.CW) }


    //   var roundedCorners = floatArrayOf(20f, 20f, 20f, 20f, 20F, 20F, 20F, 20F)


    //   private var roundRectShape: RoundRectShape = RoundRectShape(roundedCorners, rect1, roundedCorners)

    private fun calculateSizes() {
        diameterElem = measuredHeight / 8.0f
        lengthWay = measuredHeight - diameterElem * 6


        elemLT = Elem(Position.LT)
        elemLB = Elem(Position.LB)
        elemRT = Elem(Position.RT)
        elemRB = Elem(Position.RB)

        elemLT.setRectF(RectF(0f, 0f, diameterElem, diameterElem))
        elemRT.setRectF(RectF(lengthWay, 0f, lengthWay + diameterElem, diameterElem))
        elemRB.setRectF(
            RectF(
                lengthWay,
                lengthWay,
                lengthWay + diameterElem,
                lengthWay + diameterElem
            )
        )
        elemLB.setRectF(RectF(0f, lengthWay, diameterElem, lengthWay + diameterElem))

    }

    private fun configureMainAnimatorSet() {
        val roundCornerAnimatorGroupOne: ValueAnimator = ValueAnimator.ofFloat(
            ROUND_CORNER_DEFAULT_VALUE,
            ROUND_CORNER_VALUE,
            ROUND_CORNER_DEFAULT_VALUE
        )

        val roundCornerAnimatorGroupTwo: ValueAnimator = ValueAnimator.ofFloat(
            ROUND_CORNER_VALUE,
            ROUND_CORNER_DEFAULT_VALUE,
            ROUND_CORNER_VALUE
        )

        val valueAnimator: ValueAnimator = ValueAnimator.ofFloat(0F, lengthWay)
        val degreeAnimator: ValueAnimator = ValueAnimator.ofFloat(0F, ROTATE_VALUE)
        val scaleDownAnimator: ValueAnimator = ValueAnimator.ofFloat(1F, SCALE_MIN_VALUE, 1F)
        val scaleUpAnimator: ValueAnimator =
            ValueAnimator.ofFloat(1F, SCALE_MAX_VALUE, 1F)

        valueAnimator.interpolator =  AccelerateDecelerateInterpolator()
        degreeAnimator.interpolator =  AccelerateDecelerateInterpolator()
        roundCornerAnimatorGroupOne.interpolator = DecelerateInterpolator()
        roundCornerAnimatorGroupTwo.interpolator = AccelerateDecelerateInterpolator()
        scaleUpAnimator.interpolator = DecelerateInterpolator()

        scaleDownAnimator.setDuration(ONE_STEP_DURATION_MILLISECONDS).addUpdateListener {
            elemLT.scale(it.animatedValue as Float)
            elemRB.scale(sqrt(it.animatedValue as Float))
        }
        scaleUpAnimator.setDuration(ONE_STEP_DURATION_MILLISECONDS).addUpdateListener {
            elemRT.scale(it.animatedValue as Float)
            elemLB.scale(it.animatedValue as Float)
        }

        //сглаживаем углы
        roundCornerAnimatorGroupOne.setDuration(ONE_STEP_DURATION_MILLISECONDS).addUpdateListener {
            Log.w("4444", "valui is ${it.animatedValue as Float}")
            elemLT.changeCorners(it.animatedValue as Float)
            elemRB.changeCorners(it.animatedValue as Float)

        }
        //сглаживаем углы
        roundCornerAnimatorGroupTwo.setDuration(ONE_STEP_DURATION_MILLISECONDS).addUpdateListener {
            Log.w("4444", "valui is ${it.animatedValue as Float}")
            elemRT.changeCorners(it.animatedValue as Float)
            elemLB.changeCorners(it.animatedValue as Float)

        }
        valueAnimator.setDuration(ONE_STEP_DURATION_MILLISECONDS).addUpdateListener {
            Log.w("workkk", "valui is ${it.animatedValue as Float}")
            elemLT.translate(it.animatedValue as Float)
            elemRT.translate(it.animatedValue as Float)
            elemRB.translate(it.animatedValue as Float)
            elemLB.translate(it.animatedValue as Float)
        }

        degreeAnimator.setDuration(ONE_STEP_DURATION_MILLISECONDS).addUpdateListener {

            elemLT.rotate(it.animatedValue as Float)
            elemRT.rotate(it.animatedValue as Float)
            elemRB.rotate(it.animatedValue as Float)
            elemLB.rotate(it.animatedValue as Float)
        }


        // mainAnimatorSet.playTogether(valueAnimator, degreeAnimator, roundCornerAnimator)
        mainAnimatorSet.removeAllListeners()

        mainAnimatorSet.doOnEnd {
            elemLT.changeDirection()
            elemRT.changeDirection()
            elemRB.changeDirection()
            elemLB.changeDirection()

            it.start()
        }

        mainAnimatorSet.playTogether(
            valueAnimator,
            scaleUpAnimator,
            scaleDownAnimator,
            roundCornerAnimatorGroupOne,
            roundCornerAnimatorGroupTwo,
            degreeAnimator
        )
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
        configureMainAnimatorSet()
        mainAnimatorSet.start()

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

        canvas.drawPath(elemLT.path, paintLT)
        canvas.drawPath(elemRT.path, paintRT)
        canvas.drawPath(elemRB.path, paintRB)
        canvas.drawPath(elemLB.path, paintLB)


        invalidate()

    }

    sealed class Position {
        object LT : Position() {
            override fun toString() = "1 LT"
        }

        object RT : Position() {
            override fun toString() = "2 RT"
        }

        object LB : Position() {
            override fun toString() = "4 LB"
        }

        object RB : Position() {
            override fun toString() = "3 RB"
        }
    }

    data class Elem(
        var position: Position
    ) {
        init {
            Log.e("qwqw", "constructor $position")
        }

        private val corners: FloatArray = floatArrayOf(0f, 0f, 0f, 0f, 0F, 0F, 0F, 0F)
        val path: Path = Path()

        private lateinit var rectF: RectF

        private var matrixRotate = Matrix()
        private var matrixScale = Matrix()

        var isReverseCorners = false

        private var offsetLeft: Float = 0.0f
        private var offsetRight: Float = 0.0f
        private var offsetTop: Float = 0.0f
        private var offsetBottom: Float = 0.0f
        internal var degree = 0f

        fun changeCorners(value: Float) {
            // val degree = if (isReverseCorners) ROUND_CORNER_VALUE - value else value

            Log.e("wawa", "my degree $degree")

            corners.changeAllTo(value)
        }

        fun scale(value: Float) {

            matrixScale.setScale(value, value, rectF.centerX(), rectF.centerY())
            path.transform(matrixScale)
        }

        fun changeDirection() {
            position = when (position) {
                Position.LT ->
                    Position.RT
                Position.RT ->
                    Position.RB
                Position.RB ->
                    Position.LB
                Position.LB ->
                    Position.LT
            }
            changeOffset()
            isReverseCorners = !isReverseCorners
        }

        fun setRectF(newRect: RectF) {
            rectF = newRect
            changeOffset()
            updatePath()
        }

        private fun changeOffset() {
            offsetLeft = rectF.left
            offsetRight = rectF.right
            offsetTop = rectF.top
            offsetBottom = rectF.bottom
        }

        private fun updatePath() {
            path.reset()
            path.addRoundRect(rectF, corners, Path.Direction.CW)
        }

        fun rotate(degree: Float) {
            this.degree = degree
            matrixRotate.setRotate(
                degree,
                rectF.centerX(),
                rectF.centerY()
            )
            path.transform(matrixRotate)
        }

        fun translate(value: Float) {
            when (position) {
                Position.LT -> {
                    rectF.left = offsetLeft + value
                    rectF.right = offsetRight + value
                }
                Position.RT -> {
                    rectF.top = offsetTop + value
                    rectF.bottom = offsetBottom + value
                }
                Position.LB -> {
                    rectF.top = offsetTop - value
                    rectF.bottom = offsetBottom - value
                }
                Position.RB -> {
                    rectF.left = offsetLeft - value
                    rectF.right = offsetRight - value
                }
            }
            updatePath()
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Elem

            if (rectF != other.rectF) return false
            if (position != other.position) return false
            if (!corners.contentEquals(other.corners)) return false
            if (path != other.path) return false
            if (offsetLeft != other.offsetLeft) return false
            if (offsetRight != other.offsetRight) return false
            if (offsetTop != other.offsetTop) return false
            if (offsetBottom != other.offsetBottom) return false

            return true
        }

        override fun hashCode(): Int {
            var result = rectF.hashCode()
            result = 31 * result + position.hashCode()
            result = 31 * result + corners.contentHashCode()
            result = 31 * result + path.hashCode()
            result = 31 * result + offsetLeft.hashCode()
            result = 31 * result + offsetRight.hashCode()
            result = 31 * result + offsetTop.hashCode()
            result = 31 * result + offsetBottom.hashCode()
            return result
        }
    }
}


