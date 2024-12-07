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

package com.example.marsphotos.network

import android.util.Log
import com.example.marsphotos.model.NewsResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query


interface EventsApiService {
    @GET("top-headlines/sources?apiKey=ee5a217b6d0f4bc78133de1f5a008e24")
    suspend fun getTopHeadlines(): NewsResponse
}


object EventsApi {
    private const val BASE_URL = "https://newsapi.org/v2/"
    private val retrofit: Retrofit by lazy {
        try {
            Retrofit.Builder()
                .addConverterFactory(
                    Json {
                        ignoreUnknownKeys = true
                    }.asConverterFactory("application/json".toMediaType())
                )
                .baseUrl(BASE_URL)
                .build()
        } catch (e: Exception) {
            Log.e("Retrofit", "Initialization error", e)
            throw e
        }
    }

    val retrofitService: EventsApiService by lazy {
        retrofit.create(EventsApiService::class.java)
    }
}
