package com.example.mynotes.ui.homescreen

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.data.Note
import com.example.mynotes.databinding.SingleNoteViewBinding

class HomeScreenRecyclerAdapter (private val listener: INotesAdapter) :
    RecyclerView.Adapter<HomeScreenRecyclerAdapter.NoteViewHolder>() {

    private val noteList = ArrayList<Note>()

    private var selectedNotePosition = -1

    private var colorListNumber = 0

    inner class NoteViewHolder(private val binding: SingleNoteViewBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentNote: Note, iNotesAdapter: INotesAdapter) {
            binding.apply {
                note = currentNote
                textViewTitleSingleNoteView.setTextColor(Color.BLACK)
                singleNoteLl.setBackgroundResource(R.drawable.single_view_shape)
                imageViewDeleteSingleNoteView.visibility = View.GONE
            }
            singleItemViewColor()
            itemView.setOnLongClickListener {
                if (selectedNotePosition == -1) {
                    selectedNotePosition = adapterPosition
                    iNotesAdapter.isItemSelected()
                    binding.apply {
                        singleNoteLl.setBackgroundResource(R.drawable.delete_note_shape)
                        textViewTitleSingleNoteView.visibility = View.INVISIBLE
                        imageViewDeleteSingleNoteView.visibility = View.VISIBLE
                        singleNoteLl.setOnClickListener {
                            iNotesAdapter.onClickDeleteNote(currentNote)
                            singleNoteLl.setBackgroundResource(R.drawable.single_view_shape)
                            textViewTitleSingleNoteView.visibility = View.VISIBLE
                            imageViewDeleteSingleNoteView.visibility = View.GONE
                            selectedNotePosition = -1
                        }
                    }
                }else{
                    Toast.makeText(context.applicationContext, "not possible", Toast.LENGTH_SHORT).show()
                }
                return@setOnLongClickListener true
            }
            binding.imageViewDeleteSingleNoteView.visibility = View.GONE
            binding.textViewTitleSingleNoteView.visibility = View.VISIBLE
            itemView.setOnClickListener {
                iNotesAdapter.onClickOpenNote(currentNote)
            }
        }

        private fun singleItemViewColor() {
            val colorList = context.resources.getIntArray(R.array.rainbow)
            val itemBackground = binding.singleNoteLl.background
            if(colorListNumber<6){
                val randomColor = colorList[colorListNumber]
                itemBackground.setTint(randomColor)
                colorListNumber++
            }else{
                colorListNumber = 0
                singleItemViewColor()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = SingleNoteViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = noteList[position]
        holder.bind(currentNote, iNotesAdapter = listener)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun deselectItem(){
        notifyItemChanged(selectedNotePosition)
        selectedNotePosition = -1
    }

    fun updateList(newList: List<Note>) {
        noteList.clear()
        noteList.addAll(newList)
        notifyItemRangeChanged(0,newList.size+1)
    }

    interface INotesAdapter {
        fun onClickOpenNote(note: Note)
        fun onClickDeleteNote(note: Note)
        fun isItemSelected()
    }

}