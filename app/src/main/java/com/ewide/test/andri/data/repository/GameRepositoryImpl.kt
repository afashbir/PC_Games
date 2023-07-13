package com.ewide.test.andri.data.repository

import com.ewide.test.andri.data.local.database.GameDao
import com.ewide.test.andri.data.mapper.toDomainModel
import com.ewide.test.andri.data.mapper.toEntityModel
import com.ewide.test.andri.data.remote.api.GameApi
import com.ewide.test.andri.domain.model.Game
import com.ewide.test.andri.domain.repository.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gameApi: GameApi,
    private val gameDao: GameDao
) : GameRepository {

    override suspend fun searchGames(title: String): List<Game> {
        val favoriteIds = gameDao
            .getFavourites()
            .map { it.id }

        return gameApi
            .searchGames(title)
            .map { it.toDomainModel(favoriteIds) }
    }

    override suspend fun favorite(item: Game) {
        gameDao.favorite(item.toEntityModel())
    }

    override suspend fun unFavorite(item: Game) {
        gameDao.unFavorite(item.toEntityModel())
    }

    override suspend fun getFavourites(): List<Game> {
        return gameDao
            .getFavourites()
            .map { gameCached ->
                gameCached.toDomainModel()
            }
    }
}