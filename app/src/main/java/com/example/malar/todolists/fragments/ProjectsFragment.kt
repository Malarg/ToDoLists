package com.example.malar.todolists.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.example.malar.todolists.R
import com.example.malar.todolists.adapters.ProjectsAdapter
import com.example.malar.todolists.model.Project
import com.example.malar.todolists.viewmodels.MainViewModel
import com.example.malar.todolists.viewmodels.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_tasks.*


class ProjectsFragment : Fragment() {
    private var parentActivity: OnProjectSelected? = null
    private lateinit var viewAdapter: ProjectsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_projects, container, false)
        val mainViewModelFactory = MainViewModelFactory(activity!!.application)
        mainViewModel = ViewModelProviders.of(activity!!, mainViewModelFactory).get(MainViewModel::class.java)
        viewAdapter = ProjectsAdapter(
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
        viewManager = LinearLayoutManager(this.context)
        val recView = view.findViewById<RecyclerView>(R.id.projectsRecyclerView)
        recView.adapter = viewAdapter
        recView.layoutManager = viewManager
        mainViewModel.getProjects().observe(this, Observer<List<Project>> { prjts ->
            viewAdapter.updateProjects(prjts!!)
        })
        val addPrjctFAB = view.findViewById<FloatingActionButton>(R.id.addProjectFAB)
        addPrjctFAB.setOnClickListener({v ->
            addProject(v)
        })
        return view
    }

    fun addProject(view: View) {
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
