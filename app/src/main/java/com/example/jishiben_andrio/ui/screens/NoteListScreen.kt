package com.example.jishiben_andrio.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jishiben_andrio.data.Note
import com.example.jishiben_andrio.ui.components.NoteItem
import com.example.jishiben_andrio.viewmodel.NoteViewModel

/**
 * 笔记列表屏幕
 * 
 * 应用的主屏幕，展示所有保存的笔记列表
 * 提供新建笔记和删除笔记的功能
 * 
 * @param viewModel 笔记视图模型，提供数据和操作方法
 * @param onNavigateToEditor 导航到编辑器屏幕的回调函数
 */
@Composable
fun NoteListScreen(
    viewModel: NoteViewModel,
    onNavigateToEditor: () -> Unit
) {
    // 从ViewModel获取笔记列表并观察变化
    val notes by viewModel.notes.collectAsState()
    // 记录当前待删除的笔记，用于显示确认对话框
    var noteToDelete by remember { mutableStateOf<Note?>(null) }
    
    Box(modifier = Modifier.fillMaxSize()) {
        if (notes.isEmpty()) {
            // 显示空状态提示
            Text(
                text = "点击右下角的加号按钮创建笔记",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline
            )
        } else {
            // 显示笔记列表
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 100.dp, start = 8.dp, end = 8.dp, top = 8.dp)
            ) {
                items(notes) { note ->
                    NoteItem(
                        note = note,
                        onNoteClick = { 
                            // 点击笔记时，设置当前编辑的笔记并导航到编辑器
                            viewModel.setEditingNote(it)
                            onNavigateToEditor()
                        },
                        onDeleteClick = { 
                            // 点击删除按钮时，设置待删除的笔记，触发确认对话框
                            noteToDelete = note 
                        }
                    )
                }
            }
        }
        
        // 添加新笔记的浮动按钮
        FloatingActionButton(
            onClick = {
                // 清除当前编辑状态，准备创建新笔记
                viewModel.clearEditingNote()
                onNavigateToEditor()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 24.dp, end = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "新建笔记"
            )
        }
        
        // 删除确认对话框
        // 只有当noteToDelete不为null时才显示
        noteToDelete?.let { note ->
            AlertDialog(
                onDismissRequest = { noteToDelete = null },
                title = { Text("确认删除") },
                text = { Text("确定要删除这条笔记吗？此操作无法撤销。") },
                confirmButton = {
                    Button(
                        onClick = {
                            // 确认删除
                            viewModel.deleteNote(note.id)
                            noteToDelete = null
                        }
                    ) {
                        Text("删除")
                    }
                },
                dismissButton = {
                    Button(onClick = { noteToDelete = null }) {
                        Text("取消")
                    }
                }
            )
        }
    }
} 