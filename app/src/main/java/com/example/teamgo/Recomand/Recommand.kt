package com.example.teamgo.Recomand

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import net.flow9.thisisKotlin.firebase.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamgo.TodayProject
import kotlin.collections.ArrayList

class Recommand : AppCompatActivity() {
    private lateinit var personList: List<category>
    private lateinit var adapter: CategoryAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recommand)

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

