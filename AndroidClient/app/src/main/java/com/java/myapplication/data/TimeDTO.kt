package com.java.myapplication.data

import kotlinx.serialization.Serializable

@Serializable
data class TimeDTO(
    var month: Int,
    var year: Int
) {
}