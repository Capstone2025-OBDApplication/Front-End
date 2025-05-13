package com.example.sda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import com.github.mikephil.charting.data.Entry
import java.util.*

class DrivingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_driving, container, false)

        // 캘린더 선택 날짜 처리
        val calendarView = view.findViewById<CustomCalendarView>(R.id.calendarView)
        val timeText = view.findViewById<TextView>(R.id.time)

        calendarView.setOnDateSelectedListener(object : CustomCalendarView.OnDateSelectedListener {
            override fun onDateSelected(date: Calendar) {
                val formatter = SimpleDateFormat("yyyy.MM.dd (E)", Locale.KOREAN)
                val formatted = formatter.format(date.time)
                timeText.text = formatted
            }
        })

        // 그래프 설정
        val customGraphView = view.findViewById<CustomGraphView>(R.id.graphView)

        // ✅ 운전 기록용 샘플 데이터 추가
        setupDrivingRecordGraph(customGraphView)

        return view
    }

    private fun setupDrivingRecordGraph(customGraphView: CustomGraphView) {
        // 샘플 데이터
        val speedList = listOf(
            Entry(0f, 0f), Entry(1f, 0f), Entry(2f, 0f), Entry(3f, 5f),
            Entry(4f, 20f), Entry(4.5f, 45f), // 급발진 조건 성립
            Entry(5f, 60f), Entry(6f, 70f), Entry(7f, 75f), Entry(8f, 78f),
            Entry(9f, 80f), Entry(10f, 80f), Entry(11f, 75f), Entry(12f, 60f),
            Entry(13f, 30f), Entry(14f, 5f), Entry(15f, 0f)
        )

        val rpmList = listOf(
            Entry(0f, 800f), Entry(1f, 800f), Entry(2f, 800f), Entry(3f, 1000f),
            Entry(4f, 1500f), Entry(4.5f, 3200f), // 급발진 조건 성립
            Entry(5f, 3500f), Entry(6f, 3600f), Entry(7f, 3700f), Entry(8f, 3600f),
            Entry(9f, 3400f), Entry(10f, 3000f), Entry(11f, 2000f), Entry(12f, 1500f),
            Entry(13f, 1000f), Entry(14f, 900f), Entry(15f, 800f)
        )

        val accelList = listOf(
            Entry(0f, 0f), Entry(1f, 0f), Entry(2f, 0f), Entry(3f, 2f),
            Entry(4f, 1f), Entry(4.5f, 0f), // 급발진 조건 성립 (엑셀 안 밟음)
            Entry(5f, 30f), Entry(6f, 50f), Entry(7f, 70f), Entry(8f, 80f),
            Entry(9f, 75f), Entry(10f, 70f), Entry(11f, 40f), Entry(12f, 20f),
            Entry(13f, 5f), Entry(14f, 0f), Entry(15f, 0f)
        )

        val brakeList = List(speedList.size) { Entry(it.toFloat(), 0f) } // 전체 무브레이크 상황


        val highlightXs = listOf(4.5f)
        customGraphView.setDataLists(
            speedList = speedList,
            brakeList = brakeList,
            accelList = accelList,
            rpmList = rpmList,
            highlightXs = highlightXs
        )
    }
}
