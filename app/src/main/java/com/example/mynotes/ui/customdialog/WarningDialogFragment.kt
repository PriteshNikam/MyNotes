package com.example.mynotes.ui.customdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentWarningDialogBinding
import com.example.mynotes.ui.editorscreen.AddNewNoteEditorFragment

private var ARG_DIALOG_MSG = "dialogTitle"
private var ARGS_DIALOG_POSITIVE_BTN_TEXT = "dialogPositiveBtnText"
private var ARGS_DIALOG_NEGATIVE_BTN_TEXT = "dialogNegativeBtnText"

private const val operationKey = AddNewNoteEditorFragment.operationKey
private const val bundleKey = AddNewNoteEditorFragment.bundleKey

class WarningDialogFragment: DialogFragment() {

    private lateinit var binding: FragmentWarningDialogBinding

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.drawable.custom_dialog_shape)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWarningDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            textViewDialogAlertText.text = arguments?.getString(ARG_DIALOG_MSG)
            buttonDialogPositive.text = arguments?.getString(ARGS_DIALOG_POSITIVE_BTN_TEXT)
            buttonDialogNegative.text = arguments?.getString(ARGS_DIALOG_NEGATIVE_BTN_TEXT)
        }

        binding.buttonDialogPositive.setOnClickListener {
            val result = true
            onClick(result)
        }

        binding.buttonDialogNegative.setOnClickListener {
            val result = false
            onClick(result)
        }
    }

    private fun onClick(result:Boolean){
        requireActivity().supportFragmentManager
            .setFragmentResult(operationKey, bundleOf(bundleKey to result))
    }

    companion object {
        fun newInstance(
            dialogMsg: String,
            dialogPositiveBtnText: String,
            dialogNegativeBtnText: String
        ): WarningDialogFragment {
            val fragment = WarningDialogFragment()
            fragment.arguments = Bundle().apply {
                putString(ARG_DIALOG_MSG, dialogMsg)
                putString(ARGS_DIALOG_POSITIVE_BTN_TEXT, dialogPositiveBtnText)
                putString(ARGS_DIALOG_NEGATIVE_BTN_TEXT, dialogNegativeBtnText)
            }
            return fragment
        }
    }
}