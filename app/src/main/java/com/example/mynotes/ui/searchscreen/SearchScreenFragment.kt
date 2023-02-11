package com.example.mynotes.ui.searchscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotes.data.Note
import com.example.mynotes.databinding.FragmentSearchScreenBinding
import com.example.mynotes.viewmodel.NoteViewModel


class SearchScreenFragment: Fragment(), SearchRecyclerAdapter.INotesAdapter,androidx.appcompat.widget.SearchView.OnQueryTextListener{

    lateinit var binding: FragmentSearchScreenBinding
    private val noteViewModel: NoteViewModel by viewModels()

    private lateinit var searchRecyclerAdapter: SearchRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchScreenBinding.inflate(inflater, container, false)

        binding.recyclerViewSearchFragment.layoutManager = LinearLayoutManager(requireContext())
        searchRecyclerAdapter = SearchRecyclerAdapter(this)

        binding.recyclerViewSearchFragment.adapter = searchRecyclerAdapter

        binding.editTextSearchBarSearchFragment.setOnQueryTextListener(this)

        return binding.root
    }

    override fun onItemClickOpenNote(note: Note) {
        findNavController().navigate(SearchScreenFragmentDirections.actionSearchScreenFragmentToAddNewNoteEditorFragment(note))
    }

    override fun onQueryTextSubmit(newText: String?): Boolean {
        if(newText!=null) {
            searchDataBase(newText)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText!=null) {
            searchDataBase(newText)
        }
        return true
    }

    private fun searchDataBase(input: String) {
        noteViewModel.search(input).observe(requireActivity()) {
            if (it.isNotEmpty()) {
                binding.recyclerViewSearchFragment.visibility = View.VISIBLE
                binding.searchNotFoundImageSearchFragment.visibility = View.INVISIBLE
                binding.textViewSearchNotFound.visibility = View.INVISIBLE
                searchRecyclerAdapter.updateList(it)
            } else {
                binding.searchNotFoundImageSearchFragment.visibility = View.VISIBLE
                binding.textViewSearchNotFound.visibility = View.VISIBLE
                binding.recyclerViewSearchFragment.visibility = View.INVISIBLE
            }
        }
    }

}