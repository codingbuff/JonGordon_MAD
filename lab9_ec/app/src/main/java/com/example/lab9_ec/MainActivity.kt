package com.example.lab9_ec

import android.content.*
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codelab.android.datastore.Todo
import com.example.lab9_ec.data.TodoRepo
import com.example.lab9_ec.data.TodoSerializer
import com.example.lab9_ec.data.TodoViewModel
import com.example.lab9_ec.data.TodoViewModelFactory
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text

private val DATA_STORE_FILE_NAME = "todo.pb"

val Context.dataStore: DataStore<Todo> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = TodoSerializer
)
class MainActivity : AppCompatActivity() {
    lateinit var titleInput: TextView
    lateinit var descInput: TextView
    lateinit var addBtn: Button
    lateinit var completeBtn: Button
    lateinit var titleAdded: TextView
    lateinit var descAdded: TextView
    private lateinit var viewModel:TodoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        titleInput = findViewById(R.id.titleInput)
        descInput = findViewById(R.id.descriptionInput)
        addBtn = findViewById(R.id.addBtn)
        titleAdded = findViewById(R.id.titleAdded)
        descAdded = findViewById(R.id.desAdded)
        completeBtn = findViewById(R.id.completeButton)
        viewModel = ViewModelProvider(this,
        TodoViewModelFactory(TodoRepo(dataStore)))[TodoViewModel::class.java]

        viewModel.toDo.observe(this, Observer { todo ->
            titleAdded.text = todo.title
            descAdded.text = todo.description
            if (todo.title != ""){
                completeBtn.visibility = View.VISIBLE
            }
            else{
                completeBtn.visibility = View.INVISIBLE
            }
        })

    }

    fun addTodo(view: View) {
        val title = titleInput.text.toString()
        val desc = descInput.text.toString()
        viewModel.editTodo(title,desc)
        Snackbar.make(view,R.string.todoSaved,Snackbar.LENGTH_LONG).show()
    }

    fun markComplete(view: View) {
        viewModel.editTodo("","")
        Snackbar.make(view,R.string.completed_task,Snackbar.LENGTH_LONG).show()

    }

}