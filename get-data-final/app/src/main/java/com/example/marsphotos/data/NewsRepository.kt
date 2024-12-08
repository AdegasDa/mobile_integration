package com.example.marsphotos.data

import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun insertNew(newsItem: New)

    fun getNewStream(category: String): Flow<List<New?>>
}