package com.java.myapplication.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.java.myapplication.data.HouseListDTO
import com.java.myapplication.data.HouseUiState
import com.java.myapplication.data.PlayerUiState
import com.java.myapplication.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HouseViewModel(
    private val playerViewModel: PlayerViewModel
) : ViewModel() {
    private val _houses = MutableStateFlow<List<HouseListDTO>>(emptyList())
    val houses: StateFlow<List<HouseListDTO>> = _houses.asStateFlow()

    fun updateHouses(value: List<HouseListDTO>) {
        _houses.value = value
    }

    private val _house = MutableStateFlow(HouseUiState())
    val house: StateFlow<HouseUiState> = _house.asStateFlow()


    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()
    fun showBuildDialog() { _showDialog.value = true }
    fun hideBuildDialog() { _showDialog.value = false }

    fun updateApartmentCount(value: String) {
        val number = value.toIntOrNull() ?: 0
        _house.update {
            it.copy(kolichestvo_kvartir = number)
        }

        // Теперь здесь можно сделать пересчёт других полей
        if (number == 0) {
            val newMonthEnd = 0
            _house.update {
                it.copy(construction_time = newMonthEnd)
            }
        }
        if (number > 0 && _house.value.ezemes_platez > 0) {
            val newMonthEnd = number * 100000 / _house.value.ezemes_platez
            _house.update {
                it.copy(construction_time = newMonthEnd)
            }
        }
    }

    fun updateConstructionTime(value: String) {
        val month = value.toIntOrNull() ?: 0
        _house.update {
            it.copy(construction_time = month)
        }

        if (month > 0 && (_house.value.kolichestvo_kvartir * 100000) > 0) {
            val newMonthlyPayment = (_house.value.kolichestvo_kvartir * 100000) / month
            _house.update {
                it.copy(ezemes_platez = newMonthlyPayment)
            }
        }
    }

    fun updateMonthlyPayment(payment: String) {
        val intValue = payment.toIntOrNull() ?: 0
        _house.value = _house.value.copy(ezemes_platez = intValue)
    }

    fun updateApartmentPrice(price: String) {
        val intValue = price.toIntOrNull() ?: 0
        _house.value = _house.value.copy(stoimost_kvartyry = intValue)
    }

    fun buildHouse(
        house: HouseUiState
    ) {
        if(_house.value.ezemes_platez > playerViewModel.player.value.capital){
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = house
                val response = RetrofitClient.apiService.createHouse(request)
                playerViewModel.updateCapital(response)
                _houses.value = loadPlayerHouses(playerViewModel.player.value.id)
            } catch (e: Exception) {
                Log.e("House", "Ошибка при постройке дома", e)
            }
        }
    }

    suspend fun loadPlayerHouses(playerId: Int): List<HouseListDTO> {
        var housesList: List<HouseListDTO> = emptyList()
        try {
            housesList = RetrofitClient.apiService.getPlayerHouses(playerId)
            Log.d("ListHouse", "${_houses.value.size}")
        } catch (e: Exception) {
            Log.e("House", "Ошибка загрузки домов", e)
        }
        return housesList
    }

    fun resetHouseState() {
        _house.update { HouseUiState() }
    }
}