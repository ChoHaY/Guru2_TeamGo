package com.example.teamgo

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginTop
import com.google.firebase.auth.FirebaseAuth
import net.flow9.thisisKotlin.firebase.R

class TodayProject : AppCompatActivity() {

    //lateinit var Email: TextView
    lateinit var auth: FirebaseAuth
    lateinit var Nolist: TextView
    lateinit var addProject : ImageButton
    lateinit var detail : ImageButton

    lateinit var dbManager: DBManager
    lateinit var sqlitedb : SQLiteDatabase
    lateinit var layout: LinearLayout

    @SuppressLint("Range", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_project)

        Nolist = findViewById(R.id.Nolist_tv)
        auth = FirebaseAuth.getInstance()
        addProject = findViewById(R.id.AddList_btn)

        dbManager = DBManager(this,"projectlistDB", null, 1)
        sqlitedb = dbManager.readableDatabase
        layout = findViewById(R.id.personnel)

        //detail = findViewById(R.id.Detail_btn)
        //detail.visibility = View.INVISIBLE

        var cursor : Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM projectlist;",null)
        var num: Int = 0
        while (cursor.moveToNext()){
            var str_name = cursor.getString(cursor.getColumnIndex("PJName")).toString()
            var str_date = cursor.getString(cursor.getColumnIndex("PJDate")).toString()
            var layout_item: LinearLayout = LinearLayout(this)
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.id = num

            var pjName: TextView = TextView(this)
            pjName.text = str_name
            pjName.textSize = 30f
            layout_item.addView(pjName)

            var pjDate: TextView = TextView(this)
            pjDate.text = str_date
            pjDate.textSize = 30f
            layout_item.addView(pjDate)

            Nolist.visibility = View.GONE
            layout.addView(layout_item)
            //detail.visibility = View.VISIBLE
            num++
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()

        addProject.setOnClickListener{
            val intent = Intent(this, AddProject_name::class.java)
            startActivity(intent)
        }
    }
}
