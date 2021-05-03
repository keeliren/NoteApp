package com.keeliren.noteapp.entity


import java.io.Serializable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Note : Serializable{

    private val df: DateFormat = SimpleDateFormat("dd MMM")
    var date: String
        private set
    var id: Int = 0
        private set
    var text: String
    var title: String
    var favorite: Boolean = false

    constructor(id: Int, title: String, text: String, favourite: Boolean) {
        this.id = id
        this.title = title
        this.text = text
        this.date = df.format(Calendar.getInstance().time)
        this.favorite = favourite
    }

    constructor(id: Int, title: String, text: String, favourite: Boolean, mockDate : Date) {
        this.id = id
        this.title = title
        this.text = text

        this.date = df.format(mockDate)
        this.favorite = favourite
    }

    override fun toString(): String {
        return "id : ${id}\n" +
                "Title : ${this.title}\n" +
                "Text : ${this.text}\n" +
                "Date : ${this.date}\n" +
                "Fav : ${this.favorite}"

    }



}




