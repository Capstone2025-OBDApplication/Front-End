package com.example.myapplication5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication5.databinding.ActivitySecondBinding // Databinding import

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding // Databinding 변수 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater) // Binding 초기화
        setContentView(binding.root)


        binding.backToMainButton.setOnClickListener {
            finish()

        }
    }
}