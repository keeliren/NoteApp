package com.keeliren.noteapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.keeliren.noteapp.databinding.ActivityMainBinding
import com.keeliren.noteapp.databinding.FragmentAllNoteBinding
import com.keeliren.noteapp.entity.Note

class AllNoteFragment : Fragment() {

    private lateinit var binding: FragmentAllNoteBinding

    companion object {
        @JvmStatic
        fun newInstance() = AllNoteFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAllNoteBinding.inflate(layoutInflater)

        binding.notesRv.apply{

            var noteitemClickListener = object : NotesRecyclerView.NoteItemClickListener{


                override fun onClick(v: View?, noteListItem: Int) {

                    var appContext: NotesApplication = activity!!.applicationContext as NotesApplication
                    appContext.notesList[noteListItem].favorite = !appContext.notesList[noteListItem].favorite
                   // (binding.notesRv.adapter as NotesRecyclerView).updateData(appContext.notesList)

                    (activity as MainActivity).updateNoteItem(appContext.notesList[noteListItem])
                    binding.notesRv.adapter?.notifyDataSetChanged()
                }
            }

            if(activity!=null) {
                var appContext: NotesApplication = activity!!.applicationContext as NotesApplication
                adapter = NotesRecyclerView(appContext.notesList,noteitemClickListener)
            }
            else {
                adapter = NotesRecyclerView(emptyList(),noteitemClickListener)
            }
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))

        }

        return binding.root
    }

    fun refreshList(){
        var appContext: NotesApplication = activity!!.applicationContext as NotesApplication
        (binding.notesRv.adapter as NotesRecyclerView).updateData(appContext.notesList)
        binding.notesRv.adapter?.notifyDataSetChanged()

    }

}