package com.example.marsphotos.data
import kotlinx.coroutines.flow.Flow

class OfflineNewsRepository(private val newDao: NewDao) : NewsRepository {
    override fun getNewStream(category:String): Flow<List<New?>> = newDao.getNewStream(category)

    override suspend fun insertNew(newsItem: New) = newDao.insert(newsItem)
}