package io.instaleap.statusprogressbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

class LinearStateBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var currentBarPaint: Paint
    private var labelPaint: TextPaint
    private var circlePaint: Paint
    private var boundaryPath: Path

    private var widthLine: Int = 0
    private var heightLine: Int = 0
    private val cornerRadius = 16f

    private val textFontSize: Int
    private val lineHeight: Int
    private val textColor: Int

    private var dataList: List<DataModelState>? = null
    private var totalValue: Int? = null

    init {
        this.setLayerType(LAYER_TYPE_SOFTWARE, null)
        boundaryPath = Path()
        currentBarPaint = Paint()
        labelPaint = TextPaint()
        circlePaint = Paint()

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LinearStateBar, defStyleAttr,0)
        textFontSize = typedArray.getInteger(R.styleable.LinearStateBar_fontSize, 12)
        lineHeight = typedArray.getInteger(R.styleable.LinearStateBar_heightLineStatus, 10)
        textColor = typedArray.getColor(R.styleable.LinearStateBar_fontColor, Color.BLACK)
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()

        var leftBoundary = 2f
        var rightBoundary = 0f
        var circleCX = 15f
        var startLabelDx = circleCX + 25f

        dataList?.let { data ->
            for (model in data) {
                totalValue?.let {
                    val curWidth = ((model.value * widthLine) / it).toFloat()
                    rightBoundary += curWidth

                    drawLinearBar(
                        canvas = canvas,
                        leftBoundary = leftBoundary,
                        rightBoundary = rightBoundary,
                        model = model
                    )
                    leftBoundary = rightBoundary
                }
                drawCircleState(canvas, model, circleCX)
                drawLabels(canvas, model, startLabelDx)

                circleCX += (35f + labelPaint.measureText(model.value.toString()+" "+model.label) +20f)
                startLabelDx += (labelPaint.measureText(model.value.toString()+" "+model.label) + 55f)
            }
        }
        invalidate()
    }

    private fun drawLinearBar(
        canvas: Canvas,
        leftBoundary: Float,
        rightBoundary: Float,
        model: DataModelState
    ) {
        currentBarPaint.apply {
            style = Paint.Style.FILL
            color = model.color
        }

        boundaryPath = roundedRect(
            left = leftBoundary,
            top = 2f,
            right = rightBoundary,
            bottom = heightLine - 2f,
            rx = cornerRadius,
            ry = cornerRadius
        )
        canvas.drawPath(boundaryPath, currentBarPaint)
    }

    private fun drawLabels(canvas: Canvas, model: DataModelState, startDx: Float) {
        labelPaint.apply {
            isAntiAlias = true
            textSize = textFontSize * resources.displayMetrics.density
            color = textColor
        }
        canvas.drawText(model.value.toString()+" "+model.label, startDx, 70f, labelPaint)
    }

    private fun drawCircleState(canvas: Canvas, model: DataModelState, circleCx: Float) {
        circlePaint.apply {
            style = Paint.Style.FILL
            color = model.color
        }
        canvas.drawCircle(circleCx, 55f, 20f/2, circlePaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        widthLine = measuredWidth
        heightLine = measuredHeight

        val dimensions = measureDimensions(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(dimensions.first, dimensions.second)
    }

    private fun measureDimensions(widthMeasureSpec: Int, heightMeasureSpec: Int) : Pair<Int, Int> {
        val requestedWidth = MeasureSpec.getSize(widthMeasureSpec)
        val requestedWidthMode = MeasureSpec.getMode(widthMeasureSpec)

        val requestedHeight = MeasureSpec.getSize(heightMeasureSpec)
        val requestedHeightMode = MeasureSpec.getMode(heightMeasureSpec)

        val desiredWidth: Int = measuredWidth
        val desiredHeight: Int = 80

        val width = when (requestedWidthMode) {
            MeasureSpec.EXACTLY -> requestedWidth
            MeasureSpec.UNSPECIFIED -> desiredWidth
            else -> requestedWidth.coerceAtMost(desiredWidth)
        }

        val height = when (requestedHeightMode) {
            MeasureSpec.EXACTLY -> requestedHeight
            MeasureSpec.UNSPECIFIED -> desiredHeight
            else -> requestedHeight.coerceAtMost(desiredHeight)
        }
        return Pair(width, height)
    }

    private fun roundedRect(
        left: Float,
        top: Float,
        right: Float,
        bottom: Float,
        rx: Float,
        ry: Float
    ): Path {
        var rx = rx
        var ry = ry
        val path = Path()
        if (rx < 0) rx = 0f
        if (ry < 0) ry = 0f
        val width = right - left
        val height = lineHeight.toFloat()
        if (rx > width / 2) rx = width / 2
        if (ry > height / 2) ry = height / 2
        val widthMinusCorners = width - 2 * rx
        val heightMinusCorners = height - 2 * ry
        path.moveTo(right, top + ry)
        path.rQuadTo(0f, -ry, -rx, -ry) //top-right corner
        path.rLineTo(-widthMinusCorners, 0f)
        path.rQuadTo(-rx, 0f, -rx, ry) //top-left corner
        path.rLineTo(0f, heightMinusCorners)
        path.rQuadTo(0f, ry, rx, ry) //bottom-left corner
        path.rLineTo(widthMinusCorners, 0f)
        path.rQuadTo(rx, 0f, rx, -ry) //bottom-right corner
        path.rLineTo(0f, -heightMinusCorners)
        path.close() //Given close, last line to can be removed.
        return path
    }

    fun setDataModelView(dataList: List<DataModelState>, totalValue: Int) {
        this.dataList = dataList
        this.totalValue = totalValue
    }
}
