package com.example.teamgo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.google.android.material.datepicker.MaterialDatePicker
import net.flow9.thisisKotlin.firebase.R

class AddProject_name : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_project_name)

        var dbManager = DBManager(this,"projectlistDB", null, 1)

        var edtName: EditText = findViewById(R.id.edtName)
        var PJdate : EditText = findViewById(R.id.PJdate)
        var selectDate : ImageButton = findViewById(R.id.selectDate)
        var Reg : Button = findViewById(R.id.Reg)

        selectDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.dateRangePicker().setTitleText("팀플 기간을 골라주세요").build()
            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener{
                PJdate.setText(datePicker.headerText)
            }
        }
        Reg.setOnClickListener {
            var str_name: String = edtName.text.toString()
            var str_date: String = PJdate.text.toString()

            var sqlitedb = dbManager.writableDatabase
            sqlitedb.execSQL("INSERT INTO projectlist VALUES ('"+ str_name+"','"+str_date+"');")
            sqlitedb.close()

            val intent = Intent(this, Projectplan::class.java)
            intent.putExtra("intent_name",str_name)
            startActivity(intent)
        }
    }
}