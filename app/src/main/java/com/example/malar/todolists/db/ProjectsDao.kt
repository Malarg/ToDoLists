package com.example.malar.todolists.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.malar.todolists.model.Project

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
}