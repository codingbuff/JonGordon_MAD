package com.example.turkeyfarmer

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun carveDaTurkey(view: android.view.View) {
        val realityCheck = findViewById<TextView>(R.id.turkeyKiller)
        val editName = findViewById<EditText>(R.id.editTextName)
        val name = editName.text
        realityCheck.setText("You killed " + name + ", you monster!")
        val turkeyImg = findViewById<ImageView>(R.id.liveTurkey)
        turkeyImg.setImageResource(R.drawable.turkeymeat)
    }
}