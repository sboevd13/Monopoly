package com.java.myapplication.data

import kotlinx.serialization.Serializable

@Serializable
class AdBudgetDTO(
    var player_id: Int,
    var adBudget: Int
) {

}