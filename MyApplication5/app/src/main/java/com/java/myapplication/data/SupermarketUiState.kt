package com.java.myapplication.data

import kotlinx.serialization.Serializable

@Serializable
data class SupermarketUiState(
    var construction_time: Int = 0,
    var monthly_payment: Int = 0,
    var player_id: Int = 0
) {
}