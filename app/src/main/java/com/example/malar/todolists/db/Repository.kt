package com.example.malar.todolists.db

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.example.malar.todolists.model.Project
import com.example.malar.todolists.model.ToDoTask

class Repository(val application: Application) {

    private val db: AppDatabase = AppDatabase.getDatabase(application)
    private val projectsDao = db.projectsDao()
    private val tasksDao = db.tasksDao()

    fun getProjects() = projectsDao.loadAllProjects()

    fun getTasks(projectId: Long): LiveData<List<ToDoTask>> =tasksDao.loadTasksByProject(projectId)

    fun insertTask(task: ToDoTask) {
        InsertTaskAsyncTask(tasksDao).execute(task)
    }

    private class InsertTaskAsyncTask(val dao: TasksDao) : AsyncTask<ToDoTask, Void, Void>() {
        override fun doInBackground(vararg tasks: ToDoTask?): Void? {
            dao.insertTasks(tasks[0]!!)
            return null
        }

    }

    fun removeTask(task: ToDoTask) = DeleteTaskAsyncTask(tasksDao).execute(task)
    private class DeleteTaskAsyncTask(val dao: TasksDao) : AsyncTask<ToDoTask, Void, Void>() {
        override fun doInBackground(vararg tasks: ToDoTask?): Void? {
            dao.deleteTask(tasks[0]!!)
            return null
        }

    }

    fun updateTask(task: ToDoTask) = UpdateTaskAsyncTask(tasksDao).execute(task)
    private class UpdateTaskAsyncTask(val dao: TasksDao) : AsyncTask<ToDoTask, Void, Void>() {
        override fun doInBackground(vararg tasks: ToDoTask?): Void? {
            dao.updateTasks(tasks[0]!!)
            return null
        }

    }

    fun insertProject(project: Project) {
        InsertProjectAsyncTask(projectsDao).execute(project)
    }

    private class InsertProjectAsyncTask(val dao: ProjectsDao) : AsyncTask<Project, Void, Void>() {
        override fun doInBackground(vararg projects: Project?): Void? {
            dao.insertProjects(projects[0]!!)
            return null
        }

    }

    fun removeProject(project: Project) = DeleteProjectAsyncTask(projectsDao).execute(project)
    private class DeleteProjectAsyncTask(val dao: ProjectsDao) : AsyncTask<Project, Void, Void>() {
        override fun doInBackground(vararg project: Project?): Void? {
            dao.deleteProjects(project[0]!!)
            return null
        }
    }
}