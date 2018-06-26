package com.example.malar.todolists.fragments

import com.example.malar.todolists.model.Project

interface ProjectInteraction {
    fun deleteProject(project: Project)
    fun selectProject(project: Project)
}