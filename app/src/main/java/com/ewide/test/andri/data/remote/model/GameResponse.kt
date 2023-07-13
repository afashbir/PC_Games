package com.ewide.test.andri.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameResponse(
    @SerialName("gameID")
    val id: String,

    @SerialName("external")
    val title: String,

    @SerialName("thumb")
    val thumbnailLink: String
)