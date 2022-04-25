package com.example.chordjam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chordjam.savedProgressionsAdapter.ViewHolder

class savedProgressionsAdapter(private val progList: ProgItemList): RecyclerView.Adapter<ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val progTextView: TextView = view.findViewById(R.id.progTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemViewHolder = layoutInflater.inflate(R.layout.prog_item, parent, false)
        return ViewHolder(itemViewHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val progName = progList.getItemAt(position).name
        holder.progTextView.text = progName
    }

    override fun getItemCount() = progList.getItems().size
}