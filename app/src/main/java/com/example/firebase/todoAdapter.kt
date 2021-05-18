package com.example.firebase

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView


class todoAdapter(var todos: List<Upload>): RecyclerView.Adapter<todoAdapter.ToDoViewHolder>()
{


    inner class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txt : TextView = itemView.findViewById(R.id.dirname)
        var btn : Button = itemView.findViewById(R.id.btndownload)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.singlelist , parent , false)

        return(ToDoViewHolder(view))
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.itemView.apply {

            holder.txt.text = todos[position].name
            holder.btn.setOnClickListener {
                val intent: Intent = Intent(Intent.ACTION_VIEW,Uri.parse(todos[position].url))
                intent.data = Uri.parse(todos[position].url)
                context.startActivity(intent)
            }

        }

    }

    override fun getItemCount(): Int {
        return(todos.size)
    }

}