package com.example.malar.todolists.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.malar.todolists.R

private const val ARG_TEXT = "text"

class TextEditFragment : DialogFragment() {
    private lateinit var actionType: String
    private var listener: OnTextEditListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            actionType = it.getString(ARG_TEXT)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.create()
        return builder.show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.text_edit_layout, null)

        buildOKButton(view)
        buildCancelButton(view)

        return view
    }

    private fun buildOKButton(view: View) {
        val editText = view.findViewById<EditText>(R.id.editTaskTitleEditText)
        val okButton = view.findViewById<Button>(R.id.submitTextEditButton)
        okButton.setOnClickListener {
            if (!editText.text.isEmpty()) {
                listener?.onTextEdit(editText.text.toString(), actionType)
            }
            dialog.cancel()
        }
    }

    private fun buildCancelButton(view: View) {
        val cancelButton = view.findViewById<Button>(R.id.cancelTextEditButton)
        cancelButton.setOnClickListener { dialog.cancel() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnTextEditListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnTextEditListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnTextEditListener {
        fun onTextEdit(text: String, actionCode: String)
    }

    companion object {
        @JvmStatic
        fun newInstance(text: String) =
                TextEditFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_TEXT, text)
                    }
                }
        const val ADD_PROJECT = "add project"
        const val ADD_TASK = "add task"
    }
}
