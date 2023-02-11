package com.example.mynotes.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mynotes.data.Note

@Dao
interface NoteDao {

    @Query( "Select * from notes_table order by noteTime DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note:Note)

    @Update
    suspend fun noteUpdate(note: Note)

    @Query("SELECT * FROM notes_table WHERE noteTitle LIKE :searchQuery OR noteDescription LIKE :searchQuery")
    fun noteSearch(searchQuery: String):LiveData<List<Note>>

}