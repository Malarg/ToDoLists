package com.example.malar.todolists.adapters

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.malar.todolists.R
import com.example.malar.todolists.fragments.ProjectInteraction
import com.example.malar.todolists.model.Project

class ProjectsAdapter(var projects: List<Project>, val projectInteraction: ProjectInteraction) : RecyclerView.Adapter<ProjectsAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        lateinit var projectInteraction: ProjectInteraction
        var projectTitle: TextView = view.findViewById(R.id.projectTitle)
        var projectItemLayout: ConstraintLayout = view.findViewById(R.id.projectItemLayout)
        constructor(view: View, prjctInteraction: ProjectInteraction) : this(view) {
            projectInteraction = prjctInteraction

        }
    }

    fun updateProjects(prjcts: List<Project>){
        projects = prjcts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.project_item_layout, parent, false)

        return ViewHolder(view, projectInteraction)
    }

    override fun getItemCount() = projects.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.projectTitle.text = projects[position].title
        val deleteButton = holder.view.findViewById<Button>(R.id.deleteProjectButton)
        deleteButton.setOnClickListener({
            projectInteraction.deleteProject(projects[position])
        })
        holder.itemView.setOnClickListener({
            projectInteraction.selectProject(projects[position])
        })
    }
}

