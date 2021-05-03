package com.keeliren.noteapp.dataobjects

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao{
    @Query("Select * from notes_table ORDER BY ID DESC")
    fun retrieveAllNotes() : Flow<List<NoteData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newNotes : NoteData)

    @Update
    fun update(newNote : NoteData)

}
