package com.example.malar.todolists.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import com.example.malar.todolists.R
import com.example.malar.todolists.fragments.TaskInteraction
import com.example.malar.todolists.model.ToDoTask


class TasksAdapter(var tasks: List<ToDoTask>, val taskInteraction: TaskInteraction) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.to_do_task, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val titleEditView = holder.view.findViewById<TextView>(R.id.taskTitle)
        titleEditView.text = tasks[position].title

        val isDoneCheckBox = holder.view.findViewById<CheckBox>(R.id.isDone)
        isDoneCheckBox.isChecked = tasks[position].isDone
        isDoneCheckBox.setOnCheckedChangeListener(onCheckedChanged(position))

        val deleteTaskButton = holder.view.findViewById<Button>(R.id.deleteTaskButton)
        deleteTaskButton.setOnClickListener { taskInteraction.deleteTask(tasks[position]) }
    }

    private fun onCheckedChanged(position: Int): (CompoundButton, Boolean) -> Unit {
        return { _: CompoundButton, isChecked: Boolean ->
            tasks[position].isDone = isChecked
            taskInteraction.updateTask(tasks[position])
        }
    }

    fun updateTasks(newTasks: List<ToDoTask>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}