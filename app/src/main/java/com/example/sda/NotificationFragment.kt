package com.example.sda

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.widget.LinearLayout
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.core.graphics.drawable.DrawableCompat

class NotificationFragment : Fragment() {

    data class NotificationItem(
        val findSDA: String,
        val date: String,
        val isCall: Pair<Boolean, String>,
        val callNum: Pair<String, String>
    )

    private val notifications = listOf(
        NotificationItem("오전 07:32", "2024. 10. 29.(화)", Pair(true, "오전 07:32"), Pair("112", "오전 07:33")),
        NotificationItem("오후 03:45", "2024. 10. 28.(월)", Pair(false, "오후 03:45"), Pair("", ""))
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = NotificationAdapter(notifications)

        val backButton = view.findViewById<ImageView>(R.id.ic_back)

        return view
    }

    class NotificationAdapter(private val notifications: List<NotificationItem>) :
        RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

        inner class NotificationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val dateTextView: TextView = view.findViewById(R.id.dateTextView)
            val isCallTextView: TextView = view.findViewById(R.id.isCallTextView)
            val findSDATextView: TextView = view.findViewById(R.id.findSDATextView)
            val callNumTextView: TextView = view.findViewById(R.id.callNumTextView)
            val isCallTimeTextView: TextView = view.findViewById((R.id.isCallTimeTextView))
            val findSDATimeTextView: TextView = view.findViewById((R.id.findSDATimeTextView))
            val callNumTimeTextView: TextView = view.findViewById((R.id.callNumTimeTextView))
            val callNumDataTextView: TextView = view.findViewById((R.id.callNumDataTextView))
            val callNumberLinearLayout: LinearLayout = view.findViewById((R.id.callNumLinearLayout))
            val isCallData: TextView = view.findViewById((R.id.isCallDataTextView))
            val isCallDataIcon: View = view.findViewById((R.id.isCallDataIcon))
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
            return NotificationViewHolder(view)
        }

        override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
            val item = notifications[position]

            holder.dateTextView.text = item.date
            holder.findSDATextView.text = "급발진이 감지되었습니다."
            holder.findSDATimeTextView.text =item.findSDA
            holder.isCallTextView.text = "긴급 신고 전화 여부를 물었습니다."
            holder.isCallTimeTextView.text = item.isCall.second
            if (item.isCall.first) {
                holder.callNumTextView.text = "긴급 신고 전화가 작동되었습니다."
                holder.callNumTimeTextView.text = item.callNum.second
                holder.callNumDataTextView.text = item.callNum.first
                holder.isCallData.text = "승인"
                DrawableCompat.setTint(holder.isCallDataIcon.background, Color.BLUE)
            }
            else {
                holder.callNumberLinearLayout.layoutParams.height = 0
                holder.callNumberLinearLayout.requestLayout()
                holder.isCallData.text = "거부"
                DrawableCompat.setTint(holder.isCallDataIcon.background, Color.RED)
                holder.isCallData.setTextColor(Color.RED)
            }
        }

        override fun getItemCount() = notifications.size
    }
}
