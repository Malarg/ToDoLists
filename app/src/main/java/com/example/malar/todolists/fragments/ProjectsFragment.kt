package com.example.malar.todolists.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.malar.todolists.R
import com.example.malar.todolists.adapters.ProjectsAdapter
import com.example.malar.todolists.model.Project
import com.example.malar.todolists.viewmodels.MainViewModel
import com.example.malar.todolists.viewmodels.MainViewModelFactory


class ProjectsFragment : Fragment() {
    private var parentActivity: OnProjectSelected? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_projects, container, false)
        val activity = activity ?: throw NullPointerException("Expression 'activity' must not be null")
        val mainViewModel = buildViewModel(activity)
        val viewAdapter = buildAdapter(mainViewModel)

        buildRecycleView(view, viewAdapter)
        buildAddProjectFAB(view)

        mainViewModel.projects.observe(this, Observer<List<Project>> { projects ->
            viewAdapter.updateProjects(projects!!)
        })

        return view
    }

    private fun buildViewModel(activity: FragmentActivity) : MainViewModel {
        val mainViewModelFactory = MainViewModelFactory(activity.application)
        return ViewModelProviders.of(activity, mainViewModelFactory).get(MainViewModel::class.java)
    }

    private fun buildAdapter(mainViewModel: MainViewModel): ProjectsAdapter {
        return ProjectsAdapter(
                projects = emptyList(),
                projectInteraction = object : ProjectInteraction {
                    override fun selectProject(project: Project) {
                        parentActivity?.onProjectSelected(project.id)
                    }

                    override fun deleteProject(project: Project) {
                        mainViewModel.deleteProject(project)
                    }

                }
        )
    }

    private fun buildRecycleView(view: View, viewAdapter: ProjectsAdapter) {
        val viewManager = LinearLayoutManager(this.context)
        val projectsRecycleView = view.findViewById<RecyclerView>(R.id.projectsRecyclerView)
        projectsRecycleView.adapter = viewAdapter
        projectsRecycleView.layoutManager = viewManager
    }

    private fun buildAddProjectFAB(view: View) {
        val addProjectFAB = view.findViewById<FloatingActionButton>(R.id.addProjectFAB)
        addProjectFAB.setOnClickListener { _ ->
            addProject()
        }
    }

    private fun addProject() {
        val textEditFragment = TextEditFragment.newInstance(TextEditFragment.ADD_PROJECT)
        textEditFragment.show(activity?.supportFragmentManager, "TextEdit")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnProjectSelected) {
            parentActivity = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnProjectSelected")
        }
    }

    override fun onDetach() {
        super.onDetach()
        parentActivity = null
    }

    interface OnProjectSelected {
        fun onProjectSelected(id: Long)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProjectsFragment()
    }
}
