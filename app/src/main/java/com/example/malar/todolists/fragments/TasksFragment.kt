package com.example.malar.todolists.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.malar.todolists.R
import com.example.malar.todolists.adapters.TasksAdapter
import com.example.malar.todolists.viewmodels.MainViewModel
import com.example.malar.todolists.viewmodels.MainViewModelFactory

private const val ARG_PROJECT_ID = "tasks list"

class TasksFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var projectId: Int = -1
//    private var listener: OnFragmentInteractionListener? = null
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            projectId = it.getInt(ARG_PROJECT_ID)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        viewManager = LinearLayoutManager(this.context)
        val activity = activity
        val mainViewModelFactory = MainViewModelFactory((activity ?: throw NullPointerException("Expression 'activity' must not be null")).application)
        val mainViewModel = ViewModelProviders.of(activity, mainViewModelFactory).get(MainViewModel::class.java)
        viewAdapter = TasksAdapter(tasks = mainViewModel.getTasks().value
                ?: throw NullPointerException("Expression 'mainViewModel.getTasks().value' must not be null"))
        view.findViewById<RecyclerView>(R.id.tasks).apply {
            setHasFixedSize(false)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
    }

    override fun onDetach() {
        super.onDetach()
//        listener = null
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(projectId: Int) =
                TasksFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_PROJECT_ID, projectId)
                    }
                }
    }
}
