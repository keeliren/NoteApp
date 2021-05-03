package com.keeliren.noteapp.dataobjects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes_table")
data class NoteData(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id : Int,

                @ColumnInfo(name="title")val title: String,

                @ColumnInfo(name="text")val text : String,

                @ColumnInfo(name="date")val date : String,

                @ColumnInfo(name="favorite")val favorite : Boolean
                )




