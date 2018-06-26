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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_TEXT = "text"

class TextEditFragment : DialogFragment() {
    private lateinit var actionType: String
    private var listener: OnTextEditListener? = null
    private lateinit var editText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            actionType = it.getString(ARG_TEXT)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val dialog = builder.create()
        return builder.show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.text_edit_layout, null)
        editText = view.findViewById(R.id.editTaskTitleEditText)
        val okButton = view.findViewById<Button>(R.id.submitTextEditButton)
        okButton.setOnClickListener({
            listener?.onTextEdit(editText.text.toString(), actionType)
            dialog.cancel()
        })
        val cancelButton = view.findViewById<Button>(R.id.cancelTextEditButton)
        cancelButton.setOnClickListener({dialog.cancel()})
        return view
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
