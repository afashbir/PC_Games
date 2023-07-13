package com.ewide.test.andri.data.mapper

import com.ewide.test.andri.data.remote.model.GameResponse
import com.ewide.test.andri.domain.model.Game
import java.net.URL

fun GameResponse.toDomainModel() = Game(
    id = id,
    title = title,
    thumbnailUrl = URL(thumbnailLink.replace("\\/", ""))
)