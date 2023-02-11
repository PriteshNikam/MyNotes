package com.example.mynotes.ui.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotes.ui.customdialog.AppInfoDialogFragment
import com.example.mynotes.ui.customdialog.WarningDialogFragment
import com.example.mynotes.data.Note
import com.example.mynotes.databinding.FragmentHomeScreenBinding
import com.example.mynotes.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeScreenFragment: Fragment(), HomeScreenRecyclerAdapter.INotesAdapter {

    private lateinit var binding: FragmentHomeScreenBinding

     private val noteViewModel: NoteViewModel by viewModels()

    val homeScreenRecyclerAdapter = HomeScreenRecyclerAdapter(this)

    @Inject
    lateinit var appInfoDialog: AppInfoDialogFragment

    private var isSelected = false

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if(isSelected) {
                homeScreenRecyclerAdapter.deselectItem()
                isSelected = false
            }
            else {
                activity?.finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackClick()
    }
    private fun onBackClick() {
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        binding.recyclerViewHomeScreen.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewHomeScreen.adapter = homeScreenRecyclerAdapter

        prepareRecyclerView()

        binding.floatingButtonHomeScreen.setOnClickListener{
            val demoList= Note("","",0)
            findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToAddNewNoteEditorFragment(demoList))
        }

        binding.buttonSearchHomeScreen.setOnClickListener{
            findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToSearchScreenFragment())
        }

        binding.buttonAppInfoHomeScreen.setOnClickListener{
            appInfoDialog.show(childFragmentManager, WarningDialogFragment::class.java.simpleName)
        }

        return binding.root
    }


    /**
     * open note in parameter
     */
    override fun onClickOpenNote(note: Note) {
        findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToAddNewNoteEditorFragment(note))
    }

    /**
     * delete note in parameter
     */
    override fun onClickDeleteNote(note: Note) {
        noteViewModel.delete(note)
        homeScreenRecyclerAdapter.deselectItem()
    }

    override fun isItemSelected() {
        isSelected = true
    }

    private fun prepareRecyclerView(){
        noteViewModel.allNotesList.observe(requireActivity()) {
            if (it.isNotEmpty()) {
                binding.imageEmptyHomeScreen.visibility = View.GONE
                binding.textViewCreateNoteHomeScreen.visibility = View.GONE
                homeScreenRecyclerAdapter.updateList(it)
            }
            else{
                binding.imageEmptyHomeScreen.visibility = View.VISIBLE
                binding.textViewCreateNoteHomeScreen.visibility = View.VISIBLE
                homeScreenRecyclerAdapter.updateList(it)
            }
        }
    }
}


