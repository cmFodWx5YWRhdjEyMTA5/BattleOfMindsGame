package com.bonusgaming.battleofmindskotlin.ui

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Matrix
import android.graphics.Point
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.LinearGradient
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.content.res.ResourcesCompat

private const val DIRECT_DP_GRADIENT_VALUE = 75
private const val THREE_QUARTERS = 3 / 4F
private const val FOUR = 3 / 4F
private const val STROKE_WIDTH_END_TEXT = 3F
private const val DEBUG_TEXT_SIZE = 100f
private const val DIAMETER_ELEM_DELIMETER = 2.5F
private const val SIZE_DESIRABLE = 100
private const val ONE_STEP_DURATION_MILLISECONDS = 800L
private const val ROUND_CORNER_DEFAULT_VALUE = 10F
private const val ROTATE_VALUE = 180F

class CustomLoadingValueAnimator @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defAttrStyle: Int = 0,
        defResStyle: Int = 0
) : View(context, attributeSet, defAttrStyle, defResStyle) {
    private var isNeedDrawText = false
    private var isNoNeedInvalidate = false
    private var isInvisible = false
    private val endText: String
    private lateinit var onStopCallback: LoadingOnStop

    private lateinit var elemLT: Elem
    private lateinit var elemLB: Elem
    private lateinit var elemRT: Elem
    private lateinit var elemRB: Elem

    private val colorLight = Color.parseColor(COLOR_LIGHT_TEXT)
    private val colorDark = Color.parseColor(COLOR_DARK_TEXT)

    private var lengthWay: Float = -1F
    private var mainAnimatorSet = AnimatorSet()
    private var center = Point()

    private val paintEndText = Paint().apply {
        color = Color.GRAY
        strokeWidth = STROKE_WIDTH_END_TEXT
        style = Paint.Style.FILL
        typeface = ResourcesCompat.getFont(context, R.font.modak)
        textAlign = Paint.Align.CENTER
        shader = LinearGradient(
                0f,
                0f,
                dpToPx(DIRECT_DP_GRADIENT_VALUE), dpToPx(DIRECT_DP_GRADIENT_VALUE),
                colorLight,
                colorDark,
                Shader.TileMode.MIRROR
        )
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
        style = Paint.Style.FILL
    }

    private val paintLB: Paint = Paint().apply {
        color = colorLight
        strokeWidth = 1F
        style = Paint.Style.FILL
    }

    //use for debug
    /** private var _lastTime: Long = 0
    private var frames = 0
    private var fps = 0
    private val paintFPS: Paint = Paint().apply {
    color = colorLight
    strokeWidth = 1F
    textSize = DEBUG_TEXT_SIZE
    style = Paint.Style.FILL
    }
     */

    init {
        val styledAttributes = context.obtainStyledAttributes(
                attributeSet,
                R.styleable.CustomLoadingValueAnimator,
                defAttrStyle,
                0
        )
        endText =
                styledAttributes.getString(R.styleable.CustomLoadingValueAnimator_onEndText) ?: "VS"
        styledAttributes.recycle()

        //масштабирование текста в зависимости от длины
        paintEndText.textSize = dpToPx((DIRECT_DP_GRADIENT_VALUE + (2F - endText.length)).toInt())
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        isInvisible = visibility == INVISIBLE
    }

    private fun elemsOut() {
        if (mainAnimatorSet.isRunning) mainAnimatorSet.pause()
        val valueAnimator: ValueAnimator = ValueAnimator.ofFloat(1F, 0F).apply {
            interpolator = DecelerateInterpolator()
            duration = ONE_STEP_DURATION_MILLISECONDS
            addUpdateListener {
                elemLT.scale(it.animatedValue as Float)
                elemRT.scale(it.animatedValue as Float)
                elemRB.scale(it.animatedValue as Float)
                elemLB.scale(it.animatedValue as Float)
                if (!isInvisible) invalidate()
            }
        }
        valueAnimator.removeAllListeners()
        valueAnimator.doOnEnd {
            showText()
        }
        valueAnimator.start()
    }

    private fun showText() {
        val targetSize = paintEndText.textSize
        paintEndText.textSize = 0f
        isNeedDrawText = true
        val valueAnimator: ValueAnimator = ValueAnimator.ofFloat(0F, targetSize).apply {
            interpolator = BounceInterpolator()
            duration = ONE_STEP_DURATION_MILLISECONDS
            addUpdateListener {
                paintEndText.textSize = it.animatedValue as Float
                if (!isInvisible) invalidate()
            }
        }
        valueAnimator.removeAllListeners()
        valueAnimator.doOnEnd {
            isNoNeedInvalidate = true
            if (this::onStopCallback.isInitialized)
                onStopCallback.onStop()
        }
        valueAnimator.start()
    }

    private fun calculateSizes() {
        val diameterElem = measuredHeight / DIAMETER_ELEM_DELIMETER
        lengthWay = measuredHeight - diameterElem
        val offsetLeft = center.x - lengthWay / 2f - diameterElem / 2F
        val offsetTop = center.y - lengthWay / 2f - diameterElem / 2F

        elemLT = Elem(Position.LT)
        elemLB = Elem(Position.LB)
        elemRT = Elem(Position.RT)
        elemRB = Elem(Position.RB)

        elemLT.setRectF(
                RectF(
                        diameterElem / FOUR + offsetLeft,
                        diameterElem / FOUR + offsetTop,
                        diameterElem * THREE_QUARTERS + offsetLeft,
                        diameterElem * THREE_QUARTERS + offsetTop
                )
        )
        elemRT.setRectF(
                RectF(
                        lengthWay + diameterElem / FOUR + offsetLeft,
                        diameterElem / FOUR + offsetTop,
                        lengthWay + diameterElem * THREE_QUARTERS + offsetLeft,
                        diameterElem * THREE_QUARTERS + offsetTop
                )
        )
        elemRB.setRectF(
                RectF(
                        lengthWay + diameterElem / FOUR + offsetLeft,
                        lengthWay + diameterElem / FOUR + offsetTop,
                        lengthWay + diameterElem * THREE_QUARTERS + offsetLeft,
                        lengthWay + diameterElem * THREE_QUARTERS + offsetTop
                )
        )
        elemLB.setRectF(
                RectF(
                        diameterElem / FOUR + offsetLeft,
                        lengthWay + diameterElem / FOUR + offsetTop,
                        diameterElem * THREE_QUARTERS + offsetLeft,
                        lengthWay + diameterElem * THREE_QUARTERS + offsetTop
                )
        )
    }

    private fun configureMainAnimatorSet() {
        val valueAnimator: ValueAnimator =
                ValueAnimator.ofFloat(0F, lengthWay).apply {
                    duration = ONE_STEP_DURATION_MILLISECONDS
                    interpolator = AccelerateDecelerateInterpolator()
                }
        val valueAnimatorDown: ValueAnimator =
                ValueAnimator.ofFloat(0F, lengthWay).apply {
                    duration = ONE_STEP_DURATION_MILLISECONDS
                    interpolator = BounceInterpolator()
                }
        val degreeAnimator: ValueAnimator = ValueAnimator.ofFloat(0F, ROTATE_VALUE)
                .apply {
                    duration = ONE_STEP_DURATION_MILLISECONDS
                    interpolator = DecelerateInterpolator()
                }
        //если елемент двигается НЕ вниз, то использует AccelerateDecelerateInterpolator
        valueAnimator.setDuration(ONE_STEP_DURATION_MILLISECONDS).addUpdateListener {
            if (elemLT.position != Position.RT)
                elemLT.translate(it.animatedValue as Float)
            if (elemRT.position != Position.RT)
                elemRT.translate(it.animatedValue as Float)
            if (elemRB.position != Position.RT)
                elemRB.translate(it.animatedValue as Float)
            if (elemLB.position != Position.RT)
                elemLB.translate(it.animatedValue as Float)
            if (!isNeedDrawText && !isInvisible) invalidate()
        }
        //если елемент двигается вниз, то использует BounceInterpolator
        valueAnimatorDown.setDuration(ONE_STEP_DURATION_MILLISECONDS).addUpdateListener {
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
                degreeAnimator
        )
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        fun getExceptSize(measureSpec: Int): Int {
            val mode = MeasureSpec.getMode(measureSpec)
            val size = MeasureSpec.getSize(measureSpec)
            return when (mode) {
                MeasureSpec.AT_MOST -> {
                    minOf(size, SIZE_DESIRABLE)
                }
                MeasureSpec.EXACTLY -> {
                    size
                }
                else -> {
                    SIZE_DESIRABLE
                }
            }
        }

        center.x = MeasureSpec.getSize(widthMeasureSpec) / 2
        center.y = MeasureSpec.getSize(heightMeasureSpec) / 2

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
        elemsOut()
    }

    private fun drawEndText(canvas: Canvas) {
        if (isNoNeedInvalidate) return
        if (!isNeedDrawText) return
        canvas.drawText(
                endText,
                center.x.toFloat(),
                center.y + lengthWay / 2f,
                paintEndText
        )
    }

    //use for debug
    /**  @Suppress("unused", "MagicNumber")
    private fun computeFPS(canvas: Canvas) {
    frames++
    if ((System.currentTimeMillis() - _lastTime) > 1000) {
    fps = frames
    frames = 0
    _lastTime = System.currentTimeMillis()
    }
    canvas.drawText("FPS: $fps", 100.0f, 100.0f, paintFPS)
    }*/

    private fun drawAllElements(canvas: Canvas) {
        canvas.drawPath(elemLT.path, paintLT)
        canvas.drawPath(elemRT.path, paintRT)
        canvas.drawPath(elemRB.path, paintRB)
        canvas.drawPath(elemLB.path, paintLB)
    }

    override fun onDraw(canvas: Canvas) {
        drawAllElements(canvas)
        drawEndText(canvas)
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
        val path: Path = Path()

        private val corners: FloatArray = floatArrayOf(0f, 0f, 0f, 0f, 0F, 0F, 0F, 0F)
        private lateinit var rectF: RectF
        private var matrix = Matrix()
        private var offsetLeft: Float = 0.0f
        private var offsetRight: Float = 0.0f
        private var offsetTop: Float = 0.0f
        private var offsetBottom: Float = 0.0f
        private var degree = 0f

        init {
            corners.changeAllTo(ROUND_CORNER_DEFAULT_VALUE)
        }

        fun scale(value: Float) {
            matrix.setScale(value, value, rectF.centerX(), rectF.centerY())
            path.transform(matrix)
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
            setOffset()
        }

        fun setRectF(newRect: RectF) {
            rectF = newRect
            setOffset()
            updatePath()
        }

        private fun setOffset() {
            offsetLeft = rectF.left
            offsetRight = rectF.right
            offsetTop = rectF.top
            offsetBottom = rectF.bottom
        }

        private fun updatePath() {
            path.reset()
            path.addRoundRect(rectF, corners, Path.Direction.CW)
            path.transform(matrix)
        }

        fun rotate(degree: Float) {
            this.degree = degree
            matrix.setRotate(
                    degree,
                    rectF.centerX(),
                    rectF.centerY()
            )
            path.transform(matrix)
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
    }
}


