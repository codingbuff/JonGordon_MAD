package com.example.chordjam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chordjam.data.ProgItem
import com.example.chordjam.data.ProgItemList
import com.example.chordjam.savedProgressionsAdapter.ViewHolder

class savedProgressionsAdapter(private val progList: ProgItemList, private val clickListener:(ProgItem,Int) -> Unit): RecyclerView.Adapter<ViewHolder>() {
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
        val progItem = progList.getItemAt(position)
        holder.progTextView.text = progName
        holder.itemView.setOnClickListener{clickListener(progItem,position)}
    }

    override fun getItemCount() = progList.getItems().size

}