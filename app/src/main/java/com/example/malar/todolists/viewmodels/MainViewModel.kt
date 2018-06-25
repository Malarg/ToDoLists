package com.example.malar.todolists.viewmodels

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.malar.todolists.db.Repository
import com.example.malar.todolists.model.Project
import com.example.malar.todolists.model.ToDoTask
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainViewModel(application: Application) : ViewModel(){

    private val repository: Repository = Repository(application)
    private var projects: LiveData<List<Project>> = repository.getProjects()
    private var tasks: LiveData<List<ToDoTask>> = MutableLiveData()


    fun getProjects() = projects

    fun getTasks() = tasks

    fun insertProject(project: Project) = repository.insertProject(project)

    fun deleteProject(project: Project) = repository.removeProject(project)
}
