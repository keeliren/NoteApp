package com.keeliren.noteapp.dataobjects


class NotesRepository(private val notesDao: NotesDao) {

    val allNotes = notesDao.retrieveAllNotes()

    suspend fun insert(note : NoteData){
    notesDao.insert(note)
    }

    suspend fun update(note : NoteData){
        notesDao.update(note)

    }
}