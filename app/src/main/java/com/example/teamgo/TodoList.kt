package com.example.teamgo

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginBottom
import androidx.core.view.setPadding
import net.flow9.thisisKotlin.firebase.R

class TodoList() : AppCompatActivity(){

    lateinit var dbManager: DBManager
    lateinit var sqlitedb : SQLiteDatabase

    lateinit var pjname: TextView
    lateinit var pjdate: TextView
    lateinit var back: ImageButton
    lateinit var delete: ImageButton
    lateinit var pjmem: TextView

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        //pjname = findViewById(R.id.project_name3)
        back = findViewById(R.id.back)
        back.setOnClickListener{
            val intent = Intent(this,TodayProject::class.java)
            startActivity(intent)
        }

        dbManager = DBManager(this,"projectlist_DB", null, 1)
        sqlitedb = dbManager.readableDatabase

        val intent = intent
        var str_name: String
        var str_date: String
        var str_days: String
        var layout: LinearLayout

        str_name = intent.getStringExtra("intent_PJ_name").toString()
        str_days = intent.getStringExtra("date_interval").toString()
        var num =  str_days.toInt()+1

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM projectlist_ WHERE PJname ='"+str_name+"';",null)

        layout = findViewById(R.id.personnel)

        if(cursor.moveToNext()){
            str_date = intent.getStringExtra("intent_PJ_date").toString()

            pjname = findViewById(R.id.project_name3)
            pjname.text = str_name
            pjname.setTextColor(Color.BLACK)

            pjdate = findViewById(R.id.allday)
            pjdate.text = str_date

            pjmem =findViewById(R.id.Member)



            for(day in 1 until num){
                var layout_item: LinearLayout = LinearLayout(this)
                var layout_semi: LinearLayout = LinearLayout(this)

                layout_item.orientation = LinearLayout.VERTICAL
                layout_semi.orientation = LinearLayout.HORIZONTAL

                var pjday: TextView = TextView(this)
                pjday.text = "Day$day"
                pjday.textSize = 20f
                pjday.width = 280
                pjday.setPadding(0,0,0,5)
                pjday.setTextColor(Color.BLACK)
                layout_semi.addView(pjday)

                var add: ImageButton = ImageButton(this)
                add.setImageResource(R.drawable.vec_add_schedule)
                add.setPadding(0,5,0,0)
                add.setBackgroundColor(Color.WHITE)
                layout_semi.addView(add)

                layout_item.addView(layout_semi)

                var pjdo:TextView=TextView(this)
                pjdo.text = "해야할 일"
                pjdo.setBackgroundColor(Color.LTGRAY)
                pjdo.setPadding(0,10,0,10)
                layout_item.addView(pjdo)
                layout_item.setPadding(0,0,0,40)

                layout.addView(layout_item)
            }
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()

        //////////////////////////////////////////////

        delete = findViewById(R.id.DeletePj_btn)
        delete.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("프로젝트 삭제")
                .setMessage("프로젝트를 삭제하시겠습니까?")
                .setPositiveButton("예",DialogInterface.OnClickListener{dialog, which->
                    dbManager = DBManager(this,"projectlist_DB", null, 1)
                    sqlitedb = dbManager.readableDatabase
                    sqlitedb.execSQL("DELETE FROM projectlist_ WHERE PJname ='"+str_name+"';")
                    sqlitedb.close()
                    dbManager.close()

                    val intent = Intent(this,TodayProject::class.java)
                    startActivity(intent)
                })
                .setNegativeButton("아니오",DialogInterface.OnClickListener{dialog, which->
                    builder.setOnDismissListener(object : DialogInterface.OnDismissListener {
                        override fun onDismiss(arg0: DialogInterface) {}
                    })
                })
                .setCancelable(false)
            builder.show()
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
