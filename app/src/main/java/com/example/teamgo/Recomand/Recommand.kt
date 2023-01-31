package com.example.teamgo.Recomand

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamgo.R
import com.example.teamgo.TodayProject
import kotlin.collections.ArrayList

class Recommand : AppCompatActivity() {
    lateinit var ID : TextView
    lateinit var recyclerview: RecyclerView
    private lateinit var pjlist: List<category> // 목록을 저장하는 공간
    private lateinit var adapter: CategoryAdapter   // 어댑터

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recommand)

        ID = findViewById(R.id.UserName)
        recyclerview = findViewById(R.id.Reco_Recycler)

        var userID : String = intent.getStringExtra("UserID").toString()
        ID.setText(userID + " 님")

        // 저장된 데이터 리스트 받기
        pjlist = ArrayList()
        pjlist = loadData()

        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this)
        adapter = CategoryAdapter(pjlist)
        recyclerview.adapter = adapter

        var gohome: ImageButton = findViewById(R.id.Home_btn)
        gohome.setOnClickListener {
            val intent = Intent(this, TodayProject::class.java)
            intent.putExtra("UserID",userID)
            startActivity(intent)
        }
        var gorecommand: ImageButton = findViewById(R.id.Recommand_btn)
        gorecommand.isEnabled = false
    }
    // 받은 데이터를 가져오는 함수
    private fun loadData(): List<category> {
        val project = ArrayList<category>()
        val projects = resources.getStringArray(R.array.pj_name)

        for (i in projects.indices) {
            val person = category().apply {
                cate = projects[i]
            }
            project.add(person)
        }
        return project
    }
}

