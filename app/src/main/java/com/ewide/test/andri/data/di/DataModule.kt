package com.ewide.test.andri.data.di

import com.ewide.test.andri.data.remote.api.GameApi
import com.ewide.test.andri.data.repository.GameRepositoryImpl
import com.ewide.test.andri.domain.repository.GameRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {

    @Provides
    @Singleton
    fun provideGameApi(
        retrofit: Retrofit
    ): GameApi {
        return retrofit.create(GameApi::class.java)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {
        @Binds
        @Singleton
        fun bindGameRepository(impl: GameRepositoryImpl): GameRepository
    }
}