package com.keeliren.noteapp.dataobjects

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NotesViewModel(val repo: NotesRepository) : ViewModel() {

    val allNotes: LiveData<List<NoteData>> = repo.allNotes.asLiveData()


    fun insert(noteItem : NoteData) = viewModelScope.launch(Dispatchers.IO){
        repo.insert(noteItem)
    }
    fun update(noteItem : NoteData) = viewModelScope.launch(Dispatchers.IO){
        repo.update(noteItem)
    }


}


class NotesViewModelFactory(private val repo : NotesRepository):ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {


            return NotesViewModel(repo) as T
        }

        throw IllegalArgumentException("Unknown viewmodel class")
    }

}

