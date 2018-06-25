package com.example.malar.todolists.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.malar.todolists.model.Project
import com.example.malar.todolists.model.ToDoTask
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface AppDao{

    @Query("SELECT * FROM projects")
    fun loadAllProjects() : LiveData<List<Project>>

    @Query("SELECT * FROM toDoTasks WHERE projectId=:projectId")
    fun loadTasksByProject(projectId: Long) : LiveData<List<ToDoTask>>

    @Insert
    fun insertTasks(task: ToDoTask) : Long

    @Insert
    fun insertProjects(project: Project) : Long

    @Update
    fun updateProjects(vararg project: Project)

    @Update
    fun updateTasks(vararg task: ToDoTask)

    @Delete
    fun deleteProjects(vararg project: Project)

    @Delete
    fun deleteTask(vararg task: ToDoTask)

    @Query("SELECT * FROM projects WHERE title LIKE :filterString")
    fun loadProjectsWithFilter(filterString: String) : List<Project>

//    @Query("SELECT * FROM toDoTasks WHERE title LIKE '%:filterString%' AND projectId=:projectId")
//    fun loadTasksWithFilter(projectId: Long, filterString: String) : List<ToDoTask>
}