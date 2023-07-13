package com.ewide.test.andri.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

private const val DATABASE_VERSION = 1

@Database(
    entities = [GameCached::class],
    version = DATABASE_VERSION
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun gameDao(): GameDao
}