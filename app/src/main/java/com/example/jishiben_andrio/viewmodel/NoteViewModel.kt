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

/**
 * 笔记视图模型类
 * 
 * 作为应用中数据层和UI层之间的桥梁，处理笔记数据的业务逻辑
 * 管理笔记列表状态和当前操作的笔记状态
 * 
 * @param application 应用实例，用于创建Repository
 */
class NoteViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * 笔记数据仓库实例
     */
    private val repository = NoteRepository(application)
    
    /**
     * 内部的笔记列表状态流
     */
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    
    /**
     * 对外暴露的只读笔记列表状态流
     */
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()
    
    /**
     * 当前正在编辑的笔记状态
     */
    val currentNote = mutableStateOf(Note())
    
    /**
     * 标记当前是否处于编辑模式
     * true表示正在编辑已有笔记，false表示创建新笔记
     */
    val isEditing = mutableStateOf(false)
    
    /**
     * 初始化代码块，加载所有笔记
     */
    init {
        loadNotes()
    }
    
    /**
     * 从数据仓库加载所有笔记
     * 并按最后修改时间降序排序
     */
    private fun loadNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            _notes.value = repository.getAllNotes().sortedByDescending { it.lastModifiedDate }
        }
    }
    
    /**
     * 保存当前笔记
     * 
     * 只有当标题或内容不为空时才会保存
     * 如果是编辑模式，更新最后修改时间
     * 如果是新建模式，同时设置创建时间和最后修改时间
     */
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
    
    /**
     * 删除指定ID的笔记
     * 
     * @param noteId 要删除的笔记ID
     */
    fun deleteNote(noteId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(noteId)
            loadNotes()
        }
    }
    
    /**
     * 设置当前正在编辑的笔记
     * 
     * @param note 要编辑的笔记对象
     */
    fun setEditingNote(note: Note) {
        currentNote.value = note
        isEditing.value = true
    }
    
    /**
     * 清除当前编辑状态
     * 重置当前笔记为空白笔记，用于创建新笔记
     */
    fun clearEditingNote() {
        currentNote.value = Note()
        isEditing.value = false
    }
} 