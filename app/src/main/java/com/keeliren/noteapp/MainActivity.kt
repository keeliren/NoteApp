package com.keeliren.noteapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.keeliren.noteapp.databinding.ActivityMainBinding
import com.keeliren.noteapp.dataobjects.NoteData
import com.keeliren.noteapp.dataobjects.NotesViewModel
import com.keeliren.noteapp.dataobjects.NotesViewModelFactory
import com.keeliren.noteapp.entity.Note

class MainActivity : AppCompatActivity() {

    private var NOTE_CREATE_REQUEST_CODE = 10001
    private lateinit var binding: ActivityMainBinding
    private var currentFragmentDisplayed: Fragment? = null
    private var allNotesFragment: AllNoteFragment? = null
    private var favNotesFragment: FavNotesFragment? = null
    private val notesViewModel: NotesViewModel by viewModels() {

        NotesViewModelFactory((application as NotesApplication).repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var appContext : NotesApplication = applicationContext as NotesApplication

        var list = appContext.notesList

        for(i in list){

            Log.d("debug",i.toString())
        }

        allNotesFragment = AllNoteFragment.newInstance()

        currentFragmentDisplayed = allNotesFragment

        var frag_transaction = supportFragmentManager.beginTransaction()

        frag_transaction.add(
                R.id.main_fragment_content,
                allNotesFragment!!, "allnotesfragment"
        ).show(allNotesFragment!!)
        frag_transaction.commit()




        binding.noteListBottomNavigation.setOnNavigationItemSelectedListener {

            if (binding.noteListBottomNavigation.selectedItemId == it.itemId) {

                false
                //No need to do anything if the same item is selected
            } else {

                //Init and create fragment when selected.
                when (it.itemId) {

                    R.id.page_all_notes -> {


                        if (allNotesFragment == null) {
                            allNotesFragment = AllNoteFragment.newInstance()
                        }
                        loadFragment(allNotesFragment!!, "allnotesfragment")

                        true
                    }

                    R.id.page_fav -> {
                        if (favNotesFragment == null) {
                            favNotesFragment = FavNotesFragment.newInstance()
                        }
                        loadFragment(favNotesFragment!!, "favnotesfragment")

                        true
                    }
                    else -> {

                        false
                    }
                }
            }


        }
    }

    override fun onStart() {
        super.onStart()

        notesViewModel.allNotes.observe(this, Observer {

            var appContext : NotesApplication = applicationContext as NotesApplication

            toggleEmptyList(it.isEmpty())

            appContext.notesList.clear()
            for(noteData in it){
                appContext.notesList.add(Note(noteData.id,noteData.title,noteData.text,noteData.favorite))
            }

            if(currentFragmentDisplayed is AllNoteFragment){

                (currentFragmentDisplayed as AllNoteFragment).refreshList()
            }
            else if(currentFragmentDisplayed is FavNotesFragment){

                (currentFragmentDisplayed as FavNotesFragment).refreshList()
            }

        })

    }

    override fun onResume() {
        super.onResume()

        var appContext : NotesApplication = applicationContext as NotesApplication
        toggleEmptyList(appContext.notesList.isEmpty())

    }
    private fun loadFragment(fragment: Fragment, tag: String) {


        val frag_transaction = supportFragmentManager.beginTransaction()

        var frag = supportFragmentManager.findFragmentByTag(tag)


        if (frag == null) {

            frag_transaction.add(R.id.main_fragment_content, fragment, tag)

            frag_transaction.hide(currentFragmentDisplayed!!).show(fragment!!)

            frag_transaction.commit()

            currentFragmentDisplayed = fragment


        } else {

            frag_transaction.hide(currentFragmentDisplayed!!)
            frag_transaction.show(frag)
            frag_transaction.commit()


            currentFragmentDisplayed = frag
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode)
        {

            NOTE_CREATE_REQUEST_CODE->{

                if(resultCode == Activity.RESULT_OK) {
                    if (data!!.hasExtra("data") && data != null) {
                        var n = data?.getSerializableExtra("data") as Note
                        notesViewModel.insert(NoteData(0, n.title, n.text, n.date, n.favorite))


                    }
                }
            }


        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.addNote ->{

                var i = Intent(this,NewNoteActivity::class.java)
                startActivityForResult(i,NOTE_CREATE_REQUEST_CODE)

            }

        }

        return super.onOptionsItemSelected(item)
    }


    fun updateNoteItem( n : Note){


        notesViewModel.update(NoteData(n.id,n.title,n.text,n.date,n.favorite))

        if(favNotesFragment!=null){

            favNotesFragment!!.refreshList()

        }


        if(allNotesFragment!=null){

            allNotesFragment!!.refreshList()

        }
    }


    fun toggleEmptyList(isEmpty : Boolean){


        if(isEmpty)
        {

            binding.emptyList.visibility= View.VISIBLE
            binding.mainFragmentContent.visibility = View.GONE
            binding.noteListBottomNavigation.visibility = View.GONE

        }
        else{

            binding.emptyList.visibility= View.GONE
            binding.mainFragmentContent.visibility = View.VISIBLE
            binding.noteListBottomNavigation.visibility = View.VISIBLE

        }


    }

}