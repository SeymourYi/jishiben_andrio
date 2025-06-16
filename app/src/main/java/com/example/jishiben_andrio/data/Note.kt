package com.example.jishiben_andrio.data

import java.util.Date
import java.util.UUID

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val content: String = "",
    val createdDate: Date = Date(),
    val lastModifiedDate: Date = Date()
) 