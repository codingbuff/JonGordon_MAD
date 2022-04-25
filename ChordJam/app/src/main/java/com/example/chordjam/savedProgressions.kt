package com.example.chordjam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class savedProgressions : AppCompatActivity() {

    companion object{
        val PROG_LIST = "progression_list_passed"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_progressions)

        val progItemList = intent.getSerializableExtra(PROG_LIST) as ProgItemList
        for (item in progItemList.getItems()){
            println("progItem name: ${item.name}")
            println("progItem chords: ${item.chords}")
        }
    }
}