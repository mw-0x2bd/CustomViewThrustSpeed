package com.mw.rfniias.thrustspeed.ui.component

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.mw.rfniias.thrustspeed.R
import android.animation.ValueAnimator
import android.graphics.Path
import android.graphics.Path.FillType
import android.view.animation.AccelerateDecelerateInterpolator


class Point(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var heightPoint = 40f
    private var widthPoint = 40f

    private var heightA = 0f
    private var widthA = 40f

    private var state = STATE.NORMAL
    private var stateForAnimation = STATE.NORMAL

    private var centerX = 0f
    private var centerY = 0f

    private val paint = Paint()

    private val animatorIncrease: ValueAnimator
    private val animatorDecrease: ValueAnimator

    init {
        context.obtainStyledAttributes(attrs, R.styleable.Point).apply {
            heightPoint = getDimension(R.styleable.Point_height_point, heightPoint)
            widthPoint = getDimension(R.styleable.Point_width_point, widthPoint)
            recycle()
        }

        animatorIncrease = ValueAnimator.ofFloat(0f, widthPoint / 2f).apply {
            duration = 350
            addUpdateListener {
                widthA = it.animatedValue as Float
                invalidate()
            }
        }

        animatorDecrease = ValueAnimator.ofFloat(widthPoint / 2f, 0f).apply {
            duration = 350
            addUpdateListener {
                widthA = it.animatedValue as Float
                invalidate()
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    if (state != stateForAnimation) {
                        state = stateForAnimation
                        if (state != STATE.NORMAL) {
                            animatorIncrease.start()
                        }
                    }
                }

            })
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        setMeasuredDimension(
//            (widthPoint + paddingLeft + paddingRight).toInt(),
//            (heightPoint + paddingBottom + paddingTop).toInt()
//        )

        val desiredWidth = (widthPoint + paddingLeft + paddingRight).toInt()
        val desiredHeight = (heightPoint + paddingBottom + paddingTop).toInt()

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
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.isAntiAlias = true
//        paint.setShadowLayer(10f, 5f, 5f, Color.TRANSPARENT)

        when (state) {
            STATE.UP -> {
                paint.color = Color.GREEN
                canvas.drawPath(drawPointUp(), paint)
            }
            STATE.NORMAL -> {
                paint.color = Color.BLUE
                canvas.drawPath(drawPointNormal(), paint)
            }
            STATE.DOWN -> {
                paint.color = Color.RED
                canvas.drawPath(drawPointDown(), paint)
            }
        }
    }

    private fun drawPointDown() = Path().apply {
        fillType = FillType.EVEN_ODD
        moveTo(centerX - widthA, centerY - heightPoint / 2f)
        lineTo(centerX + widthA, centerY - heightPoint / 2f)
        lineTo(centerX, centerY + heightPoint / 2f)
    }

    private fun drawPointUp() = Path().apply {
        fillType = FillType.EVEN_ODD
        moveTo(centerX - widthA, centerY + heightPoint / 2f)
        lineTo(centerX + widthA, centerY + heightPoint / 2f)
        lineTo(centerX, centerY - heightPoint / 2f)
    }

    private fun drawPointNormal() = Path().apply {
        fillType = FillType.EVEN_ODD
        moveTo(centerX, centerY + heightPoint / 2f)
        lineTo(centerX, centerY + heightPoint / 2f)
        lineTo(centerX, centerY - heightPoint / 2f)
    }

    private enum class STATE {
        UP,
        NORMAL,
        DOWN
    }

    fun up() {
        if (animatorIncrease.isRunning) animatorIncrease.end()
        if (animatorDecrease.isRunning) animatorDecrease.end()
        if (state == STATE.NORMAL) {
            state = STATE.UP
            animatorIncrease.start()
        } else if (state == STATE.DOWN) {
            stateForAnimation = STATE.UP
            animatorDecrease.start()
        }
    }

    fun normal() {
        if (animatorIncrease.isRunning) animatorIncrease.end()
        if (animatorDecrease.isRunning) animatorDecrease.end()
        if (state == STATE.UP ||
            state == STATE.DOWN
        ) {
            stateForAnimation = STATE.NORMAL
            animatorDecrease.start()
        }
    }

    fun down() {
        if (animatorIncrease.isRunning) animatorIncrease.end()
        if (animatorDecrease.isRunning) animatorDecrease.end()
        if (state == STATE.NORMAL) {
            state = STATE.DOWN
            animatorIncrease.start()
        } else if (state == STATE.UP) {
            stateForAnimation = STATE.DOWN
            animatorDecrease.start()
        }
    }
}