package com.example.teamgo

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import net.flow9.thisisKotlin.firebase.R

class TodoList : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb : SQLiteDatabase

    lateinit var pjname: TextView
    lateinit var pjdate: TextView
    lateinit var back: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        pjname = findViewById(R.id.project_name3)
        pjdate = findViewById(R.id.date1)
        back = findViewById(R.id.back)

        back.setOnClickListener{
            val intent = Intent(this,TodayProject::class.java)
            startActivity(intent)
        }

        dbManager = DBManager(this,"projectlistDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        //val intent = intent
        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM projectlist;",null)

        if(cursor.moveToNext()){
            var str_name: String = intent.getStringExtra("intent_PJ_name").toString()
            var str_date: String = intent.getStringExtra("intent_PJ_date").toString()

            pjname = findViewById(R.id.project_name3)
            pjname.text = str_name
            pjname.setTextColor(Color.BLACK)

            pjdate = findViewById(R.id.allday)
            pjdate.text = str_date
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()
    }
}