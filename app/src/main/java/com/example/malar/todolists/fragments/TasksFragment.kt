package com.example.malar.todolists.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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

private const val ARG_PROJECT_ID = "tasks list"

class TasksFragment : Fragment() {
    private var projectId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            projectId = it.getLong(ARG_PROJECT_ID)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        val mainViewModel = buildViewModel(activity)
        val viewAdapter = buildAdapter(mainViewModel)
        buildRecycleView(view, viewAdapter)
        buildAddTaskFAB(view)

        mainViewModel.tasks.observe(this, Observer<List<ToDoTask>> { tasks ->
            val tasks = tasks ?: NullPointerException("Tasks should not be null")
            viewAdapter.updateTasks(tasks as List<ToDoTask>)
        })

        return view
    }

    private fun buildViewModel(activity: FragmentActivity?): MainViewModel {
        val mainViewModelFactory = MainViewModelFactory((activity
                ?: throw NullPointerException("Expression 'activity' must not be null")).application)
        return ViewModelProviders.of(activity, mainViewModelFactory).get(MainViewModel::class.java)
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

    private fun buildRecycleView(view: View, viewAdapter: TasksAdapter) {
        val viewManager = LinearLayoutManager(this.context)
        view.findViewById<RecyclerView>(R.id.tasksRecyclerView).apply {
            setHasFixedSize(false)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun buildAddTaskFAB(view: View) {
        view.findViewById<FloatingActionButton>(R.id.addTaskFAB).setOnClickListener {
            addTask()
        }
    }

    private fun addTask() {
        val textEditFragment = TextEditFragment.newInstance(TextEditFragment.ADD_TASK)
        textEditFragment.show(activity?.supportFragmentManager, "TextEdit")
    }

    companion object {
        @JvmStatic
        fun newInstance(projectId: Long) =
                TasksFragment().apply {
                    arguments = Bundle().apply {
                        putLong(ARG_PROJECT_ID, projectId)
                    }
                }
    }
}
