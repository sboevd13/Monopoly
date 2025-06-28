package com.java.myapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class Supermarket(
    private val playerViewModel: PlayerViewModel,
    private val timeViewModel: TimeViewModel,
    private val houseViewModel: HouseViewModel,
    private val supermarketViewModel: SupermarketViewModel
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GameReadyViewModel(playerViewModel, timeViewModel, houseViewModel, supermarketViewModel) as T
    }
}