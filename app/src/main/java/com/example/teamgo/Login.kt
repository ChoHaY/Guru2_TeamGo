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
        val savedID = pref.getString("ID","").toString()
        val savedEmail = pref.getString("Email","").toString()
        val savedPassword = pref.getString("Password","").toString()
        val savedC =pref.getBoolean("BOX",false)

        if(savedC == true){
            val intent: Intent = Intent(applicationContext, TodayProject::class.java)
            intent.putExtra("UserID",savedID)
            startActivity(intent)
            finish()
           Handler(Looper.getMainLooper()).postDelayed({
               Toast.makeText(this, "자동 로그인", Toast.LENGTH_SHORT).show()
           },500)
        }

        Loginbtn.setOnClickListener {

            var ID = putID.text.toString()
            var Email = putEmail.text.toString()
            var Password = putPassword.text.toString()
            var CheckBox = check.isChecked()

            if( savedID != ID && savedEmail != Email && savedPassword != Password && savedC == false ) {
                saveDate(ID,Email,Password,CheckBox)
                val intent: Intent = Intent(applicationContext, TodayProject::class.java)
                intent.putExtra("UserID",savedID)
                startActivity(intent)
                Toast.makeText(this, "회원가입 완료", Toast.LENGTH_SHORT).show()

            } else if (savedID == ID && savedEmail == Email && savedPassword == Password && savedC == false){
                saveDate(ID,Email,Password,CheckBox)
                val intent: Intent = Intent(applicationContext, TodayProject::class.java)
                intent.putExtra("UserID",savedID)
                startActivity(intent)
                Toast.makeText(this, "로그인 완료", Toast.LENGTH_SHORT).show()

            } else /*if (savedID != ID || savedEmail != Email || savedPassword != Password ) */{
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun saveDate( loginID: String, email: String, password: String ,checkBox: Boolean ){
        val pref =getSharedPreferences("LoginInfo", MODE_PRIVATE) //shared key 설정
        val edit = pref.edit() // 수정모드

        edit.putString("ID", loginID)
        edit.putString("Email", email)
        edit.putString("Password", password)
        edit.putBoolean("BOX", checkBox)

        edit.apply() // 적용하기
    }
}
