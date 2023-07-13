package com.ewide.test.andri.data.mapper

import com.ewide.test.andri.data.local.database.GameCached
import com.ewide.test.andri.data.remote.model.GameResponse
import com.ewide.test.andri.domain.model.Game
import java.net.URL

fun GameResponse.toDomainModel(favoriteIds: List<String>) = Game(
    id = id,
    title = title,
    thumbnailUrl = URL(thumbnailLink.replace("\\/", "")),
    isLoved = favoriteIds.contains(id)
)

fun Game.toEntityModel() = GameCached(
    id = id,
    title = title,
    thumbnailLink = thumbnailUrl.toString(),
    isLoved = isLoved
)

fun GameCached.toDomainModel() = Game(
    id = id,
    title = title,
    thumbnailUrl = URL(thumbnailLink),
    isLoved = true
)