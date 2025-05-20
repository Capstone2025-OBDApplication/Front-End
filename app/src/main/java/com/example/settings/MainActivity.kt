package com.example.settings

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources

class MainActivity : AppCompatActivity() {

    private lateinit var phoneListContainer: LinearLayout
    private lateinit var addPhoneNumber: TextView

    private lateinit var inputName: EditText
    private lateinit var inputPhone: EditText
    private lateinit var inputCar: EditText
    private lateinit var editProfile: TextView

    private var isEditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 연결
        phoneListContainer = findViewById(R.id.phoneListContainer)
        addPhoneNumber = findViewById(R.id.addPhoneNumber)
        inputName = findViewById(R.id.inputName)
        inputPhone = findViewById(R.id.inputPhone)
        inputCar = findViewById(R.id.inputCar)
        editProfile = findViewById(R.id.editProfile)

        // 수정/저장 버튼 클릭
        editProfile.setOnClickListener {
            isEditing = !isEditing

            setEditMode(inputName, isEditing)
            setEditMode(inputPhone, isEditing)
            setEditMode(inputCar, isEditing)

            if (isEditing) {
                editProfile.text = "저장하기"
                inputName.requestFocus()
            } else {
                editProfile.text = "수정하기"
                Toast.makeText(this, "정보가 저장되었습니다", Toast.LENGTH_SHORT).show()
            }
        }

        // 전화번호 추가 버튼 클릭
        addPhoneNumber.setOnClickListener {
            addNewPhoneNumber("추가된 번호")
        }
    }

    // 공통 EditText 편집 상태 설정 함수
    private fun setEditMode(editText: EditText, enable: Boolean) {
        editText.isEnabled = enable
        editText.isFocusable = enable
        editText.isFocusableInTouchMode = enable
    }

    // 전화번호 항목 추가
    private fun addNewPhoneNumber(number: String) {
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(12, 12, 12, 12)
            setBackgroundResource(R.drawable.phone_item_background)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 8, 0, 8)
            }
        }

        val numberInput = EditText(this).apply {
            setText(number)
            textSize = 16f
            isEnabled = false
            isFocusable = false
            isFocusableInTouchMode = false
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        val editIcon = ImageView(this)
        var isEditingNumber = false

        editIcon.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_edit))
        editIcon.setPadding(16, 0, 16, 0)

        editIcon.setOnClickListener {
            isEditingNumber = !isEditingNumber

            if (isEditingNumber) {
                // 편집 가능하게 전환
                setEditMode(numberInput, true)
                numberInput.requestFocus()
                editIcon.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_save))
            } else {
                // 편집 종료
                setEditMode(numberInput, false)
                editIcon.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_edit))
                Toast.makeText(this, "번호가 저장되었습니다", Toast.LENGTH_SHORT).show()
            }
        }

        val deleteIcon = ImageView(this).apply {
            setImageDrawable(AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_delete))
            setPadding(16, 0, 0, 0)
            setOnClickListener {
                phoneListContainer.removeView(container)
            }
        }

        container.addView(numberInput)
        container.addView(editIcon)
        container.addView(deleteIcon)

        phoneListContainer.addView(container)
    }
}
