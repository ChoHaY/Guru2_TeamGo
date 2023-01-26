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
import androidx.core.view.marginBottom
import androidx.core.view.setPadding
import com.google.firebase.auth.FirebaseAuth
import net.flow9.thisisKotlin.firebase.R

class TodayProject : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var Nolist: TextView
    lateinit var addProject : ImageButton

    lateinit var dbManager: DBManager
    lateinit var sqlitedb : SQLiteDatabase
    lateinit var layout: LinearLayout

    @RequiresApi(Build.VERSION_CODES.Q)
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

        var cursor : Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM projectlist;",null)
        var num1: Int = 0
        var num2: Int = 0
        while (cursor.moveToNext()){
            var str_name = cursor.getString(cursor.getColumnIndex("PJName")).toString()
            var str_date = cursor.getString(cursor.getColumnIndex("PJDate")).toString()
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
            detail.setPadding(0,10,0,0)
            detail.foregroundGravity
            detail.setOnClickListener{
                val intent = Intent(this,TodoList::class.java)
                intent.putExtra("intent_PJ_name",str_name)
                intent.putExtra("intent_PJ_date",str_date)
                startActivity(intent)
            }
            layout_semi.addView(detail)

            layout_item.addView(layout_semi)

            var pjDate: TextView = TextView(this)
            pjDate.text = str_date
            pjDate.textSize = 15f
            pjDate.setPadding(0,0,0,25)
            layout_item.addView(pjDate)

            Nolist.visibility = View.GONE
            layout.addView(layout_item)
            num1++
            num2++
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()

        addProject.setOnClickListener{
            val intent = Intent(this, AddProject_name::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.invitation_btn).setOnClickListener {

            val message = "팀 계획을 짜고 싶다고?? 당신을 '팀 고'로 초대합니다! ~\n\nhttps://naver.com"
            share(message)
        }
    }
    @SuppressLint("QueryPermissionsNeeded")
    fun share(content: String) {
        val intent = Intent(Intent.ACTION_SEND) // 공유하는 인텐트 생성
            .apply {
                type = "text/plain" // 데이터 타입 설정
                putExtra(Intent.EXTRA_TEXT, content) // 보낼 내용 설정
            }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(intent, "초대 메세지 보내기"))
        } else {
            Toast.makeText(this, "초대 메세지를 전송할 수 없습니다", Toast.LENGTH_LONG).show()
        }
    }
}



