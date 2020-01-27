package com.bonusgaming.battleofmindskotlin.custom_views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.bonusgaming.battleofmindskotlin.R
import com.google.android.material.math.MathUtils
import com.google.android.material.math.MathUtils.dist

import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class CustomLoading @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defAttrStyle: Int = 0,
    defResStyle: Int = 0
) : View(context, attributeSet, defAttrStyle, defResStyle) {

    companion object{
        const val SIZE_DP_ONE: Int = 5
        const val SIZE_OFFSET_DP: Int = 5

        const val SIZE_ONE = 1
        const val SIZE_TWO = 1
        const val SIZE_THREE = 1
        const val SIZE_FOUR = 1

        const val CENTER_DISTANCE = 1.5F

        const val DIRECTION_RIGHT = 1
        const val DIRECTION_DOWN = 2
        const val DIRECTION_LEFT = 3
        const val DIRECTION_UP = 4
        const val COLOR_GRADIENT_1 = "#F27A54"
        const val COLOR_GRADIENT_2 = "#A154F2"

        const val MAX_SIZE_TEXT_VS = 100
    }

    private var linearGradient: LinearGradient
    private lateinit var onStopCallback: LoadingOnStop
    private var distanceCheck = 15
    private var offsetDelimeter = 5
    private var isStopped: Boolean = false
    private var isStopping: Boolean = false
    private var isDrawVS: Boolean = false
    private var deltaT: Int = 0

    private var lastT: Long = 0

    private var angle: Double = -0.09
    private var fps: Int = 0
    private var frames: Int = 0
    private var _lastTime: Long = 0
    private var paintOne: Paint
    private var paintTwo: Paint
    private var paintThree: Paint
    private var paintFour: Paint
    private var paintFPS: Paint
    private var paintVS: Paint

    private var centerX: Int = 0
    private var centerY: Int = 0
    private var centerOneX: Float = 0.0F
    private var centerOneY: Float = 0.0F
    private var centerTwoX: Float = 0.0F
    private var centerTwoY: Float = 0.0F
    private var centerThreeX: Float = 0.0F
    private var centerThreeY: Float = 0.0F
    private var centerFourX: Float = 0.0F
    private var centerFourY: Float = 0.0F


    private var rectOne: Path
    private var rectHolderOne: RectHolder

    private var rectTwo: Path
    private var rectHolderTwo: RectHolder

    private var rectThree: Path
    private var rectHolderThree: RectHolder

    private var rectFour: Path
    private var rectHolderFour: RectHolder

    private var offsetX: Float = 0.0F
    private var offsetY: Float = 0.0F

    private fun getPaintForSquare(color: Int): Paint {
        val paint = Paint()
        paint.color = color
        paint.strokeWidth = 1F
        paint.style = Paint.Style.FILL
        return paint
    }

    init {
        paintOne = getPaintForSquare(Color.RED)
        paintTwo = getPaintForSquare(Color.YELLOW)
        paintThree = getPaintForSquare(Color.WHITE)
        paintFour = getPaintForSquare(Color.GREEN)

        paintFPS = Paint()
        paintFPS.color = Color.YELLOW
        paintFPS.strokeWidth = 2f
        paintFPS.style = Paint.Style.FILL
        paintFPS.textSize = 60.0f


        paintVS = Paint()
        paintVS.color = Color.GRAY
        paintVS.strokeWidth = 3f
        paintVS.style = Paint.Style.FILL
        paintVS.typeface = ResourcesCompat.getFont(context, R.font.modak)
        paintVS.textSize = 0.0f
        paintVS.textAlign = Paint.Align.CENTER

        linearGradient = LinearGradient(
            0f,
            0f,
            dpToPx(75), dpToPx(75),
            Color.parseColor(COLOR_GRADIENT_1),
            Color.parseColor(COLOR_GRADIENT_2),
            Shader.TileMode.MIRROR
        )
        paintVS.shader = linearGradient


        rectOne = Path()
        rectHolderOne = RectHolder(direction = DIRECTION_RIGHT)

        rectTwo = Path()
        rectHolderTwo = RectHolder(direction = DIRECTION_DOWN)

        rectThree = Path()
        rectHolderThree = RectHolder(direction = DIRECTION_LEFT)

        rectFour = Path()
        rectHolderFour = RectHolder(direction = DIRECTION_UP)

    }

    private fun rectToPath(rect: RectHolder, path: Path) {
        path.rewind()
        path.moveTo(rect.ltX, rect.ltY)
        path.lineTo(rect.rtX, rect.rtY)
        path.lineTo(rect.rbX, rect.rbY)
        path.lineTo(rect.lbX, rect.lbY)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        lastT = System.currentTimeMillis()
        centerX = MeasureSpec.getSize(widthMeasureSpec) / 2
        centerY = MeasureSpec.getSize(heightMeasureSpec) / 2

        centerOneX = MeasureSpec.getSize(widthMeasureSpec) / 2 - CENTER_DISTANCE * dpToPx(20)
        centerOneY = MeasureSpec.getSize(heightMeasureSpec) / 2 - CENTER_DISTANCE * dpToPx(20)

        centerTwoX = MeasureSpec.getSize(widthMeasureSpec) / 2 + CENTER_DISTANCE * dpToPx(20)
        centerTwoY = MeasureSpec.getSize(heightMeasureSpec) / 2 - CENTER_DISTANCE * dpToPx(20)


        centerThreeX = MeasureSpec.getSize(widthMeasureSpec) / 2 + CENTER_DISTANCE * dpToPx(20)
        centerThreeY = MeasureSpec.getSize(heightMeasureSpec) / 2 + CENTER_DISTANCE * dpToPx(20)

        centerFourX = MeasureSpec.getSize(widthMeasureSpec) / 2 - CENTER_DISTANCE * dpToPx(20)
        centerFourY = MeasureSpec.getSize(heightMeasureSpec) / 2 + CENTER_DISTANCE * dpToPx(20)


        var left: Float =
            (centerOneX - SIZE_ONE * dpToPx(SIZE_OFFSET_DP) - dpToPx(SIZE_DP_ONE))
        var top: Float =
            (centerOneY + SIZE_ONE * dpToPx(SIZE_OFFSET_DP) + dpToPx(SIZE_DP_ONE))

        var right: Float =
            (centerOneX + SIZE_ONE * dpToPx(SIZE_OFFSET_DP) + dpToPx(SIZE_DP_ONE))
        var bottom: Float =
            (centerOneY - SIZE_ONE * dpToPx(SIZE_OFFSET_DP) - dpToPx(SIZE_DP_ONE))

        rectHolderOne.ltX = left
        rectHolderOne.ltY = top
        rectHolderOne.rtX = right
        rectHolderOne.rtY = top
        rectHolderOne.rbX = right
        rectHolderOne.rbY = bottom
        rectHolderOne.lbX = left
        rectHolderOne.lbY = bottom
        rectHolderOne.centerX = centerOneX
        rectHolderOne.centerY = centerOneY

        left = centerTwoX - SIZE_TWO * dpToPx(SIZE_OFFSET_DP) - dpToPx(SIZE_DP_ONE)
        top = centerTwoY + SIZE_TWO * dpToPx(SIZE_OFFSET_DP) + dpToPx(SIZE_DP_ONE)

        right = centerTwoX + SIZE_TWO * dpToPx(SIZE_OFFSET_DP) + dpToPx(SIZE_DP_ONE)
        bottom = centerTwoY - SIZE_TWO * dpToPx(SIZE_OFFSET_DP) - dpToPx(SIZE_DP_ONE)

        rectHolderTwo.ltX = left
        rectHolderTwo.ltY = top
        rectHolderTwo.rtX = right
        rectHolderTwo.rtY = top
        rectHolderTwo.rbX = right
        rectHolderTwo.rbY = bottom
        rectHolderTwo.lbX = left
        rectHolderTwo.lbY = bottom
        rectHolderTwo.centerX = centerTwoX
        rectHolderTwo.centerY = centerTwoY

        left = (centerThreeX - SIZE_THREE * dpToPx(SIZE_OFFSET_DP) - dpToPx(SIZE_DP_ONE))
        top = (centerThreeY + SIZE_THREE * dpToPx(SIZE_OFFSET_DP) + dpToPx(SIZE_DP_ONE))

        right = (centerThreeX + SIZE_THREE * dpToPx(SIZE_OFFSET_DP) + dpToPx(SIZE_DP_ONE))
        bottom =
            (centerThreeY - SIZE_THREE * dpToPx(SIZE_OFFSET_DP) - dpToPx(SIZE_DP_ONE))

        rectHolderThree.ltX = left
        rectHolderThree.ltY = top
        rectHolderThree.rtX = right
        rectHolderThree.rtY = top
        rectHolderThree.rbX = right
        rectHolderThree.rbY = bottom
        rectHolderThree.lbX = left
        rectHolderThree.lbY = bottom

        rectHolderThree.centerX = centerThreeX
        rectHolderThree.centerY = centerThreeY

        left = (centerFourX - SIZE_FOUR * dpToPx(SIZE_OFFSET_DP) - dpToPx(SIZE_DP_ONE))
        top = (centerFourY + SIZE_FOUR * dpToPx(SIZE_OFFSET_DP) + dpToPx(SIZE_DP_ONE))

        right = (centerFourX + SIZE_FOUR * dpToPx(SIZE_OFFSET_DP) + dpToPx(SIZE_DP_ONE))
        bottom = (centerFourY - SIZE_FOUR * dpToPx(SIZE_OFFSET_DP) - dpToPx(SIZE_DP_ONE))

        rectHolderFour.ltX = left
        rectHolderFour.ltY = top
        rectHolderFour.rtX = right
        rectHolderFour.rtY = top
        rectHolderFour.rbX = right
        rectHolderFour.rbY = bottom
        rectHolderFour.lbX = left
        rectHolderFour.lbY = bottom
        rectHolderFour.centerX = centerFourX
        rectHolderFour.centerY = centerFourY

        offsetX = abs(rectHolderFour.rbX - centerFourX)
        offsetY = abs(rectHolderFour.rbY - centerFourY)
        rectToPath(rectHolderOne, rectOne)
        rectToPath(rectHolderFour, rectFour)
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
        if ((System.currentTimeMillis() - _lastTime) > 1000) {
            fps = frames
            frames = 0
            _lastTime = System.currentTimeMillis()
        }
        canvas.drawText("FPS: $fps", 500.0f, 120.0f, paintFPS)
    }

    private fun drawVersusText(canvas: Canvas) {
        canvas.drawText(
            "VS",
            centerX.toFloat(),
            centerY.toFloat() + centerY.toFloat() / 15,
            paintVS
        )
        paintVS.textSize = MathUtils.lerp(paintVS.textSize, dpToPx(MAX_SIZE_TEXT_VS), 0.1F)
        if (paintVS.textSize > dpToPx(MAX_SIZE_TEXT_VS)) isStopped = true
    }

    private fun rotateRectHolder(rect: RectHolder, angle: Double) {
        rect.ltX -= rect.centerX
        rect.ltY -= rect.centerY
        rect.rtX -= rect.centerX
        rect.rtY -= rect.centerY
        rect.rbX -= rect.centerX
        rect.rbY -= rect.centerY
        rect.lbX -= rect.centerX
        rect.lbY -= rect.centerY

        val newLTX: Float = (rect.ltX * cos(angle) - rect.ltY * sin(angle)).toFloat() + rect.centerX
        val newLTY: Float = (rect.ltX * sin(angle) + rect.ltY * cos(angle)).toFloat() + rect.centerY

        val newRTX: Float = (rect.rtX * cos(angle) - rect.rtY * sin(angle)).toFloat() + rect.centerX
        val newRTY: Float = (rect.rtX * sin(angle) + rect.rtY * cos(angle)).toFloat() + rect.centerY

        val newRBX: Float = (rect.rbX * cos(angle) - rect.rbY * sin(angle)).toFloat() + rect.centerX
        val newRBY: Float = (rect.rbX * sin(angle) + rect.rbY * cos(angle)).toFloat() + rect.centerY

        val newLBX: Float = (rect.lbX * cos(angle) - rect.lbY * sin(angle)).toFloat() + rect.centerX
        val newLBY: Float = (rect.lbX * sin(angle) + rect.lbY * cos(angle)).toFloat() + rect.centerY

        rect.ltX = newLTX
        rect.ltY = newLTY
        rect.rtX = newRTX
        rect.rtY = newRTY
        rect.rbX = newRBX
        rect.rbY = newRBY
        rect.lbX = newLBX
        rect.lbY = newLBY
    }

    //1 -right,2-down,3-left,4-up
    private fun translateRectHolder(rect: RectHolder) {
        checkDirection(rect)
        val offset = deltaT / offsetDelimeter
        when (rect.direction) {
            DIRECTION_RIGHT -> {
                rect.ltX += offset
                rect.rtX += offset
                rect.rbX += offset
                rect.lbX += offset
                rect.centerX += offset
            }
            DIRECTION_DOWN -> {
                rect.ltY += offset
                rect.rtY += offset
                rect.rbY += offset
                rect.lbY += offset
                rect.centerY += offset
            }
            DIRECTION_LEFT -> {
                rect.ltX -= offset
                rect.rtX -= offset
                rect.rbX -= offset
                rect.lbX -= offset
                rect.centerX -= offset
            }
            DIRECTION_UP -> {
                rect.ltY -= offset
                rect.rtY -= offset
                rect.rbY -= offset
                rect.lbY -= offset
                rect.centerY -= offset
            }
        }
    }

    private fun checkDirection(rect: RectHolder) {
        when {
            dist(rect.centerX, rect.centerY, centerTwoX, centerTwoY) < distanceCheck
            -> {
                rect.direction = DIRECTION_DOWN
            }
            dist(rect.centerX, rect.centerY, centerThreeX, centerThreeY) < distanceCheck
            -> {
                rect.direction = DIRECTION_LEFT
            }
            dist(rect.centerX, rect.centerY, centerFourX, centerFourY) < distanceCheck
            -> {
                rect.direction = DIRECTION_UP
            }
            dist(rect.centerX, rect.centerY, centerOneX, centerOneY) < distanceCheck
            -> {
                rect.direction = DIRECTION_RIGHT
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        deltaT = (System.currentTimeMillis() - lastT).toInt()
        lastT = System.currentTimeMillis()

        if (isStopping) {
            stopingAnimation(rectHolderOne)
            stopingAnimation(rectHolderTwo)
            stopingAnimation(rectHolderThree)
            stopingAnimation(rectHolderFour)
        }

        if (isDrawVS) drawVersusText(canvas)

        rotateRectHolder(rectHolderOne, angle)
        translateRectHolder(rectHolderOne)
        rectToPath(rectHolderOne, rectOne)
        canvas.drawPath(rectOne, paintOne)

        rotateRectHolder(rectHolderTwo, angle)
        translateRectHolder(rectHolderTwo)
        rectToPath(rectHolderTwo, rectTwo)
        canvas.drawPath(rectTwo, paintTwo)

        rotateRectHolder(rectHolderThree, angle)
        translateRectHolder(rectHolderThree)
        rectToPath(rectHolderThree, rectThree)
        canvas.drawPath(rectThree, paintThree)

        rotateRectHolder(rectHolderFour, angle)
        translateRectHolder(rectHolderFour)
        rectToPath(rectHolderFour, rectFour)
        canvas.drawPath(rectFour, paintFour)

        if (!isStopped)
            invalidate()
        else if (::onStopCallback.isInitialized)
            onStopCallback.onStop()

    }

    private fun stopingAnimation(rect: RectHolder) {
        if (dist(rect.rtX, rect.rtY, rect.centerX, rect.centerY) < 4) {
            rect.rtX = rect.centerX
            rect.rtY = rect.centerY
            rect.ltX = rect.centerX
            rect.ltY = rect.centerY
            rect.rbX = rect.centerX
            rect.rbY = rect.centerY
            rect.lbX = rect.centerX
            rect.lbY = rect.centerY
            isDrawVS = true
        }

        rect.rtX = MathUtils.lerp(rect.rtX, rect.centerX, deltaT / 500F)
        rect.rtY = MathUtils.lerp(rect.rtY, rect.centerY, deltaT / 500F)

        rect.ltX = MathUtils.lerp(rect.ltX, rect.centerX, deltaT / 500F)
        rect.ltY = MathUtils.lerp(rect.ltY, rect.centerY, deltaT / 500F)

        rect.rbX = MathUtils.lerp(rect.rbX, rect.centerX, deltaT / 500F)
        rect.rbY = MathUtils.lerp(rect.rbY, rect.centerY, deltaT / 500F)

        rect.lbX = MathUtils.lerp(rect.lbX, rect.centerX, deltaT / 500F)
        rect.lbY = MathUtils.lerp(rect.lbY, rect.centerY, deltaT / 500F)
    }

    class RectHolder(
        var ltX: Float = 0F,
        var ltY: Float = 0F,
        var rtX: Float = 0F,
        var rtY: Float = 0F,
        var rbX: Float = 0F,
        var rbY: Float = 0F,
        var lbX: Float = 0F,
        var lbY: Float = 0F,
        var centerX: Float = 0F,
        var centerY: Float = 0F,
        var direction: Int
    )

}


