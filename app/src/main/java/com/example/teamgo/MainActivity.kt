package com.example.teamgo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import net.flow9.thisisKotlin.firebase.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnAddGroup: ImageButton = findViewById(R.id.btnAddGroup)
        var btnPJList: ImageButton = findViewById(R.id.btnPJList)

        btnAddGroup.setOnClickListener {
            val intent = Intent(this, AddProject_name::class.java)
            startActivity(intent)
        }

        btnPJList.setOnClickListener {
            val intent = Intent(this, ProjectList::class.java)
            startActivity(intent)
        }
    }
}