package com.example.sda

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.example.sda.R

class CustomGraphView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val speedDataSet = LineDataSet(mutableListOf(), "속도").apply {
        color = Color.BLUE
        setCircleColor(Color.BLUE)
        lineWidth = 2f
        setDrawCircles(false)
    }
    private val brakeDataSet = LineDataSet(mutableListOf(), "브레이크").apply {
        color = Color.GREEN
        setCircleColor(Color.GREEN)
        lineWidth = 2f
        setDrawCircles(false)
    }
    private val accelDataSet = LineDataSet(mutableListOf(), "엑셀").apply {
        color = Color.MAGENTA
        setCircleColor(Color.MAGENTA)
        lineWidth = 2f
        setDrawCircles(false)
    }
    private val rpmDataSet = LineDataSet(mutableListOf(), "RPM").apply {
        color = Color.RED
        setCircleColor(Color.RED)
        lineWidth = 2f
        setDrawCircles(false)
    }

    private val lineData = LineData(speedDataSet, brakeDataSet, accelDataSet, rpmDataSet)
    private val highlightList = mutableListOf<Float>()

    private lateinit var chart: LineChart
    private lateinit var btnSpeed: TextView
    private lateinit var btnBrake: TextView
    private lateinit var btnAccel: TextView
    private lateinit var btnRpm: TextView

    private var speedVisible = true
    private var brakeVisible = true
    private var accelVisible = true
    private var rpmVisible = true

    init {
        orientation = VERTICAL
        LayoutInflater.from(context).inflate(R.layout.custom_graph_view, this, true)

        chart = findViewById(R.id.graphChart)
        btnSpeed = findViewById(R.id.btnSpeed)
        btnBrake = findViewById(R.id.btnBrake)
        btnAccel = findViewById(R.id.btnAccel)
        btnRpm = findViewById(R.id.btnRpm)

        chart.data = lineData
        chart.isDragEnabled = true
        chart.setScaleEnabled(false)
        chart.legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        chart.legend.form = Legend.LegendForm.CIRCLE
        chart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER

        val leftAxis = chart.axisLeft
        leftAxis.axisMinimum = 0f
        leftAxis.axisMaximum = 250f

        val rightAxis = chart.axisRight
        rightAxis.axisMaximum = 200f

        setupButtons()
        chart.setRenderer(CustomLineChartRenderer(chart, chart.animator, chart.viewPortHandler))
        chart.invalidate()
    }

    private fun setupButtons() {
        btnSpeed.setOnClickListener {
            speedVisible = !speedVisible
            updateGraph()
            updateButtonUI(btnSpeed, speedVisible)
        }
        btnBrake.setOnClickListener {
            brakeVisible = !brakeVisible
            updateGraph()
            updateButtonUI(btnBrake, brakeVisible)
        }
        btnAccel.setOnClickListener {
            accelVisible = !accelVisible
            updateGraph()
            updateButtonUI(btnAccel, accelVisible)
        }
        btnRpm.setOnClickListener {
            rpmVisible = !rpmVisible
            updateGraph()
            updateButtonUI(btnRpm, rpmVisible)
        }
    }

    private fun updateGraph() {
        toggleVisibility(speedVisible, brakeVisible, accelVisible, rpmVisible)
    }

    private fun updateButtonUI(button: TextView, isActive: Boolean) {
        button.setTextColor(if (isActive) resources.getColor(R.color.blue) else resources.getColor(R.color.gray))
        button.setTypeface(null, if (isActive) android.graphics.Typeface.BOLD else android.graphics.Typeface.NORMAL)
    }

    fun updateData(
        xValue: Float,
        speed: Float? = null,
        brake: Float? = null,
        accel: Float? = null,
        rpm: Float? = null,
        highlight: Boolean = false
    ) {
        speed?.let { speedDataSet.addEntry(Entry(xValue, it)) }
        brake?.let { brakeDataSet.addEntry(Entry(xValue, it)) }
        accel?.let { accelDataSet.addEntry(Entry(xValue, it)) }
        rpm?.let { rpmDataSet.addEntry(Entry(xValue, it)) }

        if (highlight) highlightList.add(xValue)

        if (xValue > chart.xAxis.axisMaximum) {
            chart.xAxis.axisMaximum = xValue
        }

        chart.moveViewToX(xValue)
        lineData.notifyDataChanged()
        chart.notifyDataSetChanged()
        chart.invalidate()
    }

    fun setDataLists(
        speedList: List<Entry>,
        brakeList: List<Entry>,
        accelList: List<Entry>,
        rpmList: List<Entry>,
        highlightXs: List<Float> = emptyList()
    ) {
        speedDataSet.clear(); speedDataSet.values = speedList
        brakeDataSet.clear(); brakeDataSet.values = brakeList
        accelDataSet.clear(); accelDataSet.values = accelList
        rpmDataSet.clear(); rpmDataSet.values = rpmList

        highlightList.clear()
        highlightList.addAll(highlightXs)

        chart.xAxis.axisMaximum = (speedList.maxOfOrNull { it.x } ?: 0f).coerceAtLeast(10f)

        lineData.notifyDataChanged()
        chart.notifyDataSetChanged()
        chart.invalidate()
    }

    fun toggleVisibility(speed: Boolean, brake: Boolean, accel: Boolean, rpm: Boolean) {
        speedDataSet.isVisible = speed
        brakeDataSet.isVisible = brake
        accelDataSet.isVisible = accel
        rpmDataSet.isVisible = rpm

        lineData.notifyDataChanged()
        chart.notifyDataSetChanged()
        chart.invalidate()
    }

    fun setHighlightList(list: List<Float>) {
        highlightList.clear()
        highlightList.addAll(list)
        chart.invalidate()
    }

    inner class CustomLineChartRenderer(
        chart: LineChart,
        animator: com.github.mikephil.charting.animation.ChartAnimator,
        viewPortHandler: com.github.mikephil.charting.utils.ViewPortHandler
    ) : com.github.mikephil.charting.renderer.LineChartRenderer(chart, animator, viewPortHandler) {
        override fun drawExtras(c: Canvas) {
            super.drawExtras(c)
            highlightList.forEach { centerX ->
                val left = chart.getTransformer(speedDataSet.axisDependency)
                    .getPixelForValues(centerX - 3, 0f).x.toFloat()
                val right = chart.getTransformer(speedDataSet.axisDependency)
                    .getPixelForValues(centerX + 3, 0f).x.toFloat()
                val top = chart.viewPortHandler.contentTop()
                val bottom = chart.viewPortHandler.contentBottom()

                val shader = LinearGradient(
                    left, top, left, bottom,
                    Color.argb(100, 255, 0, 0),
                    Color.argb(0, 255, 0, 0),
                    Shader.TileMode.CLAMP
                )

                val paint = Paint().apply {
                    style = Paint.Style.FILL
                    this.shader = shader
                }
                c.drawRect(left, top, right, bottom, paint)
            }
        }
    }
}
