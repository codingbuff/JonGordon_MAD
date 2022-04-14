package com.example.lab8

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.lab8.model.WordViewModel
import com.example.lab8.util.JSONdata


class MainActivity : AppCompatActivity() {
    private val viewModel: WordViewModel by viewModels()
    lateinit var lookUpView: EditText
    lateinit var submitBtn: Button
    lateinit var searchWord: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lookUpView = findViewById<EditText>(R.id.editWord)
        submitBtn = findViewById<Button>(R.id.submitBtn)

        var searchWord = ""

        submitBtn.setOnClickListener {
            searchWord = lookUpView.text.toString()
            Log.d("setOnClickListener","searchWord: ${searchWord}")
            val loader = JSONdata()
            loader.loadJSON(this.applicationContext,viewModel,searchWord)
        }
    }



}