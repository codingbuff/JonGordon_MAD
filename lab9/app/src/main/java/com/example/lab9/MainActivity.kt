package com.example.lab9


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lab9.data.DataStoreRepo
import com.example.lab9.data.RandomThoughtViewModel
import com.example.lab9.data.RandomThoughtViewModelFactory
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text

private val Context.dataStore by preferencesDataStore(name = "randomThought")
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: RandomThoughtViewModel
    private lateinit var thought: TextView
    private lateinit var dateTime: TextView
    private lateinit var displayThought: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this,
            RandomThoughtViewModelFactory(DataStoreRepo(dataStore)))[RandomThoughtViewModel::class.java]
        thought = findViewById(R.id.editThought)
        dateTime = findViewById(R.id.editDateTime)
        displayThought = findViewById(R.id.savedThoughtView)
        viewModel.randomThought.observe(this, Observer { it->
            displayThought.text = it.thought + "\n" + it.datTime
        })
    }

    fun saveThought(view: View) {
        val enteredThought = thought.text.toString()
        val enteredDateTime = dateTime.text.toString()
        viewModel.saveThought(enteredThought,enteredDateTime)
        Snackbar.make(view,R.string.thoughtSaved,Snackbar.LENGTH_LONG).show()
    }
}