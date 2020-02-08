package com.bonusgaming.battleofmindskotlin.custom_views

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.content.res.ResourcesCompat
import com.bonusgaming.battleofmindskotlin.R


class CustomLoadingValueAnimator @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defAttrStyle: Int = 0,
    defResStyle: Int = 0
) : View(context, attributeSet, defAttrStyle, defResStyle) {


    companion object {
        const val SIZE_DESIRABLE = 100
        const val ONE_STEP_DURATION_MILLISECONDS = 800L
        const val ROUND_CORNER_VALUE = 90F
        const val ROUND_CORNER_DEFAULT_VALUE = 10F
        const val ROTATE_VALUE = 180F
        const val SCALE_MIN_VALUE = 0.5F
        const val SCALE_MAX_VALUE = 1.2F

    }

    private val paintEndText = Paint().apply {


        color = Color.GRAY
        strokeWidth = 3f
        style = Paint.Style.FILL
        typeface = ResourcesCompat.getFont(context, R.font.modak)
        textSize = 30f
        textAlign = Paint.Align.CENTER
        shader = LinearGradient(
            0f,
            0f,
            dpToPx(75), dpToPx(75),
            colorLight,
            colorDark,
            Shader.TileMode.MIRROR
        )
    }
    private lateinit var endText: String
    private lateinit var elemLT: Elem
    private lateinit var elemLB: Elem
    private lateinit var elemRT: Elem
    private lateinit var elemRB: Elem

    private val colorLight = Color.parseColor("#F27A54")
    private val colorDark = Color.parseColor("#A154F2")

    private var lengthWay: Float = -1F
    private var diameterElem: Float = -1F
    private var mainAnimatorSet = AnimatorSet()
    private lateinit var center: Pair<Int, Int>


    private val paintFPS: Paint = Paint().apply {
        color = colorLight
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
        color = colorDark
        strokeWidth = 1F
        style = Paint.Style.FILL
    }

    private val paintRB: Paint = Paint().apply {
        color = Color.WHITE
        strokeWidth = 1F
        style = Paint.Style.FILL_AND_STROKE
    }

    private val paintLB: Paint = Paint().apply {
        color = colorLight
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
        endText="Jopa"
        diameterElem = measuredHeight / 3.0f
        lengthWay = measuredHeight - diameterElem
        val offsetLeft = center.first / 2f - lengthWay / 2f - diameterElem / 2F
        val offsetTop = center.second / 2f - lengthWay / 2f - diameterElem / 2F

        elemLT = Elem(Position.LT)
        elemLB = Elem(Position.LB)
        elemRT = Elem(Position.RT)
        elemRB = Elem(Position.RB)

        elemLT.setRectF(
            RectF(
                diameterElem / 4f + offsetLeft,
                diameterElem / 4f + offsetTop,
                diameterElem * 3f / 4f + offsetLeft,
                diameterElem * 3f / 4f + offsetTop
            )
        )
        elemRT.setRectF(
            RectF(
                lengthWay + diameterElem / 4F + offsetLeft,
                diameterElem / 4F + offsetTop,
                lengthWay + diameterElem * 3 / 4 + offsetLeft,
                diameterElem * 3F / 4F + offsetTop
            )
        )
        elemRB.setRectF(
            RectF(
                lengthWay + diameterElem / 4F + offsetLeft,
                lengthWay + diameterElem / 4F + offsetTop,
                lengthWay + diameterElem * 3f / 4f + offsetLeft,
                lengthWay + diameterElem * 3f / 4f + offsetTop
            )
        )
        elemLB.setRectF(
            RectF(
                diameterElem / 4F + offsetLeft,
                lengthWay + diameterElem / 4F + offsetTop,
                diameterElem * 3f / 4f + offsetLeft,
                lengthWay + diameterElem * 3f / 4f + offsetTop
            )
        )

    }

    private fun configureMainAnimatorSet() {
        val roundCornerAnimatorGroupOne: ValueAnimator = ValueAnimator.ofFloat(
            ROUND_CORNER_DEFAULT_VALUE,
            ROUND_CORNER_VALUE,
            ROUND_CORNER_DEFAULT_VALUE
        )

        val valueAnimator: ValueAnimator = ValueAnimator.ofFloat(0F, lengthWay)
        val valueAnimatorDown: ValueAnimator = ValueAnimator.ofFloat(0F, lengthWay)
        val degreeAnimator: ValueAnimator = ValueAnimator.ofFloat(0F, ROTATE_VALUE)
        val scaleDownAnimator: ValueAnimator = ValueAnimator.ofFloat(1F, SCALE_MIN_VALUE, 1F)

        valueAnimator.interpolator = LinearInterpolator()
        valueAnimatorDown.interpolator = BounceInterpolator()
        degreeAnimator.interpolator = DecelerateInterpolator()
        roundCornerAnimatorGroupOne.interpolator = DecelerateInterpolator()
        scaleDownAnimator.interpolator = DecelerateInterpolator()

        scaleDownAnimator.setDuration(ONE_STEP_DURATION_MILLISECONDS).addUpdateListener {
            elemLT.scale(it.animatedValue as Float)
            elemRB.scale(it.animatedValue as Float)
        }

        elemRT.changeCorners(ROUND_CORNER_DEFAULT_VALUE)
        elemLB.changeCorners(ROUND_CORNER_DEFAULT_VALUE)
        elemLT.changeCorners(ROUND_CORNER_DEFAULT_VALUE)
        elemRB.changeCorners(ROUND_CORNER_DEFAULT_VALUE)

        //сглаживаем углы
        roundCornerAnimatorGroupOne.setDuration(ONE_STEP_DURATION_MILLISECONDS).addUpdateListener {
            elemLT.changeCorners(it.animatedValue as Float)
            elemRB.changeCorners(it.animatedValue as Float)

        }
        valueAnimator.setDuration(ONE_STEP_DURATION_MILLISECONDS).addUpdateListener {
            Log.w("workkk", "valui is ${it.animatedValue as Float}")
            if (elemLT.position != Position.RT)
                elemLT.translate(it.animatedValue as Float)
            if (elemRT.position != Position.RT)
                elemRT.translate(it.animatedValue as Float)
            if (elemRB.position != Position.RT)
                elemRB.translate(it.animatedValue as Float)
            if (elemLB.position != Position.RT)
                elemLB.translate(it.animatedValue as Float)
        }

        valueAnimatorDown.setDuration(ONE_STEP_DURATION_MILLISECONDS).addUpdateListener {
            Log.w("workkk", "valui is ${it.animatedValue as Float}")
            if (elemLT.position == Position.RT)
                elemLT.translate(it.animatedValue as Float)
            if (elemRT.position == Position.RT)
                elemRT.translate(it.animatedValue as Float)
            if (elemRB.position == Position.RT)
                elemRB.translate(it.animatedValue as Float)
            if (elemLB.position == Position.RT)
                elemLB.translate(it.animatedValue as Float)
        }


        degreeAnimator.setDuration(ONE_STEP_DURATION_MILLISECONDS).addUpdateListener {

            elemLT.rotate(it.animatedValue as Float)
            elemRT.rotate(it.animatedValue as Float)
            elemRB.rotate(it.animatedValue as Float)
            elemLB.rotate(it.animatedValue as Float)
        }

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
            valueAnimatorDown,
//            scaleDownAnimator,
//            roundCornerAnimatorGroupOne,
            degreeAnimator
        )
    }

    private fun getExceptSize(measureSpec: Int): Int {

        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)

        return when (mode) {
            MeasureSpec.AT_MOST -> {
                Log.e("onMeasure", "mode AT_MOST")
                Log.e("onMeasure", "size ${minOf(size, SIZE_DESIRABLE)}")
                minOf(size, SIZE_DESIRABLE)
            }
            MeasureSpec.EXACTLY -> {
                Log.e("onMeasure", "mode EXACTLY")
                Log.e("onMeasure", "size $size")
                size
            }
            else -> {
                Log.e("onMeasure", "mode EXACTLY")
                Log.e("onMeasure", "size $SIZE_DESIRABLE")
                SIZE_DESIRABLE
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        center = Pair(
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )
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

    private fun drawEndText(canvas: Canvas) {
        canvas.drawText(
            endText,
            center.first.toFloat(),
            center.second + lengthWay / 2f,
            paintEndText
        )
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

        drawEndText(canvas)
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


