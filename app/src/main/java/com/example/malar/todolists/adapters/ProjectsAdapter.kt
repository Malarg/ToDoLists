package com.example.malar.todolists.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.daimajia.swipe.SwipeLayout
import com.example.malar.todolists.R
import com.example.malar.todolists.fragments.ProjectInteraction
import com.example.malar.todolists.model.Project

class ProjectsAdapter(var projects: List<Project>, val projectInteraction: ProjectInteraction) : RecyclerView.Adapter<ProjectsAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var projectInteraction: ProjectInteraction
        var projectTitle: TextView = view.findViewById(R.id.projectTitle)

        constructor(view: View, projectInteraction: ProjectInteraction) : this(view) {
            this.projectInteraction = projectInteraction

        }
    }

    fun updateProjects(projects: List<Project>) {
        this.projects = projects
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
        deleteButton.setOnClickListener {
            projectInteraction.deleteProject(projects[position])
        }
        (holder.itemView as SwipeLayout).surfaceView.setOnClickListener {
            projectInteraction.selectProject(projects[position])
        }
    }
}

