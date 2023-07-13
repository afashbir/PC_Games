package com.ewide.test.andri.data

import com.ewide.test.andri.data.local.database.GameDao
import com.ewide.test.andri.data.mapper.toEntityModel
import com.ewide.test.andri.data.remote.api.GameApi
import com.ewide.test.andri.data.repository.GameRepositoryImpl
import com.ewide.test.andri.domain.repository.GameRepository
import com.ewide.test.andri.generateTestGameFromDomain
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
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
        val testFavoriteGameDomain = generateTestGameFromDomain()
        val testFavoriteGameToCache = generateTestGameFromDomain().toEntityModel()

        objectUnderTest.favorite(testFavoriteGameDomain)

        coVerify { gameDao.favorite(testFavoriteGameToCache) }
    }

    @Test
    fun `should delete un-favorite item`() = runTest {
        // Given
        val testFavoriteGameDomain = generateTestGameFromDomain()
        val testFavoriteGameToCache = generateTestGameFromDomain().toEntityModel()

        objectUnderTest.unFavorite(testFavoriteGameDomain)

        coVerify { gameDao.unFavorite(testFavoriteGameToCache) }
    }

    private fun setupGameRepository() {
        objectUnderTest = GameRepositoryImpl(
            gameApi, gameDao
        )
    }
}