package com.example.malar.todolists.fragments

import com.example.malar.todolists.model.ToDoTask

interface TaskInteraction {
    fun deleteTask(task: ToDoTask)
    fun updateTask(task: ToDoTask)
}