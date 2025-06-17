package com.java.myapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HouseViewModelFactory(
    private val playerViewModel: PlayerViewModel
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HouseViewModel(playerViewModel) as T
    }
}