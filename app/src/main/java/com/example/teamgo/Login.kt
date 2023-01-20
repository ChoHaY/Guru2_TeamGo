package com.example.teamgo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import net.flow9.thisisKotlin.firebase.R
import java.util.*

class Login : AppCompatActivity() {
    lateinit var edtEmail: EditText  //이메일 적는 구간
    lateinit var edtPassword: EditText //비밀번호 적는 구간
    lateinit var LoginRegbtn: Button //버튼
    lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.InputEmail)
        edtPassword = findViewById(R.id.Inputpassword)
        LoginRegbtn = findViewById(R.id.LoginRegbtn)

        LoginRegbtn.setOnClickListener {
            var Email = edtEmail.text.toString()
            var Password = edtPassword.text.toString()
            auth.createUserWithEmailAndPassword(Email, Password) // 회원 가입
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        if (auth.currentUser != null) {
                            var intent = Intent(this, TodayProject::class.java)
                            startActivity(intent)
                        }
                    } else if (result.exception?.message.isNullOrEmpty()) {
                        Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        login(Email.toString(), Password.toString())
                    }
                }
        }
            auth = FirebaseAuth.getInstance()
            var currentUser = auth?.currentUser
            //이미 로그인한적이 있는지 확인합니다.
            if (currentUser == null) {
                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        val intent: Intent = Intent(applicationContext, Login::class.java)
                        startActivity(intent)
                        finish()
                    }
                }, 2000)
            } else {
                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        val intent: Intent = Intent(applicationContext, TodayProject::class.java)
                        startActivity(intent)
                        finish()
                    }
                }, 2000)
            }
        }
        fun login(Email: String, Password: String) {
            auth.signInWithEmailAndPassword(Email, Password) // 로그인
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        var intent = Intent(
                            this,
                            TodayProject::class.java
                        ) //MainActivity가 아닌 '오늘의 일정'으로 넘어가는 Activity이름으로 바꾸어야 함
                        startActivity(intent)
                    }
                } }
    }
