package com.example.teamgo.AddProject

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.teamgo.DBManager
import com.example.teamgo.R
import com.example.teamgo.Todo.TodoList
import com.google.android.material.datepicker.MaterialDatePicker

import java.util.*

class AddProject_date : AppCompatActivity() {

    lateinit var backbtn: ImageButton
    lateinit var dateview: EditText
    lateinit var selectdate: ImageButton
    lateinit var days : EditText
    lateinit var nextbtn : Button

    lateinit var dbManager: DBManager
    lateinit var sqlitedb : SQLiteDatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_project_date)

        backbtn = findViewById(R.id.Back_btn2)
        dateview = findViewById(R.id.InputDate)
        selectdate = findViewById(R.id.SelectDate_btn)
        days= findViewById(R.id.Gap_ev)

        backbtn.setOnClickListener {
            val intent = Intent(this, AddProject_name::class.java)
            startActivity(intent)
        }

        // MaterialDatePicker 이욯
        selectdate.setOnClickListener{
            // 달력 불러오기
            val datePicker = MaterialDatePicker.Builder.dateRangePicker().setTitleText("팀플 기간을 골라주세요").build()
            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {

                val calendar = Calendar.getInstance()

                // 시작날짜 받아오기
                calendar.timeInMillis = it?.first ?: 0
                val startDate = SimpleDateFormat("yyyy.MM.dd").format(calendar.time).toString()
                val first = calendar.timeInMillis

                // 마지막날짜 받아오기
                calendar.timeInMillis = it?.second ?: 0
                val endDate = SimpleDateFormat("yyyy.MM.dd").format(calendar.time).toString()
                val last = calendar.timeInMillis

                // 두 날짜 차이값 받아오기
                val gap1 = (last-first)/100000
                val gap2 = gap1/(6*6*24*1)+1
                dateview.setText("$startDate  ㅡ  $endDate")
                days.setText("$gap2")
            }
        }
        days.visibility = View.INVISIBLE

        nextbtn = findViewById(R.id.Next_btn)
        nextbtn.isEnabled = false

        // 날짜 입력받으면 다음 버튼 활성화
        dateview.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(dateview.length() > 0) {
                    nextbtn.isEnabled= true
                    nextbtn.setBackgroundColor(Color.parseColor("#215FFF"))
                } else {
                    nextbtn.isEnabled= false
                    nextbtn.setBackgroundColor(Color.LTGRAY)
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        dbManager = DBManager(this,"projectlist_DB", null, 1)
        var userID : String = intent.getStringExtra("UserID").toString()

        // 이름과 날짜 데이터를 저장해 프로젝트 생성
        nextbtn.setOnClickListener {
            var str_name : String = intent.getStringExtra("name").toString()
            var str_date : String = dateview.text.toString()
            var interval : String = days.text.toString()

            sqlitedb = dbManager.writableDatabase
            sqlitedb.execSQL("INSERT INTO projectlist_ VALUES('"+ str_name + "','"+str_date+"','"+interval+"');")
            sqlitedb.close()

            val intent = Intent(this, TodoList::class.java)
            intent.putExtra("UserID",userID)
            intent.putExtra("intent_PJ_name",str_name)
            intent.putExtra("intent_PJ_date",str_date)
            intent.putExtra("date_interval",interval)
            startActivity(intent)
        }
    }
}
