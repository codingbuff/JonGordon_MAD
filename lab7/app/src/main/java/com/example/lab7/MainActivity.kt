package com.example.lab7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab7.model.Cat
import com.example.lab7.sample.SampleData

class MainActivity : AppCompatActivity() {
    private var catList = SampleData.catList

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.display_meow -> Toast.makeText(this,"MEOW!",Toast.LENGTH_SHORT).show()
            R.id.display_purr -> Toast.makeText(this,"puuurrrrrrrr",Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //get the recycler view
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        Log.d("in oncrete", "made it to oncreate")
        //divider line between rows
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        //define an adapter
        val adapter = CatAdapter(catList, {item: Cat -> itemClicked(item)})

        //assign the adapter to the recycle view
        recyclerView.adapter = adapter

        //set a layout manager on the recycler view
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun itemClicked(item : Cat) {
        //create intent
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("type", item.type)
        intent.putExtra("resourceID", item.imageResourceID)

        //start activity
        startActivity(intent)
    }

}