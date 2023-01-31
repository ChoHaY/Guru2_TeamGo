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

class TodayProject : AppCompatActivity() {

    lateinit var id : TextView
    lateinit var nolist: TextView
    lateinit var addProject : ImageButton

    lateinit var dbManager: DBManager
    lateinit var sqlitedb : SQLiteDatabase
    lateinit var layout: LinearLayout

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("Range", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.today_project)

        id = findViewById(R.id.UserName)
        nolist = findViewById(R.id.Nolist_tv)
        addProject = findViewById(R.id.AddList_btn)
        layout = findViewById(R.id.personnel)

        // 받아 온 아이디 띄우기
        var userID : String = intent.getStringExtra("UserID").toString()
        id.setText(userID + " 님")

        // 데이터 베이스 설정
        dbManager = DBManager(this, "projectlist_DB", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM projectlist_;", null)
        var num1: Int = 0
        var num2: Int = 0

        // 추가한 프로젝트 리스트 동적 생성
        while (cursor.moveToNext()) {

            nolist.visibility = View.GONE  // 기존 텍스트 숨기기

            var str_name = cursor.getString(cursor.getColumnIndex("PJname")).toString()
            var str_date = cursor.getString(cursor.getColumnIndex("PJdate")).toString()
            var str_gap = cursor.getString(cursor.getColumnIndex("PJgap")).toString()

            var layout_semi: LinearLayout = LinearLayout(this)  // 이름과 버튼이 들어갈 레이아웃
            var layout_item: LinearLayout = LinearLayout(this)  // semi와 날짜가 들어갈 레이아웃

            layout_semi.orientation = LinearLayout.HORIZONTAL
            layout_semi.id = num1
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.id = num2

            // 프로젝트 이름
            var pjName: TextView = TextView(this)  // 프로젝트 이름
            pjName.width = 270
            pjName.text = str_name
            pjName.textSize = 20f
            pjName.setTextColor(Color.BLACK)
            layout_semi.addView(pjName)

            // 프로젝트 계획으로 이동하기 위한 버튼
            var pjDetail: ImageButton = ImageButton(this)  // TodoList로 이동
            pjDetail.setImageDrawable(getDrawable(R.drawable.vec_forward))
            pjDetail.setBackgroundColor(Color.WHITE)
            pjDetail.setPadding(0, 10, 0, 0)
            pjDetail.foregroundGravity
            pjDetail.setOnClickListener {
                val intent = Intent(this, TodoList::class.java)
                intent.putExtra("intent_PJ_name", str_name)
                intent.putExtra("intent_PJ_date", str_date)
                intent.putExtra("date_interval",str_gap)
                intent.putExtra("UserID",userID)
                startActivity(intent)
            }
            layout_semi.addView(pjDetail)

            layout_item.addView(layout_semi)

            // 프로젝트 기간
            var pjDate: TextView = TextView(this)
            pjDate.text = str_date
            pjDate.textSize = 15f
            pjDate.setTextColor(Color.BLACK)
            pjDate.setBackgroundResource(R.drawable.line)
            pjDate.setPadding(0, 0, 0, 30)

            layout_item.addView(pjDate)
            layout.addView(layout_item)
            num1++
            num2++
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()

        // 프로젝트 추가 버튼
        addProject.setOnClickListener {
            val intent = Intent(this, AddProject_name::class.java)
            intent.putExtra("UserID",userID)
            startActivity(intent)
        }

        // 홈버튼 비활성화
        var gohome: ImageButton = findViewById(R.id.Home_btn)
        gohome.isEnabled=false

        // 추천 화면으로 이동
        var gorecommand: ImageButton = findViewById(R.id.Recommand_btn)
        gorecommand.setOnClickListener{
            val intent = Intent(this, Recommand::class.java)
            intent.putExtra("UserID",userID)
            startActivity(intent)
        }
    }
}



