package com.example.settings

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var phoneInput: EditText
    private lateinit var addPhoneButton: Button
    private lateinit var phoneListContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        backButton = findViewById(R.id.backButton)
        phoneInput = findViewById(R.id.phoneInput)
        addPhoneButton = findViewById(R.id.addPhoneButton)
        phoneListContainer = findViewById(R.id.phoneListContainer)

        backButton.setOnClickListener {
            finish()
        }

        addPhoneButton.setOnClickListener {
            val number = phoneInput.text.toString().trim()
            if (number.isNotEmpty()) {
                addPhoneNumber(number)
                phoneInput.text.clear()
            }
        }
    }

    private fun addPhoneNumber(number: String) {
        val inflater = layoutInflater
        val itemView = inflater.inflate(R.layout.item_phone_number, phoneListContainer, false)

        val phoneText = itemView.findViewById<TextView>(R.id.phoneNumberText)
        val editButton = itemView.findViewById<ImageView>(R.id.editButton)
        val deleteButton = itemView.findViewById<ImageView>(R.id.deleteButton)

        phoneText.text = number

        editButton.setOnClickListener {
            val newNumber = phoneInput.text.toString().trim()
            if (newNumber.isNotEmpty()) {
                phoneText.text = newNumber
                phoneInput.text.clear()
            } else {
                Toast.makeText(this, "수정할 번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        deleteButton.setOnClickListener {
            phoneListContainer.removeView(itemView)
        }

        phoneListContainer.addView(itemView)
    }
}
