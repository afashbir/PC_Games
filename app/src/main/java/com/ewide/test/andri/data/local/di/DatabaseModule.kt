package com.ewide.test.andri.data.local.di

import android.content.Context
import androidx.room.Room
import com.ewide.test.andri.data.local.database.AppDatabase
import com.ewide.test.andri.data.local.database.GameDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val APP_DATABASE_NAME = "app_database_name"

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            APP_DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideGameDao(database: AppDatabase): GameDao {
        return database.gameDao()
    }
}