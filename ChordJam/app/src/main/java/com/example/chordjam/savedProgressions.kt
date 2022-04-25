package com.example.chordjam

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chordjam.MainActivity.Companion.SELECTED_PROGRESSION_INDEX
import com.example.chordjam.data.ProgItem
import com.example.chordjam.data.ProgItemList

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

        val adapter = savedProgressionsAdapter(progItemList) { prog: ProgItem, index: Int ->
            progressionSelected(
                prog,
                index
            )
        }

        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    private fun progressionSelected(prog: ProgItem, index: Int){
        if(prog.chords.isNullOrEmpty() || prog.chords[0].isNullOrBlank()){
            val noProgSavedBuilder = AlertDialog.Builder(this)
            noProgSavedBuilder.apply {
                setNegativeButton(R.string.okayResponse
                ) { _, _ ->
                //Nothing else to do. Display prompt and user goes back to previous screen
                }
            }
            noProgSavedBuilder.setMessage(R.string.noProgSavedPrompt)
                .setTitle(R.string.noProgSavedTitle)
            val noProgSavedDialog = noProgSavedBuilder.create()
            noProgSavedDialog.show()
        }else{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(SELECTED_PROGRESSION_INDEX,index)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }

}