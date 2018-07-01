package com.example.malar.todolists.db

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.*
import com.example.malar.todolists.model.Project
import com.example.malar.todolists.model.ToDoTask
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface ProjectsDao{

    @Query("SELECT * FROM projects")
    fun loadAllProjects() : LiveData<List<Project>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProjects(project: Project) : Long

    @Update
    fun updateProjects(vararg project: Project)

    @Delete
    fun deleteProjects(vararg project: Project)

//    @Query("SELECT * FROM projects WHERE title LIKE :filterString")
//    fun loadProjectsWithFilter(filterString: String) : List<Project>

//    @Query("SELECT * FROM toDoTasks WHERE title LIKE '%:filterString%' AND projectId=:projectId")
//    fun loadTasksWithFilter(projectId: Long, filterString: String) : List<ToDoTask>
}