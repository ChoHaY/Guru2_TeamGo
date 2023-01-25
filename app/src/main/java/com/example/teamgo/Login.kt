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
    lateinit var putEmail: EditText
    lateinit var putPassword: EditText
    lateinit var Loginbtn: Button
    lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        putEmail = findViewById(R.id.InputEmail)
        putPassword = findViewById(R.id.InputPassword)
        Loginbtn = findViewById(R.id.Login_btn)

        Loginbtn.setOnClickListener {
            var Email = putEmail.text.toString()
            var Password = putPassword.text.toString()

            auth.createUserWithEmailAndPassword(Email, Password)
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
            //auth = FirebaseAuth.getInstance()
            /*var currentUser = auth?.currentUser //이미 로그인한적이 있는지 확인합니다.

            if (currentUser == null) {
                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        val intent: Intent = Intent(applicationContext, Login::class.java)
                        startActivity(intent)
                        finish()
                    }
                }, 1500)
            } else {
                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        val intent: Intent = Intent(applicationContext, TodayProject::class.java)
                        startActivity(intent)
                        finish()
                    }
                }, 1500)
            }*/
    }
    fun login(Email: String, Password: String) {
        auth.signInWithEmailAndPassword(Email, Password) // 로그인
             .addOnCompleteListener { result ->
              if (result.isSuccessful) {
                  Toast.makeText(this, "로그인 완료", Toast.LENGTH_SHORT).show()
                  var intent = Intent(this, TodayProject::class.java)
                  startActivity(intent)
              }
        }
    }
}
