package com.example.malar.todolists.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.graphics.Color

@Entity(tableName = "projects")
class Project(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0,
                   @ColumnInfo(name = "title") var title: String)