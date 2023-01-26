package com.example.teamgo

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import net.flow9.thisisKotlin.firebase.R
class TodoList() : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var pjname: TextView
    lateinit var pjdate: TextView
    lateinit var back: ImageButton
    lateinit var delete: ImageButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        pjname = findViewById(R.id.project_name3)
        pjdate = findViewById(R.id.date1)
        back = findViewById(R.id.back)

        back.setOnClickListener {
            val intent = Intent(this, TodayProject::class.java)
            startActivity(intent)
        }

        dbManager = DBManager(this, "projectlistDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        val intent = intent
        var str_name: String
        var str_date: String

        str_name = intent.getStringExtra("intent_PJ_name").toString()

        var cursor: Cursor
        cursor =
            sqlitedb.rawQuery("SELECT * FROM projectlist WHERE PJName ='" + str_name + "';", null)

        if (cursor.moveToNext()) {
            str_date = intent.getStringExtra("intent_PJ_date").toString()

            pjname = findViewById(R.id.project_name3)
            pjname.text = str_name
            pjname.setTextColor(Color.BLACK)

            pjdate = findViewById(R.id.allday)
            pjdate.text = str_date
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()

        //////////////////////////////////////////////

        delete = findViewById(R.id.DeletePj_btn)
        delete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("프로젝트 삭제")
                .setMessage("프로젝트를 삭제하시겠습니까?")
                .setPositiveButton("예", DialogInterface.OnClickListener { dialog, which ->
                    dbManager = DBManager(this, "projectlistDB", null, 1)
                    sqlitedb = dbManager.readableDatabase
                    sqlitedb.execSQL("DELETE FROM projectlist WHERE PJName ='" + str_name + "';")
                    sqlitedb.close()
                    dbManager.close()

                    val intent = Intent(this, TodayProject::class.java)
                    startActivity(intent)
                })
                .setNegativeButton("아니오", DialogInterface.OnClickListener { dialog, which ->
                    builder.setOnDismissListener(object : DialogInterface.OnDismissListener {
                        override fun onDismiss(arg0: DialogInterface) {}
                    })
                })
                .setCancelable(false)
            builder.show()
        }
    }
}




