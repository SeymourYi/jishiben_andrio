package com.example.jishiben_andrio.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

/**
 * 笔记数据仓库类
 * 
 * 负责笔记数据的持久化存储与检索，使用SharedPreferences作为本地存储方式
 * 将笔记数据转换为JSON格式存储，支持笔记的增删改查操作
 * 
 * @param context 应用上下文，用于获取SharedPreferences实例
 */
class NoteRepository(context: Context) {
    /**
     * SharedPreferences实例，用于数据持久化存储
     */
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("notes_preferences", Context.MODE_PRIVATE)
    
    /**
     * Gson实例，用于JSON序列化和反序列化
     */
    private val gson = Gson()
    
    companion object {
        /**
         * 存储笔记列表的键名
         */
        private const val NOTES_KEY = "notes_key"
    }
    
    /**
     * 获取所有保存的笔记
     * 
     * 从SharedPreferences中读取JSON字符串并转换为笔记列表
     * 
     * @return 所有笔记的列表，如果没有笔记则返回空列表
     */
    fun getAllNotes(): List<Note> {
        val notesJson = sharedPreferences.getString(NOTES_KEY, null) ?: return emptyList()
        val type = object : TypeToken<List<Note>>() {}.type
        return gson.fromJson(notesJson, type)
    }
    
    /**
     * 保存或更新笔记
     * 
     * 如果笔记ID已存在，则更新对应笔记
     * 如果是新笔记，则添加到列表中
     * 
     * @param note 要保存的笔记对象
     */
    fun saveNote(note: Note) {
        val notes = getAllNotes().toMutableList()
        
        // 检查笔记是否已存在
        val existingNoteIndex = notes.indexOfFirst { it.id == note.id }
        
        if (existingNoteIndex != -1) {
            // 更新已有笔记
            val updatedNote = note.copy(lastModifiedDate = Date())
            notes[existingNoteIndex] = updatedNote
        } else {
            // 添加新笔记
            notes.add(note)
        }
        
        // 保存更新后的列表
        val notesJson = gson.toJson(notes)
        sharedPreferences.edit().putString(NOTES_KEY, notesJson).apply()
    }
    
    /**
     * 删除指定ID的笔记
     * 
     * @param noteId 要删除的笔记ID
     */
    fun deleteNote(noteId: String) {
        val notes = getAllNotes().toMutableList()
        notes.removeIf { it.id == noteId }
        
        // 保存更新后的列表
        val notesJson = gson.toJson(notes)
        sharedPreferences.edit().putString(NOTES_KEY, notesJson).apply()
    }
} 