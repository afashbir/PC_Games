package com.ewide.test.andri.data.repository

import com.ewide.test.andri.data.mapper.toDomainModel
import com.ewide.test.andri.data.remote.api.GameApi
import com.ewide.test.andri.domain.model.Game
import com.ewide.test.andri.domain.repository.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gameApi: GameApi
) : GameRepository {

    override suspend fun searchGames(title: String): List<Game> {
        return gameApi
            .searchGames(title)
            .map { it.toDomainModel() }
    }
}