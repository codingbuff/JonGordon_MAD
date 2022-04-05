package com.example.lab7

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab7.CatAdapter.ViewHolder
import com.example.lab7.model.Cat

class CatAdapter(private val catList: ArrayList<Cat>, private val clickListener:(Cat) -> Unit): RecyclerView.Adapter<ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val catTextView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //create an instance of LayoutInflater
        val layoutInflater = LayoutInflater.from(parent.context)
        //inflate the view
        val itemViewHolder = layoutInflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemViewHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //get data at the position
        val cat = catList[position]
        //set the text of the textview to the name
        Log.d("onbindviewholder", cat.type)
        holder.catTextView.text = cat.type
        //assign click listener
        holder.itemView.setOnClickListener{clickListener(cat)}
    }

    override fun getItemCount() = catList.size

}