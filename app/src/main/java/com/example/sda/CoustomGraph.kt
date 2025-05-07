package com.example.sda

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class CustomGraphView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LineChart(context, attrs) {

    private val rpmDataSet = LineDataSet(mutableListOf(), "RPM").apply {
        color = Color.RED
        setCircleColor(Color.RED)
        lineWidth = 2f
        circleRadius = 3f
        valueTextColor = Color.RED
    }

    private val speedDataSet = LineDataSet(mutableListOf(), "Speed").apply {
        color = Color.BLUE
        setCircleColor(Color.BLUE)
        lineWidth = 2f
        circleRadius = 3f
        valueTextColor = Color.BLUE
    }

    private val brakeDataSet = LineDataSet(mutableListOf(), "Brake Pressure").apply {
        color = Color.GREEN
        setCircleColor(Color.GREEN)
        lineWidth = 2f
        circleRadius = 3f
        valueTextColor = Color.GREEN
    }

    private val accelDataSet = LineDataSet(mutableListOf(), "Accel Pressure").apply {
        color = Color.MAGENTA
        setCircleColor(Color.MAGENTA)
        lineWidth = 2f
        circleRadius = 3f
        valueTextColor = Color.MAGENTA
    }

    private val lineData = LineData(rpmDataSet, speedDataSet, brakeDataSet, accelDataSet)

    init {
        this.data = lineData
        this.invalidate()
    }

    fun updateData(rpm: Float?, speed: Float?, brake: Float?, accel: Float?) {
        val xValue = System.currentTimeMillis().toFloat() / 1000

        rpm?.let { rpmDataSet.addEntry(Entry(xValue, it)) }
        speed?.let { speedDataSet.addEntry(Entry(xValue, it)) }
        brake?.let { brakeDataSet.addEntry(Entry(xValue, it)) }
        accel?.let { accelDataSet.addEntry(Entry(xValue, it)) }

        lineData.notifyDataChanged()
        this.notifyDataSetChanged()
        this.invalidate()
    }
}
