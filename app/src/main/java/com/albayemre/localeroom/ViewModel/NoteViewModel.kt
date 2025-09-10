package com.albayemre.localeroom.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albayemre.localeroom.Data.Local.Entity.DataEntity
import com.albayemre.localeroom.Data.Local.Repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _noteList = MutableStateFlow<List<DataEntity>>(emptyList())
    val noteList = _noteList.asStateFlow()

    private val _currentNote = MutableStateFlow<DataEntity?>(null)
    val currentNote = _currentNote.asStateFlow()

    init {
        getAllNoteData()
    }

    fun getAllNoteData() {
        viewModelScope.launch {
            repository.getAllNotesDatabase()
                .distinctUntilChanged()
                .collect { data ->
                    _noteList.value = data
                }
        }
    }

    fun getNote(noteId: String) {
        viewModelScope.launch {
            _currentNote.value = repository.getNoteFromDatabase(noteId)
        }
    }

    fun clearCurrentNote() {
        _currentNote.value = null
    }

    fun insertNote(dataEntity: DataEntity) {
        viewModelScope.launch {
            repository.insertNoteToDatabase(dataEntity)
        }
    }

    fun updateNote(dataEntity: DataEntity) {
        viewModelScope.launch {
            repository.updateNoteToDatabase(dataEntity)
        }
    }

    fun deleteNote(dataEntity: DataEntity) {
        viewModelScope.launch {
            repository.deleteNote(dataEntity)
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            repository.deleteAllNotesFromDatabase()
        }
    }

    fun searchNote(searchText: String) {
        viewModelScope.launch {
            repository.searchNoteToDatabase(searchText).let { 
                _noteList.value = it
            }
        }
    }
}
