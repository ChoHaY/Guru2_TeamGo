package com.example.teamgo.Todo

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.teamgo.DBManager
import com.example.teamgo.R
import com.example.teamgo.TodayProject

class TodoList() : AppCompatActivity(){

    lateinit var dbManager: DBManager
    lateinit var sqlitedb : SQLiteDatabase

    lateinit var pjname: TextView
    lateinit var pjdate: TextView
    lateinit var pjmem: TextView
    lateinit var back: ImageButton
    lateinit var delete: ImageButton

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todo_list)

        var userID : String = intent.getStringExtra("UserID").toString()

        back = findViewById(R.id.Back_btn3)
        back.setOnClickListener{
            val intent = Intent(this, TodayProject::class.java)
            intent.putExtra("UserID",userID)
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
        str_date = intent.getStringExtra("intent_PJ_date").toString()
        str_days = intent.getStringExtra("date_interval").toString()
        var num =  str_days.toInt()+1 // (마지막 날짜 - 시작 날짜) + 1

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM projectlist_ WHERE PJname ='"+str_name+"';",null)

        layout = findViewById(R.id.personnel)

        if(cursor.moveToNext()){
            pjname = findViewById(R.id.Project_Name_tv)
            pjname.text = str_name
            pjname.setTextColor(Color.BLACK)

            pjdate = findViewById(R.id.Project_date_tv)
            pjdate.text = str_date

            pjmem =findViewById(R.id.Member_tv)

            // 기간만큼 할일 작성 목록 동적으로 생성
            for(day in 1 until num){
                var layout_item: LinearLayout = LinearLayout(this)
                var layout_1: LinearLayout = LinearLayout(this)
                var layout_2: LinearLayout = LinearLayout(this)

                layout_1.orientation = LinearLayout.HORIZONTAL
                layout_2.orientation = LinearLayout.HORIZONTAL
                layout_item.orientation = LinearLayout.VERTICAL

                var pjday: TextView = TextView(this)
                pjday.width = 280
                pjday.text = " Day$day"
                pjday.textSize = 20f
                pjday.setPadding(0,0,0,5)
                pjday.setTextColor(Color.BLACK)
                layout_1.addView(pjday)

                var pjdo:TextView=TextView(this)
                pjdo.width = 250
                pjdo.text = "해야할 일"
                pjdo.setTextColor(Color.BLACK)
                pjdo.setBackgroundResource(R.drawable.list_stroke)
                pjdo.setPadding(15,15,0,15)
                layout_item.setPadding(0,0,0,40)
                layout_2.addView(pjdo)

                var add: ImageButton = ImageButton(this)
                add.setImageResource(R.drawable.vec_add_schedule)
                add.setPadding(10,5,0,0)
                add.setBackgroundColor(Color.WHITE)
                layout_1.addView(add)

                // 해야할 일 추가
                add.setOnClickListener{
                    val dialog = TodoMemo(this)
                    dialog.showDialog()
                    dialog.setOnClickListener(object : TodoMemo.OnDialogClickListener {
                        override fun onClicked(name: String) {
                            pjdo.text= name
                        }
                    })
                }

                // 해야할 일 수정
                pjdo.setOnClickListener {
                    val dialog = WriteMemo(this)
                    dialog.showDialog()
                    dialog.setOnClickListener(object : WriteMemo.OnDialogClickListener {
                        override fun onClicked(name: String) {
                            pjdo.text= name
                        }
                    })
                }

                // 관리자 설정
                var pjmanager: TextView = TextView(this)
                pjmanager.width = 60
                pjmanager.text = "담당자"
                pjmanager.setBackgroundColor(Color.parseColor("#EAEAEA"))
                pjmanager.setBackgroundResource(R.drawable.manager_back)
                pjmanager.setPadding(10,15,0,15)
                pjmanager.setTextColor(Color.BLACK)
                layout_2.addView(pjmanager)

                // 담당자 버튼 클릭시 담당자 설정
                pjmanager.setOnClickListener{
                    val dialog = WriteManager(this)
                    dialog.showDialog()
                    dialog.setOnClickListener(object : WriteManager.OnDialogClickListener {
                        override fun onClicked(name: String) {
                            pjmanager.text= name
                        }
                    })
                }
                layout_2.setPadding(0,0,0,20)
                layout.addView(layout_1)
                layout.addView(layout_2)
            }
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()

        // 프로젝트 삭제
        delete = findViewById(R.id.DeletePj_btn)
        delete.setOnClickListener{
            // 다이얼로그를 사용해 프로젝트 삭제 확인 메세 생성
            val builder = AlertDialog.Builder(this)
            builder.setTitle("프로젝트 삭제").setMessage("프로젝트를 삭제하시겠습니까?")
                .setPositiveButton("예",DialogInterface.OnClickListener{dialog,
                    which-> dbManager = DBManager(this,"projectlist_DB", null, 1)

                    // 데이터 삭제 후 메인화면을 이동
                    sqlitedb = dbManager.readableDatabase
                    sqlitedb.execSQL("DELETE FROM projectlist_ WHERE PJname ='"+str_name+"';")
                    sqlitedb.close()
                    dbManager.close()

                    val intent = Intent(this, TodayProject::class.java)
                    intent.putExtra("UserID",userID)
                    startActivity(intent)
                })
                .setNegativeButton("아니오",DialogInterface.OnClickListener{dialog,
                    which-> builder.setOnDismissListener(object : DialogInterface.OnDismissListener {
                        override fun onDismiss(arg0: DialogInterface) {}
                    })
                })
                .setCancelable(false)
            builder.show()
        }

        // 팀원 링크로 초대하기 (링크 복사)
        findViewById<ImageButton>(R.id.Invitation_btn).setOnClickListener {
            val message = "팀 계획을 짜고 싶다고??\n당신을 '팀고(TeamGo)'로 초대합니다!\n\nhttps://naver.com"
            share(message)
        }
    }
    @SuppressLint("QueryPermissionsNeeded")
    // 링크 초대 함수
    fun share(content: String) {
        val intent = Intent(Intent.ACTION_SEND) // 공유하는 인텐트 생성
            .apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, content) // 보낼 내용 설정
            }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(intent, "초대 메세지 보내기"))
        } else {
            Toast.makeText(this, "초대 메세지를 전송할 수 없습니다", Toast.LENGTH_LONG).show()
        }
    }
}
