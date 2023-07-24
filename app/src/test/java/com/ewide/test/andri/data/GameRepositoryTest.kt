package com.ewide.test.andri.data

import com.ewide.test.andri.data.local.database.GameDao
import com.ewide.test.andri.data.mapper.toEntityModel
import com.ewide.test.andri.data.remote.api.GameApi
import com.ewide.test.andri.data.repository.GameRepositoryImpl
import com.ewide.test.andri.domain.repository.GameRepository
import com.ewide.test.andri.generateLovedTestGameFromDomain
import com.ewide.test.andri.generateTestGameResponse
import com.ewide.test.andri.generateUnLovedTestGameFromDomain
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GameRepositoryTest {

    @RelaxedMockK
    private lateinit var gameApi: GameApi

    @RelaxedMockK
    private lateinit var gameDao: GameDao

    private lateinit var objectUnderTest: GameRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        setupGameRepository()
    }

    @Test
    fun `should save favorite item`() = runTest {
        val testFavoriteGameDomain = generateLovedTestGameFromDomain(DUMMY_GAME_ID)
        val testFavoriteGameToCache = testFavoriteGameDomain.toEntityModel()

        objectUnderTest.favorite(testFavoriteGameDomain)

        coVerify { gameDao.favorite(testFavoriteGameToCache) }
    }

    @Test
    fun `should delete un-favorite item`() = runTest {
        val testFavoriteGameDomain = generateLovedTestGameFromDomain(DUMMY_GAME_ID)
        val testFavoriteGameToCache = testFavoriteGameDomain.toEntityModel()

        objectUnderTest.unFavorite(testFavoriteGameDomain)

        coVerify { gameDao.unFavorite(testFavoriteGameToCache) }
    }

    @Test
    fun `should search games title with given keyword`() = runTest {
        val keyword = "Fun"
        val anotherGameID = "ID-002"

        coEvery {
            gameApi.searchGames(keyword)
        } returns listOf(
            generateTestGameResponse(DUMMY_GAME_ID),
            generateTestGameResponse(anotherGameID)
        )

        val lovedGame = generateLovedTestGameFromDomain(anotherGameID)

        coEvery {
            gameDao.getFavourites()
        } returns listOf(lovedGame.toEntityModel())

        val searchResult = objectUnderTest.searchGames(keyword)

        coVerify {
            gameDao.getFavourites()

            gameApi.searchGames(keyword)
        }

        val expectedResult = listOf(
            generateUnLovedTestGameFromDomain(DUMMY_GAME_ID),
            lovedGame
        )

        assertEquals(expectedResult, searchResult)
    }

    private fun setupGameRepository() {
        objectUnderTest = GameRepositoryImpl(
            gameApi, gameDao
        )
    }

    companion object {
        private const val DUMMY_GAME_ID = "ID-001"
    }
}