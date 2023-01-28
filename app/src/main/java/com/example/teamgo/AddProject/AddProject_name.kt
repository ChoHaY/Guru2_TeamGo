package com.example.teamgo.AddProject

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.teamgo.TodayProject
import net.flow9.thisisKotlin.firebase.R

class AddProject_name : AppCompatActivity() {

    lateinit var Backbtn : ImageButton
    lateinit var Nextbtn : Button
    lateinit var RegName : EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_project_name)

        Backbtn = findViewById(R.id.Back_btn)
        RegName = findViewById(R.id.InputName)
        Nextbtn = findViewById(R.id.Next_btn)
        Nextbtn.isEnabled = false

        var userID : String = intent.getStringExtra("UserID").toString()

        Backbtn.setOnClickListener {
            val intent = Intent(this, TodayProject::class.java)
            intent.putExtra("UserID",userID)
            startActivity(intent)
        }

        RegName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(RegName.length() > 0) {
                    Nextbtn.isEnabled= true
                    Nextbtn.setBackgroundColor(Color.parseColor("#215FFF"))
                } else {
                    Nextbtn.isEnabled= false
                    Nextbtn.setBackgroundColor(Color.LTGRAY)
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        Nextbtn.setOnClickListener {
            val intent = Intent(this, AddProject_date::class.java)
            intent.putExtra("UserID",userID)
            intent.putExtra("name",RegName.text.toString())
            startActivity(intent)
        }
    }
}