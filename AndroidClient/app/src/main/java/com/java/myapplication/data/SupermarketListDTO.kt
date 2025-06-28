package com.java.myapplication.data

import kotlinx.serialization.Serializable

@Serializable
data class SupermarketListDTO(
    var construction_time: Int,
    var monthly_payment: Int
) {
}