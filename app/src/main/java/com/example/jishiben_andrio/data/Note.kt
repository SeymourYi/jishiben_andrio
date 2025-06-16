package com.example.jishiben_andrio.data

import java.util.Date
import java.util.UUID

/**
 * 笔记数据模型类
 * 
 * 该类表示记事本应用中的一条笔记记录，包含笔记的所有基本信息
 * 
 * @property id 笔记的唯一标识符，默认使用UUID自动生成
 * @property title 笔记的标题
 * @property content 笔记的内容文本
 * @property createdDate 笔记的创建时间
 * @property lastModifiedDate 笔记的最后修改时间
 */
data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val content: String = "",
    val createdDate: Date = Date(),
    val lastModifiedDate: Date = Date()
) 