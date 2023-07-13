package com.ewide.test.andri.data.remote.api

import com.ewide.test.andri.data.remote.model.GameResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GameApi {

    @GET("games")
    suspend fun searchGames(
        @Query("title") title: String
    ): List<GameResponse>
}