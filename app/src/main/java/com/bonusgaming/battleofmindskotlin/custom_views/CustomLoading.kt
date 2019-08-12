package com.bonusgaming.battleofmindskotlin.custom_views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.bonusgaming.battleofmindskotlin.R
import com.google.android.material.math.MathUtils
import com.google.android.material.math.MathUtils.dist
import io.reactivex.disposables.Disposable
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

const val sizeDpOne: Int = 5
const val sizeOffsetDp: Int = 5

const val sizeOne = 1
const val sizeTwo = 1
const val sizeThree = 1
const val sizeFour = 1

const val centerDistance = 1.5F
 class CustomLoading : View {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    lateinit var linearGradient: LinearGradient
    var distanceCheck = 15
    var offsetDelimeter = 5
    var isStopped: Boolean = false
    var isStopping: Boolean = false
    var isDrawVS: Boolean = false
    var deltaT: Int = 0
    var lastT: Long = 0

    private var angle: Double = -8.05
    var fps: Int = 0
    var frames: Int = 0
    private var _lastTime: Long = 0
    var paintOne: Paint
    var paintTwo: Paint
    var paintThree: Paint
    var paintFour: Paint
    var paintFPS: Paint
    var paintVS: Paint

    var centerX: Int = 0
    var centerY: Int = 0
    var centerOneX: Float = 0.0F
    var centerOneY: Float = 0.0F
    var centerTwoX: Float = 0.0F
    var centerTwoY: Float = 0.0F
    var centerThreeX: Float = 0.0F
    var centerThreeY: Float = 0.0F

    var centerFourX: Float = 0.0F
    var centerFourY: Float = 0.0F


    var rectOne: Path
    var rectHolderOne: RectHolder

    var rectTwo: Path
    var rectHolderTwo: RectHolder

    var rectThree: Path
    var rectHolderThree: RectHolder

    var rectFour: Path
    var rectHolderFour: RectHolder

    var offsetX: Float = 0.0F
    var offsetY: Float = 0.0F
    lateinit var disposable: Disposable

    init {
        paintOne = Paint()
        paintOne.setColor(Color.RED)
        paintOne.setStrokeWidth(1f);
        paintOne.setStyle(Paint.Style.FILL);

        paintTwo = Paint()
        paintTwo.setColor(Color.YELLOW)
        paintTwo.setStrokeWidth(1f);
        paintTwo.setStyle(Paint.Style.FILL);

        paintThree = Paint()
        paintThree.setColor(Color.WHITE)
        paintThree.setStrokeWidth(1f);
        paintThree.setStyle(Paint.Style.FILL);

        paintFour = Paint()
        paintFour.setColor(Color.GREEN)
        paintFour.setStrokeWidth(1f);
        paintFour.setStyle(Paint.Style.FILL);

        paintFPS = Paint()
        paintFPS.setColor(Color.YELLOW)
        paintFPS.setStrokeWidth(2f);
        paintFPS.setStyle(Paint.Style.FILL);
        paintFPS.textSize = 60.0f

        paintVS = Paint()

        paintVS.setColor(Color.GRAY)
        paintVS.setStrokeWidth(3f);
        paintVS.setStyle(Paint.Style.FILL);
        paintVS.typeface = ResourcesCompat.getFont(context, R.font.modak)
        paintVS.textSize = 0.0f
        paintVS.textAlign = Paint.Align.CENTER
        linearGradient = LinearGradient(
            0f,
            0f,
            dpToPx(75), dpToPx(75),
            Color.parseColor("#F27A54"),
            Color.parseColor("#A154F2"),
            Shader.TileMode.MIRROR
        )
        paintVS.shader = linearGradient;


        rectOne = Path()
        rectHolderOne = RectHolder(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1, 1)

        rectTwo = Path()
        rectHolderTwo = RectHolder(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2, 2)

        rectThree = Path()
        rectHolderThree = RectHolder(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 3, 3)
        rectFour = Path()
        rectHolderFour = RectHolder(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 4, 4)

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
        Log.e(
            "Lifecycle",
            "onMeasure(${MeasureSpec.getSize(widthMeasureSpec)}, ${MeasureSpec.getSize(heightMeasureSpec)})"
        )
        lastT = System.currentTimeMillis()
        centerX = MeasureSpec.getSize(widthMeasureSpec) / 2
        centerY = MeasureSpec.getSize(heightMeasureSpec) / 2

        centerOneX = MeasureSpec.getSize(widthMeasureSpec) / 2 - centerDistance * dpToPx(20)
        centerOneY = MeasureSpec.getSize(heightMeasureSpec) / 2 - centerDistance * dpToPx(20)

        centerTwoX = MeasureSpec.getSize(widthMeasureSpec) / 2 + centerDistance * dpToPx(20)
        centerTwoY = MeasureSpec.getSize(heightMeasureSpec) / 2 - centerDistance * dpToPx(20)


        centerThreeX = MeasureSpec.getSize(widthMeasureSpec) / 2 + centerDistance * dpToPx(20)
        centerThreeY = MeasureSpec.getSize(heightMeasureSpec) / 2 + centerDistance * dpToPx(20)

        centerFourX = MeasureSpec.getSize(widthMeasureSpec) / 2 - centerDistance * dpToPx(20)
        centerFourY = MeasureSpec.getSize(heightMeasureSpec) / 2 + centerDistance * dpToPx(20)


        var left: Float = (centerOneX - sizeOne * dpToPx(sizeOffsetDp) - dpToPx(sizeDpOne)).toFloat()
        var top: Float = (centerOneY + sizeOne * dpToPx(sizeOffsetDp) + dpToPx(sizeDpOne)).toFloat()

        var right: Float = (centerOneX + sizeOne * dpToPx(sizeOffsetDp) + dpToPx(sizeDpOne)).toFloat()
        var bottom: Float = (centerOneY - sizeOne * dpToPx(sizeOffsetDp) - dpToPx(sizeDpOne)).toFloat()

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

        left = (centerTwoX - sizeTwo * dpToPx(sizeOffsetDp) - dpToPx(sizeDpOne)).toFloat()
        top = (centerTwoY + sizeTwo * dpToPx(sizeOffsetDp) + dpToPx(sizeDpOne)).toFloat()

        right = (centerTwoX + sizeTwo * dpToPx(sizeOffsetDp) + dpToPx(sizeDpOne)).toFloat()
        bottom = (centerTwoY - sizeTwo * dpToPx(sizeOffsetDp) - dpToPx(sizeDpOne)).toFloat()

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

        left = (centerThreeX - sizeThree * dpToPx(sizeOffsetDp) - dpToPx(sizeDpOne)).toFloat()
        top = (centerThreeY + sizeThree * dpToPx(sizeOffsetDp) + dpToPx(sizeDpOne)).toFloat()

        right = (centerThreeX + sizeThree * dpToPx(sizeOffsetDp) + dpToPx(sizeDpOne)).toFloat()
        bottom = (centerThreeY - sizeThree * dpToPx(sizeOffsetDp) - dpToPx(sizeDpOne)).toFloat()

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

        left = (centerFourX - sizeFour * dpToPx(sizeOffsetDp) - dpToPx(sizeDpOne)).toFloat()
        top = (centerFourY + sizeFour * dpToPx(sizeOffsetDp) + dpToPx(sizeDpOne)).toFloat()

        right = (centerFourX + sizeFour * dpToPx(sizeOffsetDp) + dpToPx(sizeDpOne)).toFloat()
        bottom = (centerFourY - sizeFour * dpToPx(sizeOffsetDp) - dpToPx(sizeDpOne)).toFloat()

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

    public fun startStopping() {
        isStopping = true
        offsetDelimeter /= 2
        distanceCheck *= 2
    }

    override fun requestLayout() {
        super.requestLayout()
        Log.e("Lifecycle", "requestLayout")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.e("Lifecycle", "onLayout")
    }

    fun dpToPx(size: Int): Float {
        return (size * resources.displayMetrics.density);
    }

    private fun computeFPS(canvas: Canvas) {
        frames++
        if ((System.currentTimeMillis() - _lastTime) > 1000) {
            fps = frames
            frames = 0
            _lastTime = System.currentTimeMillis()
        }
        canvas.drawText("FPS: $fps", 500.0f, 120.0f, paintFPS);
    }

    private fun drawVS(canvas: Canvas) {
        canvas.drawText("VS", centerX.toFloat(), centerY.toFloat() + centerY.toFloat() / 15, paintVS);
        paintVS.textSize = MathUtils.lerp(paintVS.textSize, 220F, 0.1F)
        if (paintVS.textSize > 250) isStopped = true

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
            1 -> {
                rect.ltX += offset
                rect.rtX += offset
                rect.rbX += offset
                rect.lbX += offset
                rect.centerX += offset
            }
            2 -> {
                rect.ltY += offset
                rect.rtY += offset
                rect.rbY += offset
                rect.lbY += offset
                rect.centerY += offset
            }
            3 -> {
                rect.ltX -= offset
                rect.rtX -= offset
                rect.rbX -= offset
                rect.lbX -= offset
                rect.centerX -= offset
            }
            4 -> {
                rect.ltY -= offset
                rect.rtY -= offset
                rect.rbY -= offset
                rect.lbY -= offset
                rect.centerY -= offset
            }
        }


    }

    private fun checkDirection(rect: RectHolder) {
        if (dist(
                rect.centerX,
                rect.centerY,
                centerTwoX,
                centerTwoY
            ) < distanceCheck
        ) {
            Log.e("DEF", "${rect.defaultDirection}")
            Log.e("WAS", "${rect.direction}")
            Log.e("SET", "${2}")
            rect.direction = 2
        } else if (dist(
                rect.centerX,
                rect.centerY,
                centerThreeX,
                centerThreeY
            ) < distanceCheck
        ) {
            Log.e("DEF", "${rect.defaultDirection}")
            Log.e("WAS", "${rect.direction}")
            Log.e("SET", "${3}")
            rect.direction = 3
        } else if (dist(
                rect.centerX,
                rect.centerY,
                centerFourX,
                centerFourY
            ) < distanceCheck
        ) {
            Log.e("DEF", "${rect.defaultDirection}")
            Log.e("WAS", "${rect.direction}")
            Log.e("SET", "${4}")
            rect.direction = 4
        } else if (dist(
                rect.centerX,
                rect.centerY,
                centerOneX,
                centerOneY
            ) < distanceCheck
        ) {
            Log.e("DEF", "${rect.defaultDirection}")
            Log.e("WAS", "${rect.direction}")
            Log.e("SET", "${5}")
            rect.direction = 1
        }
    }


    override fun onDraw(canvas: Canvas) {
        // Log.e("Lifecycle", "onDraw $centerX, $centerY")

        deltaT = (System.currentTimeMillis() - lastT).toInt()
        lastT = System.currentTimeMillis()


        if (isStopping) {
            stopingAnimation(rectHolderOne)
            stopingAnimation(rectHolderTwo)
            stopingAnimation(rectHolderThree)
            stopingAnimation(rectHolderFour)
        }

        if (isDrawVS) drawVS(canvas)

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
        var ltX: Float,
        var ltY: Float,
        var rtX: Float,
        var rtY: Float,
        var rbX: Float,
        var rbY: Float,
        var lbX: Float,
        var lbY: Float,
        var centerX: Float,
        var centerY: Float,
        var direction: Int,
        var defaultDirection: Int
    )

}


