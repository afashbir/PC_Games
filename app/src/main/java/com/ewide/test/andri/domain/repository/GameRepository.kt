package com.ewide.test.andri.domain.repository

import com.ewide.test.andri.domain.model.Game

interface GameRepository {
    suspend fun searchGames(title: String): List<Game>
}