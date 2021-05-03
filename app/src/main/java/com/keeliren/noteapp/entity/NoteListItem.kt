package com.keeliren.noteapp.entity

class NoteListItem {

    var type =LIST_TYPE_UNASSIGNED
    var note : Note ?=null
    var date : String ?=null
    var appListID : Int = -1
    companion object{

        val LIST_TYPE_UNASSIGNED = -1
        val LIST_TYPE_DATE = 0
        val LIST_TYPE_NOTE = 1

    }

}