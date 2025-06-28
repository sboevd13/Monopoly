package com.java.myapplication.data

import kotlinx.serialization.Serializable

@Serializable
data class PlayerListDTO(
    val id: Int,
    val nickname: String,
    val capital: Int
) {
}