package com.java.myapplication.data

import kotlinx.serialization.Serializable

@Serializable
class PlayerReadyDTO(
    var isReady: Boolean,
    var player_id: Int
) {
}