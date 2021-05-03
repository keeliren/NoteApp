package com.keeliren.noteapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.keeliren.noteapp.databinding.FragmentFavNotesBinding
import com.keeliren.noteapp.entity.Note


class FavNotesFragment : Fragment() {

    private lateinit var binding: FragmentFavNotesBinding

    companion object {
        @JvmStatic
        fun newInstance() = FavNotesFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavNotesBinding.inflate(layoutInflater)

        binding.favNotesRv.apply{

            var noteitemClickListener = object : NotesRecyclerView.NoteItemClickListener{


                override fun onClick(v: View?, noteListItem: Int) {

                    var appContext: NotesApplication = activity!!.applicationContext as NotesApplication
                    var fn = appContext.getFavNotes()
                    fn[noteListItem].favorite = !fn[noteListItem].favorite
                    (activity as MainActivity).updateNoteItem(fn[noteListItem])

                    binding.favNotesRv.adapter?.notifyDataSetChanged()
                }
            }

            if(activity!=null) {
                var appContext: NotesApplication = activity!!.applicationContext as NotesApplication
                adapter = NotesRecyclerView(appContext.getFavNotes(),noteitemClickListener)
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
        (binding.favNotesRv.adapter as NotesRecyclerView).updateData(appContext.getFavNotes())
        binding.favNotesRv.adapter?.notifyDataSetChanged()

    }


}