package com.example.chordjam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class savedProgressions : AppCompatActivity() {

    companion object{
        val PROG_LIST = "progression_list_passed"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_progressions)

        val progItemList = intent.getSerializableExtra(PROG_LIST) as ProgItemList

        //for loop used to debug
        for (item in progItemList.getItems()){
            Log.d("progItem name", "${item.name}")
            Log.d("progItem chords", "${item.chords}")
        }

        val recyclerView:RecyclerView = findViewById(R.id.progressionsRecyclerView)
        recyclerView.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))

        val adapter = savedProgressionsAdapter(progItemList)

        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }
}