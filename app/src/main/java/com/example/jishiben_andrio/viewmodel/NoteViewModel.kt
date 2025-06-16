package com.example.jishiben_andrio.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.jishiben_andrio.data.Note
import com.example.jishiben_andrio.data.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = NoteRepository(application)
    
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()
    
    val currentNote = mutableStateOf(Note())
    val isEditing = mutableStateOf(false)
    
    init {
        loadNotes()
    }
    
    private fun loadNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            _notes.value = repository.getAllNotes().sortedByDescending { it.lastModifiedDate }
        }
    }
    
    fun saveNote() {
        if (currentNote.value.title.isNotBlank() || currentNote.value.content.isNotBlank()) {
            val noteToSave = if (isEditing.value) {
                currentNote.value.copy(lastModifiedDate = Date())
            } else {
                currentNote.value.copy(
                    createdDate = Date(),
                    lastModifiedDate = Date()
                )
            }
            
            viewModelScope.launch(Dispatchers.IO) {
                repository.saveNote(noteToSave)
                loadNotes()
            }
        }
    }
    
    fun deleteNote(noteId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(noteId)
            loadNotes()
        }
    }
    
    fun setEditingNote(note: Note) {
        currentNote.value = note
        isEditing.value = true
    }
    
    fun clearEditingNote() {
        currentNote.value = Note()
        isEditing.value = false
    }
} 