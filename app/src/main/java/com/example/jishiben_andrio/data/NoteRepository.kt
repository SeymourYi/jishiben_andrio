package com.example.jishiben_andrio.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class NoteRepository(context: Context) {
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("notes_preferences", Context.MODE_PRIVATE)
    private val gson = Gson()
    
    companion object {
        private const val NOTES_KEY = "notes_key"
    }
    
    fun getAllNotes(): List<Note> {
        val notesJson = sharedPreferences.getString(NOTES_KEY, null) ?: return emptyList()
        val type = object : TypeToken<List<Note>>() {}.type
        return gson.fromJson(notesJson, type)
    }
    
    fun saveNote(note: Note) {
        val notes = getAllNotes().toMutableList()
        
        // Check if the note already exists
        val existingNoteIndex = notes.indexOfFirst { it.id == note.id }
        
        if (existingNoteIndex != -1) {
            // Update existing note
            val updatedNote = note.copy(lastModifiedDate = Date())
            notes[existingNoteIndex] = updatedNote
        } else {
            // Add new note
            notes.add(note)
        }
        
        // Save updated list
        val notesJson = gson.toJson(notes)
        sharedPreferences.edit().putString(NOTES_KEY, notesJson).apply()
    }
    
    fun deleteNote(noteId: String) {
        val notes = getAllNotes().toMutableList()
        notes.removeIf { it.id == noteId }
        
        // Save updated list
        val notesJson = gson.toJson(notes)
        sharedPreferences.edit().putString(NOTES_KEY, notesJson).apply()
    }
} 