package com.keeliren.noteapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.keeliren.noteapp.databinding.DateItemLayoutBinding
import com.keeliren.noteapp.databinding.NoteItemLayoutBinding

import com.keeliren.noteapp.entity.Note
import com.keeliren.noteapp.entity.NoteListItem


class NotesRecyclerView(dataset: List<Note>, noteitemClickListener: NoteItemClickListener?) :
    RecyclerView.Adapter<NotesRecyclerView.NoteHolder>() {

    private val DATE_ITEM = 1
    private val NOTE_ITEM = 2
    private var _noteDataSet = arrayListOf<NoteListItem>()
    private var noteitemClickListener: NoteItemClickListener?
    init{

        convertNoteDataToNoteListItem(dataset)
        this.noteitemClickListener = noteitemClickListener
    }

    private fun convertNoteDataToNoteListItem(dataset: List<Note>){


        var dateStr : String?=null
        for((idx,note) in dataset.withIndex()){

            var note_item = NoteListItem()
            if(dateStr != note.date) {
                dateStr = note.date
                var date_item = NoteListItem()
                date_item.type = NoteListItem.LIST_TYPE_DATE
                date_item.date = note.date

                _noteDataSet.add(date_item)
            }

            note_item.type = NoteListItem.LIST_TYPE_NOTE
            note_item.note = note
            note_item.appListID = idx
            _noteDataSet.add(note_item)

        }


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {

       if(viewType == NOTE_ITEM) {
           val binding =
               NoteItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

           return NoteHolder(binding,viewType)
       }
        else{

           val binding =
               DateItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

           return NoteHolder(binding,viewType)
        }
    }

    override fun getItemCount(): Int {
        return _noteDataSet.count()
    }


    //Update dataset with new data dataset
    fun updateData(newDataset: List<Note>) {

        _noteDataSet.clear()

        convertNoteDataToNoteListItem(newDataset)

        notifyDataSetChanged()


    }

    override fun getItemViewType(position: Int): Int
    {
        
        if(_noteDataSet[position].type == NoteListItem.LIST_TYPE_DATE){


            return DATE_ITEM
        }
        else{

            return NOTE_ITEM
        }

    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {

        holder.bindNoteItem(_noteDataSet[position])
    }


    inner class NoteHolder(val view: ViewBinding, val type : Int) : RecyclerView.ViewHolder(view.root),
        View.OnClickListener {

        var noteListItem: NoteListItem? = null

        init {

            if(type == NOTE_ITEM)
                view.root.setOnClickListener(this)

        }


        fun bindNoteItem(noteListItem: NoteListItem) {


            this.noteListItem = noteListItem

            if(this.noteListItem?.type == NoteListItem.LIST_TYPE_NOTE) {
                var v = view as NoteItemLayoutBinding
                v.date.text = noteListItem.note?.date

                v.title.text = noteListItem.note?.title

                v.text.text = noteListItem.note?.text

                if (noteListItem.note!!.favorite) {

                    v.favourite.setImageResource(android.R.drawable.star_on)
                } else {

                    v.favourite.setImageResource(android.R.drawable.star_off)
                }

            }
            else{
                var v = view as DateItemLayoutBinding

                v.date.text = noteListItem.date
            }
        }


        override fun onClick(v: View?) {
            val context = itemView.context

            if (noteListItem != null) {

                noteitemClickListener?.onClick(v, this!!.noteListItem!!.appListID!!)
                Toast.makeText(
                    context,
                    "${noteListItem!!.note?.date}\n${noteListItem!!.note?.title}\n${noteListItem!!.note?.text}",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }

    }

    interface NoteItemClickListener {

        fun onClick(v: View?, listIdx: Int)

    }

}