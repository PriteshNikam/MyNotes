package com.example.mynotes.ui.searchscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.data.Note
import com.example.mynotes.databinding.SingleNoteViewBinding

class SearchRecyclerAdapter(private val listener: INotesAdapter):
    RecyclerView.Adapter<SearchRecyclerAdapter.NoteViewHolder>(){

    private val searchList = ArrayList<Note>()

    // single_view argument.
    class NoteViewHolder(private val binding: SingleNoteViewBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(viewData:Note, iNotesAdapter: INotesAdapter) {
            val itemBackground = binding.singleNoteLl.background
            itemBackground.setTint(binding.singleNoteLl.resources.getColor(R.color.app_background_light_color,null))
            binding.note = viewData
            itemView.setOnClickListener{
                iNotesAdapter.onItemClickOpenNote(viewData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = SingleNoteViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(binding)
    }

    // set data with view
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = searchList[position]
        holder.bind(currentNote, iNotesAdapter = listener)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    fun updateList(newList: List<Note>){ // notify adapter that some changes has occurred.
        searchList.clear()
        searchList.addAll(newList)
        notifyDataSetChanged()
    }

    interface  INotesAdapter{ // to handle specific clicks
        fun onItemClickOpenNote(note: Note)
    }

}