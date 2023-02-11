package com.example.mynotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mynotes.data.Note
import com.example.mynotes.db.NoteDatabase
import com.example.mynotes.db.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = NoteDatabase.getDatabase(application).getNoteDao()
    private val noteRepository = NoteRepository(dao)

    var allNotesList: LiveData<List<Note>> = noteRepository.allNotes

    fun insert(mNoteTitle: String, mNoteDescription: String, mNoteDate: Long) {
        val demoList = Note(mNoteTitle, mNoteDescription, mNoteDate)
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote(demoList)
        }
    }

    fun delete(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNote(note)
        }
    }

    fun update(updateNote: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.updateNote(updateNote)
        }
    }

    fun search(searchQuery: String): LiveData<List<Note>> =
        noteRepository.searchNote("%$searchQuery%")

}