package com.java.myapplication.data

import kotlinx.serialization.Serializable

@Serializable
data class PlayerListDTO(
    val nickname: String,
    val capital: Int
) {
}