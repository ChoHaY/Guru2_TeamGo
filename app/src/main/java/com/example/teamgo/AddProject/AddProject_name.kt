package com.example.teamgo.AddProject

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.teamgo.R
import com.example.teamgo.TodayProject

class AddProject_name : AppCompatActivity() {

    lateinit var backbtn : ImageButton
    lateinit var regname : EditText
    lateinit var nextbtn : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_project_name)

        backbtn = findViewById(R.id.Back_btn)
        regname = findViewById(R.id.InputName)
        nextbtn = findViewById(R.id.Next_btn)
        nextbtn.isEnabled = false

        var userID : String = intent.getStringExtra("UserID").toString()

        // 뒤로가기 버튼
        backbtn.setOnClickListener {
            val intent = Intent(this, TodayProject::class.java)
            intent.putExtra("UserID",userID)
            startActivity(intent)
        }

        // 프로젝트 이름을 작성해야 '다음' 버튼 활성화
        regname.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(regname.length() > 0) {
                    nextbtn.isEnabled= true
                    nextbtn.setBackgroundColor(Color.parseColor("#215FFF"))
                } else {
                    nextbtn.isEnabled= false
                    nextbtn.setBackgroundColor(Color.LTGRAY)
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        // 프로젝트 이름 저장 후 기간 설정으로 이동
        nextbtn.setOnClickListener {
            val intent = Intent(this, AddProject_date::class.java)
            intent.putExtra("UserID",userID)
            intent.putExtra("name",regname.text.toString())
            startActivity(intent)
        }
    }
}