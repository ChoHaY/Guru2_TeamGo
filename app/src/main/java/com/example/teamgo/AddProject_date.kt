package com.example.teamgo

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.datepicker.MaterialDatePicker
import net.flow9.thisisKotlin.firebase.R

class AddProject_date : AppCompatActivity() {

    lateinit var Backbtn: ImageButton
    lateinit var DateView: EditText
    lateinit var SelectDate: ImageButton
    lateinit var Nextbtn : Button
    lateinit var getName : String

    lateinit var dbManager: DBManager
    lateinit var sqlitedb : SQLiteDatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_project_date)

        Backbtn = findViewById(R.id.Back_btn2)
        DateView = findViewById(R.id.InputDate)
        SelectDate = findViewById(R.id.SelectDate_btn)


        Backbtn.setOnClickListener {
            val intent = Intent(this,AddProject_name::class.java)
            startActivity(intent)
        }

        SelectDate.setOnClickListener{
            val datePicker = MaterialDatePicker.Builder.dateRangePicker().setTitleText("팀플 기간을 골라주세요").build()
            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                DateView.setText(datePicker.headerText)
            }
        }

        Nextbtn = findViewById(R.id.Next_btn)
        Nextbtn.isEnabled = false

        DateView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(DateView.length() > 0) {
                    Nextbtn.isEnabled= true
                    Nextbtn.setBackgroundColor(Color.parseColor("#215FFF"))
                } else {
                    Nextbtn.isEnabled= false
                    Nextbtn.setBackgroundColor(Color.LTGRAY)
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        dbManager = DBManager(this,"projectlistDB", null, 1)

        Nextbtn.setOnClickListener {
            var str_name : String = intent.getStringExtra("name").toString()
            var str_date : String = DateView.text.toString()

            sqlitedb = dbManager.writableDatabase
            sqlitedb.execSQL("INSERT INTO projectlist VALUES('"+ str_name + "','"+str_date+"');")
            sqlitedb.close()

            val intent = Intent(this, TodayProject::class.java)
            startActivity(intent)
        }
    }
}