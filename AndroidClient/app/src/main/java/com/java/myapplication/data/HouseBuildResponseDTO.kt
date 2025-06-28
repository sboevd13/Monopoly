package com.java.myapplication.data

import kotlinx.serialization.Serializable


@Serializable
data class HouseBuildResponseDTO(
    var stoimost_kvartyry: Int,
    var kolichestvo_kvartir: Int,
    var construction_time: Int,
    var ezemes_platez: Int,
    var player_capital: Int,
) {

}