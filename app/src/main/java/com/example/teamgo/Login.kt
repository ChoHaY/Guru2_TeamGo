package com.example.teamgo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.CheckBox
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
    lateinit var putID: EditText
    lateinit var check: CheckBox

        @SuppressLint("MissingInflatedId")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.login)

            auth = FirebaseAuth.getInstance()

            putEmail = findViewById(R.id.InputID)
            putPassword = findViewById(R.id.InputPassword)
            Loginbtn = findViewById(R.id.Login_btn)
            putID = findViewById(R.id.InputID)
            check = findViewById(R.id.AuthLogin_check)

            val pref = getSharedPreferences("LoginInfo", 0)
            val savedID = pref.getString("ID", "").toString()
            val savedC = pref.getBoolean("BOX", false)



            Loginbtn.setOnClickListener {
                var ID = putID.text.toString()
                var CheckBox = check.isChecked()
                var Email = putEmail.text.toString()
                var Password = putPassword.text.toString()

                auth.createUserWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            if (auth.currentUser != null) {
                                saveDate(ID, CheckBox)
                                Toast.makeText(this, "회원가입 완료", Toast.LENGTH_SHORT).show()
                                var intent = Intent(this, TodayProject::class.java)
                                intent.putExtra("UserID", savedID)
                                startActivity(intent)
                            }
                        } else if (result.exception?.message.isNullOrEmpty()) {
                            Toast.makeText(this, "오류가 발생", Toast.LENGTH_SHORT).show()
                        } else {
                            saveDate(ID, CheckBox)
                            Toast.makeText(this, "로그인 완료", Toast.LENGTH_SHORT).show()
                            login(Email.toString(), Password.toString())
                            var intent = Intent(this, TodayProject::class.java)
                            intent.putExtra("UserID", savedID)
                            startActivity(intent)
                        }

                    }
                }


                if (savedC == true) {
                    val intent: Intent = Intent(applicationContext, TodayProject::class.java)
                    intent.putExtra("UserID", savedID)
                    startActivity(intent)
                    finish()
                    Handler(Looper.getMainLooper()).postDelayed({
                        Toast.makeText(this, "자동 로그인", Toast.LENGTH_SHORT).show()
                    },500)
                }


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



    fun saveDate( loginID :String, checkBox:Boolean ){
        val pref =getSharedPreferences("LoginInfo", MODE_PRIVATE) //shared key 설정
        val edit = pref.edit() // 수정모드

        edit.putString("ID", loginID) // 값 넣기
        edit.putBoolean("BOX", checkBox)

        edit.apply() // 적용하기
    }
}
