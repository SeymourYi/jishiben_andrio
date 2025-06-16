package com.example.jishiben_andrio.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jishiben_andrio.viewmodel.NoteViewModel

/**
 * 笔记编辑器屏幕
 * 
 * 用于创建新笔记或编辑现有笔记
 * 包含标题和内容输入框，以及保存按钮
 * 
 * @param viewModel 笔记视图模型，提供数据和操作方法
 * @param onNavigateBack 返回列表屏幕的回调函数
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditorScreen(
    viewModel: NoteViewModel,
    onNavigateBack: () -> Unit
) {
    // 获取当前编辑的笔记
    val currentNote = viewModel.currentNote.value
    // 使用本地状态跟踪标题和内容的变化
    var title by remember { mutableStateOf(currentNote.title) }
    var content by remember { mutableStateOf(currentNote.content) }
    
    Scaffold(
        // 顶部应用栏
        topBar = {
            TopAppBar(
                // 根据isEditing状态显示不同的标题
                title = { Text(if (viewModel.isEditing.value) "编辑笔记" else "新建笔记") },
                navigationIcon = {
                    // 返回按钮
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        // 浮动保存按钮
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // 保存笔记内容
                    viewModel.currentNote.value = currentNote.copy(
                        title = title,
                        content = content
                    )
                    viewModel.saveNote()
                    onNavigateBack()
                },
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "保存笔记"
                )
            }
        }
    ) { paddingValues ->
        // 主内容区域
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // 标题输入框
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("标题") },
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 内容输入框，占用剩余空间
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                label = { Text("内容") },
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }
    }
} 