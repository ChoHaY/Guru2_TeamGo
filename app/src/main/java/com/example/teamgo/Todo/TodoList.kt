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
import androidx.core.view.marginBottom
import androidx.core.view.setPadding
import com.example.teamgo.BottomSheet
import com.example.teamgo.DBManager
import com.example.teamgo.TodayProject
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
        setContentView(R.layout.todo_list)

        //pjname = findViewById(R.id.project_name3)
        back = findViewById(R.id.back)
        back.setOnClickListener{
            val intent = Intent(this, TodayProject::class.java)
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

            pjdate = findViewById(R.id.allday)
            pjdate.text = str_date

            pjname = findViewById(R.id.project_name3)
            pjname.text = str_name
            pjname.setTextColor(Color.BLACK)

            pjmem =findViewById(R.id.Member)

            for(day in 1 until num){
                var layout_item: LinearLayout = LinearLayout(this)
                var layout_1: LinearLayout = LinearLayout(this)
                var layout_2: LinearLayout = LinearLayout(this)

                layout_item.orientation = LinearLayout.VERTICAL
                layout_1.orientation = LinearLayout.HORIZONTAL
                layout_2.orientation = LinearLayout.HORIZONTAL

                var pjday: TextView = TextView(this)
                pjday.text = "Day$day"
                pjday.textSize = 20f
                pjday.width = 280
                pjday.setPadding(0,0,0,5)
                pjday.setTextColor(Color.BLACK)
                layout_1.addView(pjday)

                var pjdo:TextView=TextView(this)
                pjdo.text = "해야할 일"
                pjdo.width = 260
                pjdo.setBackgroundResource(R.drawable.list_stroke)
                pjdo.setPadding(8,15,0,15)
                layout_item.setPadding(0,0,0,40)
                layout_2.addView(pjdo)

                pjdo.setOnClickListener{
                    val bottomSheet = BottomSheet(this)
                    bottomSheet.show(supportFragmentManager, bottomSheet.tag)
                }

                var add: ImageButton = ImageButton(this)
                add.setImageResource(R.drawable.vec_add_schedule)
                add.setPadding(10,5,0,0)
                add.setBackgroundColor(Color.WHITE)
                add.setOnClickListener{
                    val dialog = TodoMemo(this)
                    dialog.showDialog()
                    dialog.setOnClickListener(object : TodoMemo.OnDialogClickListener {
                        override fun onClicked(name: String) {
                            pjdo.text= name
                        }
                    })
                }
                layout_1.addView(add)

                var pjmanager: TextView = TextView(this)
                pjmanager.text = "담당자"
                pjmanager.setBackgroundColor(Color.parseColor("#FFDFDFDF"))
                pjmanager.setPadding(5,15,5,15)
                pjmanager.setTextColor(Color.BLUE)
                layout_2.addView(pjmanager)

                //담당자 클릭했을 때 담당자 작성하는 다이얼로그 나오도록
                pjmanager.setOnClickListener{
                    val dialog = WriteManager(this)
                    dialog.showDialog()
                    dialog.setOnClickListener(object : WriteManager.OnDialogClickListener {
                        override fun onClicked(name: String) {
                            pjmanager.text= name
                        }
                    })
                }
                ////////
                layout_2.setPadding(0,0,0,20)
                layout.addView(layout_1)
                layout.addView(layout_2)
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

                    val intent = Intent(this, TodayProject::class.java)
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

            val message = "팀 계획을 짜고 싶다고??\n당신을 '팀고(TeamGo)'로 초대합니다!\n\nhttps://naver.com"
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
