package com.java.myapplication.data

import kotlinx.serialization.Serializable

@Serializable
data class CapitalDTO(
    var month: Int,
    var year: Int
) {
}