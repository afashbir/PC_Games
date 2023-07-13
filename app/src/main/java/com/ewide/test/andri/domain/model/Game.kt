package com.ewide.test.andri.domain.model

import java.net.URL

data class Game(
    val id: String,
    val title: String,
    val thumbnailUrl: URL,
    val isLoved: Boolean
)