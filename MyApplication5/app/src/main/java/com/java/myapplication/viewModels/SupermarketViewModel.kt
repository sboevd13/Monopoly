package com.java.myapplication.viewModels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.java.myapplication.data.PlayerUiState
import com.java.myapplication.data.SupermarketListDTO
import com.java.myapplication.data.SupermarketUiState
import com.java.myapplication.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SupermarketViewModel(
    private val playerViewModel: PlayerViewModel
) : ViewModel() {
    private val _supermarkets = MutableStateFlow<List<SupermarketListDTO>>(emptyList())
    val supermarkets: StateFlow<List<SupermarketListDTO>> = _supermarkets.asStateFlow()

    private val _supermarket = MutableStateFlow(SupermarketUiState())
    val supermarket: StateFlow<SupermarketUiState> = _supermarket.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()
    fun showBuildDialog() { _showDialog.value = true }
    fun hideBuildDialog() { _showDialog.value = false }

    fun updateConstructionTime(value: String) {
        val month = value.toIntOrNull() ?: 0
        _supermarket.update {
            it.copy(construction_time = month)
        }

        if (month > 0) {
            val newMonthlyPayment = 2500000 / month
            _supermarket.update {
                it.copy(monthly_payment = newMonthlyPayment)
            }
        }
    }

    fun updateMonthlyPayment(payment: String) {
        val intValue = payment.toIntOrNull() ?: 0
        _supermarket.value = _supermarket.value.copy(monthly_payment = intValue)
    }

    fun buildSupermarket(
        supermarket: SupermarketUiState
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = supermarket
                val response = RetrofitClient.apiService.createSupermarket(request)
                playerViewModel.updateCapital(response)
                loadPlayerSupermarkets(playerViewModel.player.value.id)
            } catch (e: Exception) {
                Log.e("House", "Ошибка при постройке дома", e)
            }
        }
    }

    suspend fun loadPlayerSupermarkets(playerId: Int) {
        try {
            _supermarkets.value = RetrofitClient.apiService.getPlayerSupermarkets(playerId)
            Log.d("SupermarketDebug", "Полученные данные: ${_supermarkets}") // Вывод в Logcat

        } catch (e: Exception) {
            Log.e("Supermarket", "Ошибка загрузки cegthvfhrtn", e)
        }
    }

    fun resetHouseState() {
        _supermarket.update { SupermarketUiState() }
    }
}