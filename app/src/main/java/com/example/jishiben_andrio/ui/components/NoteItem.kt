package com.example.jishiben_andrio.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.jishiben_andrio.data.Note
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * 笔记列表项组件
 * 
 * 在笔记列表中显示单个笔记的卡片组件，展示笔记标题、内容预览和修改时间
 * 支持点击进入编辑和删除操作
 * 
 * @param note 要显示的笔记对象
 * @param onNoteClick 点击笔记卡片时的回调，参数为被点击的笔记
 * @param onDeleteClick 点击删除按钮时的回调，参数为要删除的笔记ID
 */
@Composable
fun NoteItem(
    note: Note,
    onNoteClick: (Note) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    // 设置日期格式化器，用于显示最后修改时间
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onNoteClick(note) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 标题和时间信息
                Column(modifier = Modifier.weight(1f)) {
                    // 笔记标题，如果为空则显示"无标题"
                    Text(
                        text = if (note.title.isNotBlank()) note.title else "无标题",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    // 显示最后修改时间
                    Text(
                        text = dateFormat.format(note.lastModifiedDate),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // 删除按钮
                IconButton(onClick = { onDeleteClick(note.id) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "删除笔记",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
            
            // 仅当笔记内容不为空时显示内容预览
            if (note.content.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = note.content,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
} 