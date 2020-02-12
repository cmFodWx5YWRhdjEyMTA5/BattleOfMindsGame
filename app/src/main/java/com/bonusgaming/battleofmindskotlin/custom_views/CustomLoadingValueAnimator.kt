package com.bonusgaming.battleofmindskotlin.custom_views

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.content.res.ResourcesCompat
import com.bonusgaming.battleofmindskotlin.R

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

    companion object {
        const val SIZE_DESIRABLE = 100
        const val ONE_STEP_DURATION_MILLISECONDS = 800L
        const val ROUND_CORNER_DEFAULT_VALUE = 10F
        const val ROTATE_VALUE = 180F
    }

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
        strokeWidth = 3f
        style = Paint.Style.FILL
        typeface = ResourcesCompat.getFont(context, R.font.modak)
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
    private var _lastTime: Long = 0
    private var frames = 0
    private var fps = 0
    private val paintFPS: Paint = Paint().apply {
        color = colorLight
        strokeWidth = 1F
        textSize = 100F
        style = Paint.Style.FILL
    }
    ////////////////////////////////////////////////

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
        paintEndText.textSize = dpToPx((75 + (2F - endText.length)).toInt())
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
        val diameterElem = measuredHeight / 2.5f
        lengthWay = measuredHeight - diameterElem
        val offsetLeft = center.x - lengthWay / 2f - diameterElem / 2F
        val offsetTop = center.y - lengthWay / 2f - diameterElem / 2F

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
            else -> {
                SIZE_DESIRABLE
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
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
    @Suppress("unused")
    private fun computeFPS(canvas: Canvas) {
        frames++
        if ((System.currentTimeMillis() - _lastTime) > 1000) {
            fps = frames
            frames = 0
            _lastTime = System.currentTimeMillis()
        }
        canvas.drawText("FPS: $fps", 100.0f, 100.0f, paintFPS)
    }

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


