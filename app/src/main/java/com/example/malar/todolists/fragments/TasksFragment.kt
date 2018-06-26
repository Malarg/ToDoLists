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
import com.example.malar.todolists.adapters.TasksAdapter
import com.example.malar.todolists.model.ToDoTask
import com.example.malar.todolists.viewmodels.MainViewModel
import com.example.malar.todolists.viewmodels.MainViewModelFactory
import org.w3c.dom.Text

private const val ARG_PROJECT_ID = "tasks list"

class TasksFragment : Fragment() {
    private var projectId: Long = -1
    private lateinit var viewAdapter: TasksAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            projectId = it.getLong(ARG_PROJECT_ID)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mainViewModel = buildViewModel(activity)
        viewAdapter = buildAdapter(mainViewModel)
        viewManager = LinearLayoutManager(this.context)
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        view.findViewById<RecyclerView>(R.id.tasksRecyclerView).apply {
            setHasFixedSize(false)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        mainViewModel.getTasks().observe(this, Observer<List<ToDoTask>> { tasks ->
            viewAdapter.updateTasks(tasks!!.filter { it.id == projectId })
        })
        val addTaskFAB = view.findViewById<FloatingActionButton>(R.id.addTaskFAB)
        addTaskFAB.setOnClickListener({ v ->
            addTask(v)
        })
        return view
    }

    private fun addTask(view: View?) {
        val textEditFragment = TextEditFragment.newInstance(TextEditFragment.ADD_TASK)
        textEditFragment.show(activity?.supportFragmentManager, "TextEdit")
    }

    private fun buildViewModel(activity: FragmentActivity?): MainViewModel {
        val mainViewModelFactory = MainViewModelFactory((activity
                ?: throw NullPointerException("Expression 'activity' must not be null")).application)
        val mainViewModel = ViewModelProviders.of(activity, mainViewModelFactory).get(MainViewModel::class.java)
        return mainViewModel
    }

    private fun buildAdapter(mainViewModel: MainViewModel): TasksAdapter {
        return TasksAdapter(tasks = emptyList(),
                taskInteraction = object : TaskInteraction {
                    override fun deleteTask(task: ToDoTask) {
                        mainViewModel.deleteTask(task)
                    }

                    override fun updateTask(task: ToDoTask) {
                        mainViewModel.updateTask(task)
                    }
                })
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(projectId: Long) =
                TasksFragment().apply {
                    arguments = Bundle().apply {
                        putLong(ARG_PROJECT_ID, projectId)
                    }
                }
    }
}
