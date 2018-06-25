package com.example.malar.todolists.activities

import android.arch.lifecycle.Observer
import android.support.v4.app.Fragment
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.FrameLayout
import com.example.malar.todolists.R
import com.example.malar.todolists.db.Repository
import com.example.malar.todolists.fragments.ProjectsFragment
import com.example.malar.todolists.fragments.TasksFragment
import com.example.malar.todolists.fragments.TextEditFragment
import com.example.malar.todolists.model.Project
import com.example.malar.todolists.viewmodels.MainViewModel
import com.example.malar.todolists.viewmodels.MainViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : FragmentActivity(), ProjectsFragment.OnProjectSelected, TextEditFragment.OnTextEditListener {
    override fun onProjectSelected(id: Long) {

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
        changeFragment(savedInstanceState, projectsFragment)
        mainViewModel.getProjects().observe(this, Observer<List<Project>> { words ->
            words?.forEach { word ->
                println("${word.id} + ${word.title}")
            }
        })
    }

    override fun onTextEdit(text: String, actionCode: String) {
        when (actionCode){
            TextEditFragment.ADD_PROJECT -> {mainViewModel.insertProject(Project(title = text))}

        }
    }

    private fun changeFragment(savedInstanceState: Bundle?, fragment: Fragment) {
        if (findViewById<FrameLayout>(R.id.fragmentsContainer) != null) {
            if (savedInstanceState != null) {
                return
            }
            supportFragmentManager.beginTransaction().add(R.id.fragmentsContainer, fragment).commit()
        }
    }
}
