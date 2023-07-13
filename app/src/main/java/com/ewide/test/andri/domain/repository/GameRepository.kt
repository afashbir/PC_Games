package com.ewide.test.andri.domain.repository

import com.ewide.test.andri.domain.model.Game

interface GameRepository {
    suspend fun searchGames(title: String): List<Game>

    suspend fun favorite(item: Game)

    suspend fun unFavorite(item: Game)

    suspend fun getFavourites(): List<Game>
}