package com.example.canstone2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.canstone2.databinding.ActivitySuddenWarningBinding // Databinding import

class SuddenWarningActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuddenWarningBinding // Databinding 변수 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuddenWarningBinding.inflate(layoutInflater) // Binding 초기화
        setContentView(binding.root)


        binding.backToMainButton.setOnClickListener {
            finish()

        }
    }
}