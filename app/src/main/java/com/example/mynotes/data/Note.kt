package com.example.mynotes.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notes_table")
data class Note(
    var noteTitle: String,
    var noteDescription: String,
    var noteTime: Long
):Parcelable {
    @PrimaryKey(autoGenerate = true)
    var noteId = 0
}

