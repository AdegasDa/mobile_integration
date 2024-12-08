package com.example.marsphotos.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class New (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val newId: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String
)