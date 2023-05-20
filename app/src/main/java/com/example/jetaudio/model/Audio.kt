package com.example.jetaudio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audio")
data class Audio(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val image: Int,
    val title: String,
    val description: String,
    val detail: String,
)