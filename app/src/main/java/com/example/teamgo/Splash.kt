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

        // 일정 시간 지연 후 실행
        Handler(Looper.getMainLooper()).postDelayed({

            // 일정 시간 지연 후 MainActivity로 이동
            val intent = Intent(this, Login::class.java)
            startActivity((intent))

            finish()
        } ,2000)
    }
}