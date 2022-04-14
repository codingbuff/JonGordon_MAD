package com.example.lab8

import android.content.Context
import android.graphics.Movie
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonToken
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lab8.model.Word
import com.example.lab8.model.WordViewModel
import com.example.lab8.util.JSONdata
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener


class MainActivity : AppCompatActivity() {
    private val viewModel: WordViewModel by viewModels()
    private val btn: Button
    val lookUpView = findViewById<EditText>(R.id.editWord)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val submitBtn = findViewById<Button>(R.id.submitBtn)
        val loader = JSONdata()
        var searchWord = ""
        setContentView(R.layout.activity_main)
    }


    fun getDef(view: View) {
        searchWord = lookUpView.toString()
        Log.d("click listener", "${searchWord}")
        //loader.loadJSON(this.applicationContext, viewModel, searchWord)
    }


}