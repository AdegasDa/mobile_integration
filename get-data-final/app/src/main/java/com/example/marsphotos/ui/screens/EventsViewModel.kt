/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.marsphotos.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.data.New
import com.example.marsphotos.data.NewsRepository
import com.example.marsphotos.model.Events
import com.example.marsphotos.network.EventsApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface NewsUiState {
    data class Success(val sources: List<Events>) : NewsUiState
    object Error : NewsUiState
    object Loading : NewsUiState
}

class EventsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    var eventsUiState: NewsUiState by mutableStateOf(NewsUiState.Loading)
        private set

    init {
        fetchAndStoreNewsArticles()
    }

    fun fetchAndStoreNewsArticles() {
        viewModelScope.launch {
            eventsUiState = NewsUiState.Loading
            try {
                val response = EventsApi.retrofitService.getTopHeadlines()
                response.sources.forEach { event ->
                    val news = New(
                        newId = event.id,
                        name = event.name,
                        description = event.description,
                        url = event.url,
                        category = event.category,
                        language = event.language,
                        country = event.country
                    )
                    Log.d("EventsViewModel", "Saving news to database: $news")
                    newsRepository.insertNew(news)
                }
            } catch (e: IOException) {
                Log.e("EventsViewModel", "Error fetching articles: ${e.localizedMessage}", e)
                eventsUiState = NewsUiState.Error
            } catch (e: HttpException) {
                Log.e("EventsViewModel", "HTTP error fetching articles: ${e.localizedMessage}", e)
                eventsUiState = NewsUiState.Error
            }
        }
    }

    fun getNewsByCategory(category: String) {
        viewModelScope.launch {
            Log.d("EventsViewModel", "Fetching news for category: $category")
            newsRepository.getNewStream(category).collect { newsList ->
                if (newsList.isNotEmpty()) {
                    Log.d("EventsViewModel", "Retrieved ${newsList.size} news items from database.")
                    val eventsList = newsList.map { it!!.toEvent() }
                    eventsUiState = NewsUiState.Success(eventsList)
                } else {
                    Log.d("EventsViewModel", "No news found for category: $category")
                    eventsUiState = NewsUiState.Error
                }
            }
        }
    }

    private fun New.toEvent() = Events(
        id = id.toString(),
        name = name,
        description = description,
        url = url,
        category = category,
        language = language,
        country = country
    )
}
