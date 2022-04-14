package com.example.lab8

import MyListAdapter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        searchWord = lookUpView.text.toString()

        submitBtn.setOnClickListener {
            searchWord = lookUpView.text.toString()
            Log.d("setOnClickListener","searchWord: ${searchWord}")
            val loader = JSONdata()
            loader.loadJSON(this.applicationContext,viewModel,searchWord)
            val defList = viewModel.defList
            println(defList)
        }

        //get the recycler view
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        //set a layout manager on the recycler view
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //define an adapter
        val adapter = MyListAdapter(viewModel)

        //assign the adapter to the recycle view
        recyclerView.adapter = adapter

        //create the observer
        viewModel.defList.observe(this, Observer {
            adapter.update()
        })
    }
}