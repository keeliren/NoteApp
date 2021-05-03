package com.keeliren.noteapp

import android.app.Application
import com.keeliren.noteapp.dataobjects.NotesRepository
import com.keeliren.noteapp.dataobjects.NotesRoomDatabase
import com.keeliren.noteapp.entity.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.util.*
import kotlin.random.Random

class NotesApplication() : Application() {

    var notesList = arrayListOf<Note>()
    val appScope = CoroutineScope(SupervisorJob())
    val db by lazy { NotesRoomDatabase.getDatabase(this,appScope)}
    val repo by lazy{ NotesRepository(db!!.notesDao()) }

    init {

       // notesList.addAll(createMockData())


    }

    fun getFavNotes(): ArrayList<Note> {

        var favList = arrayListOf<Note>()
        for (note in notesList) {

            if (note.favorite) {

                favList.add(note)
            }


        }

        return favList
    }


    fun createMockData(): ArrayList<Note> {

        var dummyText =
            "Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean. A small river named Duden flows by their place and supplies it with the necessary regelialia. It is a paradisematic country, in which roasted parts of sentences fly into your mouth. Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic life One day however a small line of blind text by the name of Lorem Ipsum decided to leave for the far World of Grammar. The Big Oxmox advised her not to do so, because there were thousands of bad Commas, wild Question Marks and devious Semikoli, but the Little Blind Text didn’t listen. She packed her seven versalia, put her initial into the belt and made herself on the way. When she reached the first hills of the Italic Mountains, she had a last view back on the skyline of her hometown Bookmarksgrove, the headline of Alphabet Village and the subline of her own road, the Line Lane. Pityful a rethoric question ran over her cheek, then"

        var retList = arrayListOf<Note>()
        var size = Random.nextInt(1, 10)

        var dateAdded = Calendar.getInstance()
        repeat(size) {

            var titleSliceStart = Random.nextInt(1, dummyText.length - 1)


            var titleSliceEnd =
                if ((titleSliceStart + 5) > dummyText.length) dummyText.length else titleSliceStart + 5

            var title = dummyText.substring(titleSliceStart, titleSliceEnd)

            var textSliceStart = Random.nextInt(1, dummyText.length - 1)
            var textSliceEnd = Random.nextInt(textSliceStart, dummyText.length - 1)

            var text = dummyText.substring(textSliceStart, textSliceEnd)

            var favourite = Random.nextBoolean()

            var isSameDay = Random.nextBoolean()

            if (it == 0) {

                retList.add(Note(it, title, text, favourite, dateAdded.time))
            } else {
                if (!isSameDay) {

                    dateAdded.add(Calendar.DATE, -1)
                    retList.add(Note(it, title, text, favourite, dateAdded.time))
                } else {

                    retList.add(Note(it, title, text, favourite, dateAdded.time))
                }
            }


        }


        return retList
    }
}