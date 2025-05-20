package com.example.myapplication5

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    private lateinit var countDownText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cancelButton: Button = findViewById(R.id.cancelButton)

        cancelButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
            finish()
        }

        countDownText = findViewById(R.id.countDownText)

        // 카운트다운 시작 (10초)
        startCountDown(10)
    }

    private fun startCountDown(seconds: Int) {
        object : CountDownTimer((seconds * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                countDownText.text = "신고(${secondsLeft}s)"
            }

            override fun onFinish() {
                // 타이머가 끝난 후 전화 화면을 여는 부분
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:112") // 전화번호

                // CALL_PHONE 권한이 있는지 확인
                if (ActivityCompat.checkSelfPermission(
                        this@MainActivity,
                        Manifest.permission.CALL_PHONE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    startActivity(callIntent) // 전화 걸기
                } else {
                    // 권한 요청
                    requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL_PERMISSION)
                }

                // 만약 권한이 없는 경우, 전화 걸기 화면을 열 수 있도록 ACTION_DIAL 사용
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:112") // 전화번호
                startActivity(dialIntent)  // 전화 걸기 화면 열기
            }
        }.start()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 허용 시 전화 걸기
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:112") // 전화번호
                startActivity(callIntent)
            } else {
                // 권한 거부 시 사용자에게 안내
                Toast.makeText(this, "전화 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val REQUEST_CALL_PERMISSION = 1
    }
}
