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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.model.Events
import com.example.marsphotos.network.EventsApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface MarsUiState {
    data class Success(val sources: List<Events>) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class EventsViewModel : ViewModel() {
    var eventsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    init {
        getNewsArticles()
    }

    fun getNewsArticles() {
        viewModelScope.launch {
            eventsUiState = MarsUiState.Loading
            eventsUiState = try {
                val response = EventsApi.retrofitService.getTopHeadlines()
                MarsUiState.Success(response.sources)
            } catch (e: IOException) {
                MarsUiState.Error
            } catch (e: HttpException) {
                MarsUiState.Error
            }
        }
    }
}
