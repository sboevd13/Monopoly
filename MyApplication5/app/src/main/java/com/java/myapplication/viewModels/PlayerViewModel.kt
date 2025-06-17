package com.java.myapplication.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.java.myapplication.data.AdBudgetDTO
import com.java.myapplication.data.HouseListDTO
import com.java.myapplication.data.PlayerListDTO
import com.java.myapplication.data.PlayerReadyDTO
import com.java.myapplication.data.PlayerUiState
import com.java.myapplication.data.WinnerDTO
import com.java.myapplication.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayerViewModel : ViewModel() {

    private val _winner = MutableStateFlow(WinnerDTO(nickname = "", capital = 15000000))
    val winner: StateFlow<WinnerDTO> = _winner.asStateFlow()

    private val _player = MutableStateFlow(PlayerUiState(nickname = "", capital = 15000000))
    val player: StateFlow<PlayerUiState> = _player.asStateFlow()

    private val _players = MutableStateFlow<List<PlayerListDTO>>(emptyList())
    val players: StateFlow<List<PlayerListDTO>> = _players.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()
    fun showBuildDialog() { _showDialog.value = true }
    fun hideBuildDialog() { _showDialog.value = false }

    private val _showPlayersDialog = MutableStateFlow(false)
    val showPlayersDialog: StateFlow<Boolean> = _showPlayersDialog.asStateFlow()
    fun showPlayersDialog() { _showPlayersDialog.value = true }
    fun hidePlayersDialog() { _showPlayersDialog.value = false }

    fun updateName(name: String) {
        _player.value = _player.value.copy(nickname = name)
    }

    fun updateCapital(capital: Int) {
        _player.value = _player.value.copy(capital = capital)
    }

    fun sendNicknameToServer(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        RetrofitClient.apiService.connectPlayer(_player.value.nickname).enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    val playerId = response.body() ?: -1
                    Log.d("Game", "Получен ID игрока: $playerId")
                    if (playerId > 0) {
                        // Сохраняем ID в состоянии игрока
                        _player.value = _player.value.copy(id = playerId)
                        onSuccess()
                    onSuccess()
                    }
                } else {
                    onError("Ошибка сервера: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                onError("Не удалось подключиться к серверу: ${t.message ?: "Неизвестная ошибка"}")
            }
        })
    }

    fun sendAdBudget(
        adBudget: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = AdBudgetDTO(
                    player_id = _player.value.id,
                    adBudget.toInt()
                )

                val response = RetrofitClient.apiService.changeAdBudget(request)
                _player.value = _player.value.copy(capital = response)
            } catch (e: Exception) {
                Log.e("House", "Ошибка при изменении рекламного бюджета", e)
            }
        }
    }


    suspend fun loadPlayers() {
        try {
            _players.value = RetrofitClient.apiService.getPlayers(_player.value.id)
            Log.d("ListPlayers", "${_players.value.size}")
        } catch (e: Exception) {
            Log.e("Players", "Ошибка загрузки домов", e)
        }
    }

    suspend fun loadWinner() {
        try {
            _winner.value = RetrofitClient.apiService.getWinner()
        } catch (e: Exception) {
            Log.e("Winner", "Ошибка загрузки домов", e)
        }
    }


}