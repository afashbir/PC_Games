package com.ewide.test.andri

import com.ewide.test.andri.data.remote.model.GameResponse
import com.ewide.test.andri.domain.model.Game
import java.net.URL

internal fun generateLovedTestGameFromDomain(id: String) = Game(
    id = "#$id",
    title = "Game Title - $id",
    thumbnailUrl = URL("https://testgame.com/$id.jpg"),
    isLoved = true
)

internal fun generateUnLovedTestGameFromDomain(id: String) = Game(
    id = "#$id",
    title = "Game Title - $id",
    thumbnailUrl = URL("https://testgame.com/$id.jpg"),
    isLoved = false
)

internal fun generateTestGameResponse(id: String) = GameResponse(
    id = "#$id",
    title = "Game Title - $id",
    thumbnailLink = "https://testgame.com/$id.jpg"
)