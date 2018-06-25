package com.example.malar.todolists.db

import android.arch.persistence.room.Database
import com.example.malar.todolists.model.Project
import com.example.malar.todolists.model.ToDoTask
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context


@Database(entities = [Project::class, ToDoTask::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        private var databaseInstance: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (databaseInstance == null) synchronized(AppDatabase::class.java) {
                if (databaseInstance == null) {
                    databaseInstance = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "app_database")
                            .build()

                }
            }
            return databaseInstance
                    ?: throw NullPointerException("Expression 'databaseInstance' must not be null")
        }
    }
}