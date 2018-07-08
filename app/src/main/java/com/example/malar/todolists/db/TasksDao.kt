package com.example.malar.todolists.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.malar.todolists.model.ToDoTask

@Dao
interface TasksDao {
    @Query("SELECT * FROM toDoTasks WHERE projectId=:projectId")
    fun loadTasksByProject(projectId: Long) : LiveData<List<ToDoTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTasks(task: ToDoTask) : Long

    @Update
    fun updateTasks(vararg task: ToDoTask)

    @Delete
    fun deleteTask(vararg task: ToDoTask)
}