package com.example.teamgo

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.teamgo.AddProject.AddProject_name
import com.example.teamgo.Recomand.Recommand
import com.example.teamgo.Todo.TodoList
import com.google.firebase.auth.FirebaseAuth
import net.flow9.thisisKotlin.firebase.R
import net.flow9.thisisKotlin.firebase.R.drawable.line

class TodayProject : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var Nolist: TextView
    lateinit var addProject : ImageButton
    lateinit var ID : TextView

    lateinit var dbManager: DBManager
    lateinit var sqlitedb : SQLiteDatabase
    lateinit var layout: LinearLayout

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("Range", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.today_project)

        Nolist = findViewById(R.id.Nolist_tv)
        auth = FirebaseAuth.getInstance()
        addProject = findViewById(R.id.AddList_btn)
        ID = findViewById(R.id.UserName)

        var userID : String = intent.getStringExtra("UserID").toString()
        ID.setText(userID + " 님")

        dbManager = DBManager(this, "projectlist_DB", null, 1)
        sqlitedb = dbManager.readableDatabase
        layout = findViewById(R.id.personnel)

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM projectlist_;", null)
        var num1: Int = 0
        var num2: Int = 0
        while (cursor.moveToNext()) {
            var str_name = cursor.getString(cursor.getColumnIndex("PJname")).toString()
            var str_date = cursor.getString(cursor.getColumnIndex("PJdate")).toString()
            var str_mem = cursor.getString(cursor.getColumnIndex("PJgap")).toString()
            var layout_semi: LinearLayout = LinearLayout(this)
            var layout_item: LinearLayout = LinearLayout(this)

            layout_semi.orientation = LinearLayout.HORIZONTAL
            layout_semi.id = num1
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.id = num2

            var pjName: TextView = TextView(this)
            pjName.text = str_name
            pjName.textSize = 20f
            pjName.width = 270
            pjName.setTextColor(Color.BLACK)
            layout_semi.addView(pjName)

            var detail: ImageButton = ImageButton(this)
            detail.setImageDrawable(getDrawable(R.drawable.vec_forward))
            detail.setBackgroundColor(Color.WHITE)
            detail.setPadding(0, 10, 0, 0)
            detail.foregroundGravity
            detail.setOnClickListener {
                val intent = Intent(this, TodoList::class.java)
                intent.putExtra("intent_PJ_name", str_name)
                intent.putExtra("intent_PJ_date", str_date)
                intent.putExtra("date_interval",str_mem)
                intent.putExtra("UserID",userID)
                startActivity(intent)
            }
            layout_semi.addView(detail)

            layout_item.addView(layout_semi)

            var pjDate: TextView = TextView(this)
            pjDate.text = str_date
            pjDate.textSize = 15f
            pjDate.setPadding(0, 0, 0, 30)

            pjDate.setTextColor(Color.BLACK)
            layout_item.addView(pjDate)
            pjDate.setBackgroundResource(R.drawable.line)
            Nolist.visibility = View.GONE
            layout.addView(layout_item)
            num1++
            num2++
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()

        addProject.setOnClickListener {
            val intent = Intent(this, AddProject_name::class.java)
            intent.putExtra("UserID",userID)
            startActivity(intent)
        }

        var gohome: ImageButton = findViewById(R.id.Home_btn)
        gohome.isEnabled=false

        var recommand: ImageButton = findViewById(R.id.Recommand_btn)
        recommand.setOnClickListener{

            startActivity(intent)
            overridePendingTransition(0, 0)
            val intent = Intent(this, Recommand::class.java)
            intent.putExtra("UserID",userID)
            startActivity(intent)
        }
    }
}



