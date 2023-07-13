package com.ewide.test.andri

import com.ewide.test.andri.domain.model.Game
import java.net.URL

internal fun generateTestGameFromDomain() = Game(
    id = "#GameID",
    title = "Game Title",
    thumbnailUrl = URL("https://testgame.com/GameID.jpg"),
    isLoved = true
)