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

class Login : AppCompatActivity() {
    lateinit var putID: EditText
    lateinit var putEmail: EditText
    lateinit var putPassword: EditText
    lateinit var check: CheckBox
    lateinit var loginbtn: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        putID = findViewById(R.id.InputID)
        putEmail = findViewById(R.id.InputID)
        putPassword = findViewById(R.id.InputPassword)
        check = findViewById(R.id.AuthLogin_check)
        loginbtn = findViewById(R.id.Login_btn)

        // 입력한 정보 불러오기
        val pref = getSharedPreferences("LoginInfo", 0)
        val savedID = pref.getString("ID","").toString()
        val savedEmail = pref.getString("Email","").toString()
        val savedPassword = pref.getString("Password","").toString()
        val savedC =pref.getBoolean("BOX",false)

        // 이전에 자동로그인 박스를 체크했을 경우
        if(savedC == true){
            val intent: Intent = Intent(applicationContext, TodayProject::class.java)
            intent.putExtra("UserID",savedID)
            startActivity(intent)
            finish()
            // 토스트 메시지 시작 시간 조정
            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(this, "자동 로그인", Toast.LENGTH_SHORT).show()
            },200)
        }

        loginbtn.setOnClickListener {

            var ID = putID.text.toString()
            var Email = putEmail.text.toString()
            var Password = putPassword.text.toString()
            var CheckBox = check.isChecked()

            // 처음 입렵된 정보 => 회원가입
            if( savedID != ID && savedEmail != Email && savedPassword != Password && savedC == false ) {
                saveDate(ID,Email,Password,CheckBox)
                val intent: Intent = Intent(applicationContext, TodayProject::class.java)
                intent.putExtra("UserID",savedID)
                startActivity(intent)
                Toast.makeText(this, "회원가입 완료", Toast.LENGTH_SHORT).show() }
            // 이미 회원가입된 정보 => 로그인
            else if (savedID == ID && savedEmail == Email && savedPassword == Password && savedC ==false){
                saveDate(ID,Email,Password,CheckBox)
                val intent: Intent = Intent(applicationContext, TodayProject::class.java)
                intent.putExtra("UserID",savedID)
                startActivity(intent)
                Toast.makeText(this, "로그인 완료", Toast.LENGTH_SHORT).show() }
            // 잘못된 정보 => 로그인 실패
            else {
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show() }
        }
    }
    // SharedPreferences 를 이용한 데이터 저장
    fun saveDate( loginID: String, email: String, password: String ,checkBox: Boolean ){
        val pref =getSharedPreferences("LoginInfo", MODE_PRIVATE) // shared key 설정
        val edit = pref.edit() // 수정모드

        edit.putString("ID", loginID)
        edit.putString("Email", email)
        edit.putString("Password", password)
        edit.putBoolean("BOX", checkBox)     // 값 설정

        edit.apply() // 적용하기
    }
}
