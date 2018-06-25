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

    fun addTask(task: ToDoTask){
        InsertTaskAsyncTask(appDao).execute(task)
    }

    fun removeTask(task: ToDoTask) = appDao.deleteTask(task)

    fun updateTask(task: ToDoTask) = appDao.updateTasks(task)

    fun insertProject(project: Project) {
        InsertProjectAsyncTask(appDao).execute(project)
    }

    fun removeProject(project: Project) = DeleteProjectAsyncTask(appDao).execute(project)

    fun updateProject(project: Project): Project {
        appDao.updateProjects(project)
        return project
    }

    private class InsertProjectAsyncTask(val dao: AppDao) : AsyncTask<Project, Void, Void>() {
        override fun doInBackground(vararg project: Project?): Void? {
            dao.insertProjects(project[0]!!)
            return null
        }
    }

    private class DeleteProjectAsyncTask(val dao: AppDao) : AsyncTask<Project, Void, Void>() {
        override fun doInBackground(vararg project: Project?): Void? {
            dao.deleteProjects(project[0]!!)
            return null
        }
    }

    private class InsertTaskAsyncTask(val dao: AppDao) : AsyncTask<ToDoTask, Void, Void>(){
        override fun doInBackground(vararg task: ToDoTask?): Void? {
            dao.insertTasks(task[0]!!)
            return null
        }

    }
}