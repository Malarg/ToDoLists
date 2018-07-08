package com.example.malar.todolists.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "toDoTasks", foreignKeys = [(ForeignKey(entity = Project::class, parentColumns = ["id"], childColumns = ["projectId"], onDelete = ForeignKey.CASCADE))])
data class ToDoTask (@PrimaryKey(autoGenerate = true) var id: Long = 0,
                     @ColumnInfo(name = "title") var title: String,
                     @ColumnInfo(name = "isDone") var isDone: Boolean,
                     @ColumnInfo(name = "projectId") var projectId: Long)