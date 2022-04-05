package com.example.lab7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        //get intent
        val type = intent.getStringExtra("type")
        val resourceID = intent.getIntExtra("resourceID", -1)

        //update view
        val catImage: ImageView = findViewById(R.id.imageViewCat)
        catImage.setImageResource(resourceID)
        val catType: TextView = findViewById(R.id.textViewName)
        catType.text = type
    }
}