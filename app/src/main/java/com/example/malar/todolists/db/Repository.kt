package com.example.malar.todolists.db

import android.app.Application
import android.os.AsyncTask
import com.example.malar.todolists.model.Project
import com.example.malar.todolists.model.ToDoTask

class Repository(val application: Application) {

    private val db: AppDatabase = AppDatabase.getDatabase(application)
    private val appDao = db.appDao()

    fun getProjects() = appDao.loadAllProjects()

    fun getTasks(projectId: Long) = appDao.loadTasksByProject(projectId)

    fun insertTask(task: ToDoTask) {
        InsertTaskAsyncTask(appDao).execute(task)
    }

    private class InsertTaskAsyncTask(val dao: AppDao) : AsyncTask<ToDoTask, Void, Void>() {
        override fun doInBackground(vararg tasks: ToDoTask?): Void? {
            dao.insertTasks(tasks[0]!!)
            return null
        }

    }

    fun removeTask(task: ToDoTask) = DeleteTaskAsyncTask(appDao).execute(task)
    private class DeleteTaskAsyncTask(val dao: AppDao) : AsyncTask<ToDoTask, Void, Void>() {
        override fun doInBackground(vararg tasks: ToDoTask?): Void? {
            dao.deleteTask(tasks[0]!!)
            return null
        }

    }

    fun updateTask(task: ToDoTask) = UpdateTaskAsyncTask(appDao).execute(task)
    private class UpdateTaskAsyncTask(val dao: AppDao) : AsyncTask<ToDoTask, Void, Void>() {
        override fun doInBackground(vararg tasks: ToDoTask?): Void? {
            dao.updateTasks(tasks[0]!!)
            return null
        }

    }

    fun insertProject(project: Project) {
        InsertProjectAsyncTask(appDao).execute(project)
    }

    private class InsertProjectAsyncTask(val dao: AppDao) : AsyncTask<Project, Void, Void>() {
        override fun doInBackground(vararg projects: Project?): Void? {
            dao.insertProjects(projects[0]!!)
            return null
        }

    }

    fun removeProject(project: Project) = DeleteProjectAsyncTask(appDao).execute(project)
    private class DeleteProjectAsyncTask(val dao: AppDao) : AsyncTask<Project, Void, Void>() {
        override fun doInBackground(vararg project: Project?): Void? {
            dao.deleteProjects(project[0]!!)
            return null
        }
    }
}