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

    private lateinit var personList: List<category> //Category.kt
    private lateinit var adapter: CategoryAdapter   //CategoryAdapter.kt

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recommand)

        ID = findViewById(R.id.UserName)
        var userID : String = intent.getStringExtra("UserID").toString()
        ID.setText(userID + " 님")

        /********************************************************************/

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_list)

        personList = ArrayList()
        personList = loadData()

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CategoryAdapter(personList)
        recyclerView.adapter = adapter

        ///////////////////////////////////////////////////////////////////////

        var gohome: ImageButton = findViewById(R.id.Home_btn)
        gohome.setOnClickListener {

            val intent = Intent(this, TodayProject::class.java)
            intent.putExtra("UserID",userID)
            startActivity(intent)
        }
        var recommand: ImageButton = findViewById(R.id.Recommand_btn)
        recommand.isEnabled = false

        ////////////////////////////////////////////////////////////////////////
    }
    private fun loadData(): List<category> {
        val people = ArrayList<category>()
        val persons = resources.getStringArray(R.array.pj_name)

        for (i in persons.indices) {
            val person = category().apply {
                name = persons[i]
            }
            people.add(person)
        }
        return people
    }
}

