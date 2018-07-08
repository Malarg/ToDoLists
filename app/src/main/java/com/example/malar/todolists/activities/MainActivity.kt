package com.example.malar.todolists.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.widget.FrameLayout
import com.example.malar.todolists.R
import com.example.malar.todolists.fragments.ProjectsFragment
import com.example.malar.todolists.fragments.TasksFragment
import com.example.malar.todolists.fragments.TextEditFragment
import com.example.malar.todolists.model.Project
import com.example.malar.todolists.model.ToDoTask
import com.example.malar.todolists.viewmodels.MainViewModel
import com.example.malar.todolists.viewmodels.MainViewModelFactory

class MainActivity : FragmentActivity(), ProjectsFragment.OnProjectSelected, TextEditFragment.OnTextEditListener {
    private val PROJECTS_TAG = "projects"
    private val TASKS_TAG = "tasks"
    override fun onProjectSelected(id: Long) {
        tasksFragment = TasksFragment.newInstance(id)
        mainViewModel.selectedProjectId = id
        replaceFragment(tasksFragment, TASKS_TAG)
    }

    private lateinit var mainViewModelFactory: MainViewModelFactory

    private lateinit var mainViewModel: MainViewModel
    private lateinit var tasksFragment: TasksFragment
    private lateinit var projectsFragment: ProjectsFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModelFactory = MainViewModelFactory(this.application)
        mainViewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel::class.java)
        projectsFragment = ProjectsFragment.newInstance()
        tasksFragment = TasksFragment.newInstance(-1)
        addFragment(projectsFragment, PROJECTS_TAG)
        mainViewModel.tasks.observe(this, Observer<List<ToDoTask>> { words ->
            words?.forEach { word ->
                println("${word.id} + ${word.title}")
            }
        })
    }

    override fun onTextEdit(text: String, actionCode: String) {
        when (actionCode) {
            TextEditFragment.ADD_PROJECT -> {
                mainViewModel.insertProject(Project(title = text))
            }
            TextEditFragment.ADD_TASK -> {
                mainViewModel.insertTask(ToDoTask(title = text, projectId = mainViewModel.selectedProjectId, isDone = false))
            }
        }
    }

    private fun addFragment(fragment: Fragment, tag: String) {
        if (findViewById<FrameLayout>(R.id.fragmentsContainer) != null) {
            supportFragmentManager.beginTransaction().add(R.id.fragmentsContainer, fragment, tag).commit()
        }
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        if (findViewById<FrameLayout>(R.id.fragmentsContainer) != null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentsContainer, fragment, tag).addToBackStack(tag).commit()
        }
    }
}
