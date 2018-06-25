package com.example.malar.todolists.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.example.malar.todolists.R
import com.example.malar.todolists.model.ToDoTask
import android.text.Editable



class TasksAdapter(val tasks: List<ToDoTask>) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.to_do_task, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val titleEditView = holder.view.findViewById<TextView>(R.id.taskTitle)
        titleEditView.text = tasks[position].title
        holder.view.findViewById<CheckBox>(R.id.taskTitle).isChecked = tasks[position].isDone
    }
}