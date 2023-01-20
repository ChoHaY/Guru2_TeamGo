package com.example.teamgo

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import net.flow9.thisisKotlin.firebase.R

class ProjectList : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_list)

        var dbManager = DBManager(this,"projectlistDB", null, 1)
        var sqlitedb = dbManager.readableDatabase
        var layout : LinearLayout = findViewById(R.id.projectlist)
        var cursor : Cursor = sqlitedb.rawQuery("SELECT * FROM projectlist;", null)

        var num: Int = 0
        while(cursor.moveToNext()){
            var str_name = cursor.getString(cursor.getColumnIndex("PJName")).toString()
            var str_date = cursor.getString(cursor.getColumnIndex("PJDate")).toString()

            var layout_item: LinearLayout = LinearLayout(this)
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.id = num

            var tvName: TextView = TextView(this)
            tvName.text = str_name
            tvName.textSize = 30f
            layout_item.addView(tvName)

            var tvDate: TextView = TextView(this)
            tvDate.text = str_date
            tvDate.textSize = 30f
            layout_item.addView(tvDate)

            layout_item.setOnClickListener {
                val intent = Intent(this, Projectplan::class.java)
                intent.putExtra("intent_name",str_name)
                startActivity(intent)
            }
            layout.addView(layout_item)
            num++
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()
    }
}