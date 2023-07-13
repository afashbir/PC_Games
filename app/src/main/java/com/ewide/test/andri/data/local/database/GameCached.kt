package com.ewide.test.andri.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class GameCached(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "thumbnail_link")
    val thumbnailLink: String,

    @ColumnInfo(name = "is_loved")
    val isLoved: Boolean
)

@Dao
interface GameDao {

    @Query("SELECT * FROM GameCached")
    suspend fun getFavourites(): List<GameCached>

    @Insert
    suspend fun favorite(item: GameCached)

    @Delete
    suspend fun unFavorite(item: GameCached)
}