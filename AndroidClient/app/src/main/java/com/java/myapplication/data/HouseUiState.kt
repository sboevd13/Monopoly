package com.java.myapplication.data

import kotlinx.serialization.Serializable

@Serializable
data class HouseUiState(
    var player_id: Int = 0,
    var stoimost_kvartyry: Int = 0,
    var kolichestvo_kvartir: Int = 0,
    var construction_time: Int = 0,
    var ezemes_platez: Int = 714285,
) {
}