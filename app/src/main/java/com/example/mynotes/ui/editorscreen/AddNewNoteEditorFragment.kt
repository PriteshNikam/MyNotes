package com.example.mynotes.ui.editorscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mynotes.R
import com.example.mynotes.ui.customdialog.WarningDialogFragment
import com.example.mynotes.databinding.FragmentAddNewNoteEditorBinding
import com.example.mynotes.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewNoteEditorFragment : Fragment() {

    private lateinit var binding: FragmentAddNewNoteEditorBinding
    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var warningDialog: WarningDialogFragment
    private var newNoteId: Int = 0
    private lateinit var args: AddNewNoteEditorFragmentArgs
    private val noteTime: Long by lazy{ System.currentTimeMillis() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPress()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddNewNoteEditorBinding.inflate(inflater, container, false)

        args = AddNewNoteEditorFragmentArgs.fromBundle(requireArguments())
        newNoteId = args.note.noteId
        binding.note = args.note

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.buttonSaveNoteAddNoteEditor.setOnClickListener {
            warningDialog = WarningDialogFragment.newInstance(
                getString(R.string.save_changes), getString(
                    R.string.save
                ), getString(R.string.discard)
            )
            showDialog()

            requireActivity().supportFragmentManager
                .setFragmentResultListener(
                    operationKey,
                    viewLifecycleOwner
                ) { operationKey, bundle ->
                    val result = bundle.getBoolean(bundleKey)

                    if (result) onSaveNote()

                    else warningDialog.dismiss()
                }
        }

        binding.buttonGoBackAddNoteEditor.setOnClickListener {
            onBackPress()
        }

        binding.buttonPreviewNoteAddNoteEditor.setOnClickListener {
            binding.apply {
                buttonGoBackAddNoteEditor.visibility = View.INVISIBLE
                buttonPreviewNoteAddNoteEditor.visibility = View.INVISIBLE
                buttonEditNoteAddNoteEditor.visibility = View.VISIBLE
                edittextTitleAddNoteEditor.isEnabled = false
                edittextDescriptionAddNoteEditor.isEnabled = false
            }
        }

        binding.buttonEditNoteAddNoteEditor.setOnClickListener {
            binding.apply {
                buttonGoBackAddNoteEditor.visibility = View.VISIBLE
                buttonPreviewNoteAddNoteEditor.visibility = View.VISIBLE
                buttonEditNoteAddNoteEditor.visibility = View.INVISIBLE
                edittextTitleAddNoteEditor.isEnabled = true
                edittextDescriptionAddNoteEditor.isEnabled = true
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showDialog() {
        warningDialog.show(
            childFragmentManager,
            "CustomerDialogFragment"
        ) // does not add to backStack
    }

    private fun onSaveNote() {
        val noteTitle = binding.edittextTitleAddNoteEditor.text.toString()
        val noteDescription = binding.edittextDescriptionAddNoteEditor.text.toString()
        //val noteTime = SimpleDateFormat(dateFormat, Locale.getDefault()).format(Date())

        if (newNoteId == 0) {
            if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                noteViewModel.insert(noteTitle, noteDescription, noteTime)
                Toast.makeText(requireContext(), getString(R.string.note_saved), Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(AddNewNoteEditorFragmentDirections.actionAddNewNoteEditorFragmentToHomeScreenFragment())
            } else {
                createToast(R.string.please_write_something)
            }
        } else {
            args.apply {
                note.noteTitle = noteTitle
                note.noteDescription = noteDescription
                note.noteTime = noteTime
            }
            if (args.note.noteTitle.isNotEmpty() && args.note.noteDescription.isNotEmpty()) {
                noteViewModel.update(args.note)
                createToast(R.string.note_updated)
            } else {
                createToast(R.string.please_write_something)
            }
        }
        warningDialog.dismiss()
    }

    fun onBackPress() {

        val currentTitle = binding.edittextTitleAddNoteEditor.text.toString()
        val currentDescription = binding.edittextDescriptionAddNoteEditor.text.toString()

        val editorNotEmpty = currentTitle.isNotEmpty() || currentDescription.isNotEmpty()

        val hasDataChanged =
            args.note.noteTitle != currentTitle || args.note.noteDescription != currentDescription

        if(editorNotEmpty && hasDataChanged) {
           warningDialog = WarningDialogFragment.newInstance(
                getString(R.string.sure_want_to_exit),
                getString(R.string.keep),
                getString(R.string.discard)
            )
            showDialog()

            requireActivity().supportFragmentManager
                .setFragmentResultListener(operationKey, viewLifecycleOwner) { operationKey, bundle ->

                    val result = bundle.getBoolean(bundleKey)

                    if (result) warningDialog.dismiss()

                    else {
                        findNavController().navigate(
                            AddNewNoteEditorFragmentDirections
                                .actionAddNewNoteEditorFragmentToHomeScreenFragment()
                        )
                    }
                }
        } else {
            findNavController().navigate(
                AddNewNoteEditorFragmentDirections
                    .actionAddNewNoteEditorFragmentToHomeScreenFragment())
        }
    }
    fun createToast(message:Int){
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
    }

    companion object{
        const val operationKey = "operationKey"
        const val bundleKey = "key"
    }
}


