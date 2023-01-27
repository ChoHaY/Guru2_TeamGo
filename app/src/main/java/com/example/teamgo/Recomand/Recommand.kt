package com.example.teamgo.Recomand

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import net.flow9.thisisKotlin.firebase.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamgo.TodayProject

class Recommand : AppCompatActivity() {
    private lateinit var personList: List<Person>
    private lateinit var adapter: ExpandableAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recommand)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_list)

        personList = ArrayList()
        personList = loadData()

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ExpandableAdapter(personList)
        recyclerView.adapter = adapter

        ///////////////////////////////////////////////////////////////////////
        var gohome: ImageButton = findViewById(R.id.Home_btn)
        gohome.setOnClickListener {
            val intent = Intent(this, TodayProject::class.java)
            startActivity(intent)
        }
        var recommand: ImageButton = findViewById(R.id.Recommand_btn)
        recommand.isEnabled = false
    }
    private fun loadData(): List<Person> {
        val people = ArrayList<Person>()
        val persons = resources.getStringArray(R.array.pj_name)

        for (i in persons.indices) {
            val person = Person().apply {
                name = persons[i]
            }
            people.add(person)
        }
        return people
    }
}

