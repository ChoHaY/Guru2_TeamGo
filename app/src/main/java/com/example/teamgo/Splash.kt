package com.example.teamgo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import net.flow9.thisisKotlin.firebase.R

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 일정 시간 후 실행 함수
        Handler(Looper.getMainLooper()).postDelayed({

            // 2초 후 로그인 화면으로 이동
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()

        } ,2000)
    }
}