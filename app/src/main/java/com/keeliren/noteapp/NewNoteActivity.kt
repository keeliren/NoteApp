package com.keeliren.noteapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.keeliren.noteapp.databinding.ActivityNewNoteBinding
import com.keeliren.noteapp.entity.Note

class NewNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addNote.setOnClickListener {

            var appContext: NotesApplication = applicationContext as NotesApplication

            var toAdd = true
            if (binding.titleTV.text.isBlank()) {

                binding.titleTV.setError("Kindly enter a title")
                toAdd = false
            }

            if (binding.descriptionTV.text.isBlank()) {

                binding.titleTV.setError("Kindly enter a description")
                toAdd = false
            }
            if (toAdd) {

                var retIntent = Intent()
                retIntent.putExtra("data", Note(0, binding.titleTV.text.toString(), binding.descriptionTV.text.toString(), false))
                setResult(Activity.RESULT_OK,retIntent)
                finish()

            }
        }
    }
}