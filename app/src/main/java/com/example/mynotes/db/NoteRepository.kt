package com.example.mynotes.db

import androidx.lifecycle.LiveData
import com.example.mynotes.data.Note

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes : LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insertNote(note: Note){
        noteDao.insert(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.delete(note)
    }

    suspend fun updateNote(updateNote:Note){
        noteDao.noteUpdate(updateNote)
    }


    fun searchNote(searchQuery: String):LiveData<List<Note>>{
        return noteDao.noteSearch(searchQuery)
    }

}