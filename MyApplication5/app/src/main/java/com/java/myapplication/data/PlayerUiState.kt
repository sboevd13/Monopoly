package com.java.myapplication.data

import kotlinx.serialization.Serializable

@Serializable
data class PlayerUiState(
    val id: Int = 0,
    var nickname: String,
    var capital: Int,
) {
}