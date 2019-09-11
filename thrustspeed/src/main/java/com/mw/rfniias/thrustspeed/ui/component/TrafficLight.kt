package com.mw.rfniias.thrustspeed.ui.component

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.mw.rfniias.thrustspeed.R

class TrafficLight(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var orientation = ORIENTATION.VERTICAL
    private var radius = 40f
    private var radiusDimension = 40f
    private val lamps = hashMapOf<String, Lamp>(
//        "yellow" to Lamp(Color.YELLOW),
//        "red" to Lamp(Color.RED),
//        "green" to Lamp(Color.GREEN),
//        "white" to Lamp(Color.WHITE)
    )
    private var pole = Pair(0f, 0f)
    private val strokeWidth = 10f


    private val paint = Paint()

    private var centerX = 0f
    private var centerY = 0f

    private val animatorDecrease: ValueAnimator
    private val animatorIncrease: ValueAnimator

    init {
        context.obtainStyledAttributes(attrs, R.styleable.TrafficLight).apply {
            orientation = if (getInteger(
                    R.styleable.TrafficLight_orientation,
                    0
                ) == 0
            ) ORIENTATION.VERTICAL else ORIENTATION.HORIZONTAL
            radius = getDimensionPixelSize(R.styleable.TrafficLight_radius, radius.toInt()).toFloat()
            radiusDimension = getDimension(R.styleable.TrafficLight_radius, radiusDimension)
            recycle()
        }

        lamps["yellow"] = Lamp(this, Color.YELLOW)
        lamps["red"] = Lamp(this, Color.RED)
        lamps["green"] = Lamp(this, Color.GREEN)
        lamps["white"] = Lamp(this, Color.WHITE)

        animatorIncrease = ValueAnimator.ofFloat(0f, radius).apply {
            duration = 350
        }

        animatorDecrease = ValueAnimator.ofFloat(radius, 0f).apply {
            duration = 350
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desiredWidth: Int
        val desiredHeight: Int

        if (orientation == ORIENTATION.VERTICAL) {
            desiredWidth = (radiusDimension.toInt() + strokeWidth.toInt()) * 2 + paddingLeft + paddingRight
            desiredHeight = (radiusDimension.toInt() + strokeWidth.toInt()) * 9 + paddingBottom + paddingTop
        } else {
            desiredWidth = (radiusDimension.toInt() + strokeWidth.toInt()) * 9 + paddingLeft + paddingRight
            desiredHeight = (radiusDimension.toInt() + strokeWidth.toInt()) * 2 + paddingBottom + paddingTop
        }

        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        //Measure Width
        val width = when (widthMode) {
            View.MeasureSpec.EXACTLY -> //Must be this size
                widthSize
            View.MeasureSpec.AT_MOST -> //Can't be bigger than...
                Math.min(desiredWidth, widthSize)
            View.MeasureSpec.UNSPECIFIED -> //Be whatever you want
                desiredWidth
            else -> {
                desiredWidth
            }
        }

        //Measure Height
        val height = when (heightMode) {
            View.MeasureSpec.EXACTLY -> //Must be this size
                heightSize
            View.MeasureSpec.AT_MOST -> //Can't be bigger than...
                Math.min(desiredHeight, heightSize)
            View.MeasureSpec.UNSPECIFIED -> //Be whatever you want
                desiredWidth
            else -> //Be whatever you want
                desiredHeight
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        centerX = (w - paddingLeft - paddingRight) / 2f + paddingLeft
        centerY = (h - paddingTop - paddingBottom) / 2f + paddingTop

        when (orientation) {
            ORIENTATION.HORIZONTAL -> {
                lamps["yellow"]?.apply {
                    x = centerX - (radius + strokeWidth / 2f) * 2
                    y = centerY
                }
                lamps["red"]?.apply {
                    x = centerX
                    y = centerY
                }
                lamps["green"]?.apply {
                    x = centerX + (radius + strokeWidth / 2f) * 2
                    y = centerY
                }
                lamps["white"]?.apply {
                    x = centerX + (radius + strokeWidth / 2f) * 4
                    y = centerY
                }
                pole = Pair(centerX - (radius + strokeWidth / 2f) * 4 + strokeWidth, centerY)
            }
            ORIENTATION.VERTICAL -> {
                lamps["yellow"]?.apply {
                    x = centerX
                    y = centerY + (radius + strokeWidth / 2f) * 2
                }
                lamps["red"]?.apply {
                    x = centerX
                    y = centerY
                }
                lamps["green"]?.apply {
                    x = centerX
                    y = centerY - (radius + strokeWidth / 2f) * 2
                }
                lamps["white"]?.apply {
                    x = centerX
                    y = centerY - (radius + strokeWidth / 2f) * 4
                }
                pole = Pair(centerX, centerY + (radius + strokeWidth / 2f) * 4 - strokeWidth)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.strokeWidth = strokeWidth
        paint.isAntiAlias = true
//        paint.setShadowLayer(5f, 50f, 15f, Color.BLUE)

        lamps.forEach { entry ->

            paint.style = Paint.Style.FILL
            paint.color = Color.BLACK
            canvas.drawCircle(entry.value.x, entry.value.y, radius, paint)

            paint.color = entry.value.color
            canvas.drawCircle(entry.value.x, entry.value.y, entry.value.radiusColor, paint)

            paint.style = Paint.Style.STROKE
            paint.color = Color.BLACK
            canvas.drawCircle(entry.value.x, entry.value.y, radius, paint)
        }

        paint.color = Color.BLACK
        if (orientation == ORIENTATION.HORIZONTAL) {
            canvas.drawLine(pole.first, pole.second, pole.first + radius, pole.second, paint) //столб
            canvas.drawLine(pole.first, pole.second - radius, pole.first, pole.second + radius, paint) // земля
        } else {
            canvas.drawLine(pole.first, pole.second, pole.first, pole.second - radius, paint)
            canvas.drawLine(pole.first - radius, pole.second, pole.first + radius, pole.second, paint)
        }

    }

    fun white() {
        onLamps(arrayOf("white"))
    }

    fun green() {
        onLamps(arrayOf("green"))
    }

    fun red() {
        onLamps(arrayOf("red"))
    }

    fun yellow() {
        onLamps(arrayOf("yellow"))
    }

    fun yellowGreen() {
        onLamps(arrayOf("yellow", "green"))
//        if (animatorIncrease.isRunning) animatorIncrease.end()
//        if (animatorDecrease.isRunning) animatorDecrease.end()
//
//        animatorIncrease.removeAllUpdateListeners()
//        animatorDecrease.removeAllUpdateListeners()
//
//        lamps.forEach {
//            if (it.value.light &&
//                it.key != "yellow" &&
//                it.key != "green"
//            ) {
//                it.value.light = false
//                animatorDecrease.addUpdateListener(it.value.updateListener)
//            }
//        }
//
//        if (lamps["yellow"]?.light == false) {
//            lamps["yellow"]?.light = true
//            animatorIncrease.addUpdateListener(lamps["yellow"]?.updateListener)
//        }
//
//        if (lamps["green"]?.light == false) {
//            lamps["green"]?.light = true
//            animatorIncrease.addUpdateListener(lamps["green"]?.updateListener)
//        }
//
//        animatorDecrease.start()
//        animatorIncrease.start()
    }

    private fun onLamps(listColor: Array<String>) {
        if (animatorIncrease.isRunning) animatorIncrease.end()
        if (animatorDecrease.isRunning) animatorDecrease.end()

        animatorIncrease.removeAllUpdateListeners()
        animatorDecrease.removeAllUpdateListeners()

        lamps.forEach {
            if (it.value.light &&
                !(it.key in listColor)
            ) {
                it.value.light = false
                animatorDecrease.addUpdateListener(it.value.updateListener)
            }
        }

        listColor.forEach {
            if (lamps[it]?.light == false) {
                lamps[it]?.light = true
                animatorIncrease.addUpdateListener(lamps[it]?.updateListener)
            }
        }

        animatorDecrease.start()
        animatorIncrease.start()
    }


    enum class ORIENTATION(val orientation: Int) {
        VERTICAL(0),
        HORIZONTAL(90)
    }

    private data class Lamp(
        val view: View,
        val color: Int,
        var radiusColor: Float = 0f,
        var x: Float = 0f,
        var y: Float = 0f,
        var light: Boolean = false
    ) {
        val updateListener: (valueAnimator: ValueAnimator) -> Unit = {
            radiusColor = it.animatedValue as Float
            view.invalidate()
        }
    }
}