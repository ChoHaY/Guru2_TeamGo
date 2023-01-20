package com.example.teamgo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import net.flow9.thisisKotlin.firebase.R

class TodayProject : AppCompatActivity() {

    lateinit var Email: TextView
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_project)

        auth = FirebaseAuth.getInstance()

        var addProject : ImageButton = findViewById(R.id.add_list)

        addProject.setOnClickListener{
            val intent = Intent(this, AddProject_name::class.java)
            startActivity(intent)
        }
    }
}
