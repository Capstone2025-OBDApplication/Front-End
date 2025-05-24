package com.example.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.settings.SettingsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // FrameLayout만 포함된 레이아웃

        // SettingsFragment를 메인에 삽입
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SettingsFragment())
            .commit()
    }
}
