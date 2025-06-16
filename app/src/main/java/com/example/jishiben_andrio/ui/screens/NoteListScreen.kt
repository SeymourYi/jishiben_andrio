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

@Composable
fun NoteListScreen(
    viewModel: NoteViewModel,
    onNavigateToEditor: () -> Unit
) {
    val notes by viewModel.notes.collectAsState()
    var noteToDelete by remember { mutableStateOf<Note?>(null) }
    
    Box(modifier = Modifier.fillMaxSize()) {
        if (notes.isEmpty()) {
            // Show empty state
            Text(
                text = "点击右下角的加号按钮创建笔记",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline
            )
        } else {
            // Show notes list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 100.dp, start = 8.dp, end = 8.dp, top = 8.dp)
            ) {
                items(notes) { note ->
                    NoteItem(
                        note = note,
                        onNoteClick = { 
                            viewModel.setEditingNote(it)
                            onNavigateToEditor()
                        },
                        onDeleteClick = { noteToDelete = note }
                    )
                }
            }
        }
        
        // Add button
        FloatingActionButton(
            onClick = {
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
        
        // Delete confirmation dialog
        noteToDelete?.let { note ->
            AlertDialog(
                onDismissRequest = { noteToDelete = null },
                title = { Text("确认删除") },
                text = { Text("确定要删除这条笔记吗？此操作无法撤销。") },
                confirmButton = {
                    Button(
                        onClick = {
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