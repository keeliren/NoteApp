package com.keeliren.noteapp.dataobjects

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(NoteData::class), version = 1, exportSchema = false)
 abstract class NotesRoomDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
    companion object {
        @Volatile
        private var INSTANCE: NotesRoomDatabase? = null
        fun getDatabase(context: Context, scope : CoroutineScope) : NotesRoomDatabase {
            return INSTANCE ?: synchronized(this){

                val instance = Room.databaseBuilder(context,
                    NotesRoomDatabase::class.java,"notes_database"
                    ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

