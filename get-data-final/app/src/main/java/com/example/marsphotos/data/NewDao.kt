package com.example.marsphotos.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(newsItem: New)

    @Query("SELECT * FROM news WHERE category = :category")
    fun getNewStream(category: String): Flow<List<New>>
}