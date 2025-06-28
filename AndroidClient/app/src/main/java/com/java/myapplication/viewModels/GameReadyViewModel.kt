package com.java.myapplication.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.java.myapplication.data.PlayerReadyDTO
import com.java.myapplication.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class GameReadyViewModel(
    private val playerViewModel: PlayerViewModel,
    private val timeViewModel: TimeViewModel,
    private val houseViewModel: HouseViewModel,
    private val supermarketViewModel: SupermarketViewModel
) : ViewModel() {
    private val _gameStarted = MutableStateFlow(false)
    val gameStarted = _gameStarted.asStateFlow()

    private val _gameEnd = MutableStateFlow(false)
    val gameEnd = _gameEnd.asStateFlow()

    // Состояние кнопки: готов / ожидание
    private val _isPlayerReady = MutableStateFlow(false)
    val isPlayerReady = _isPlayerReady.asStateFlow()

    // Вызывается при нажатии на кнопку "Готово"
    fun setReady(ready: Boolean) {
        _isPlayerReady.value = ready

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = PlayerReadyDTO(
                    player_id = playerViewModel.player.value.id,
                    isReady = ready
                )
                RetrofitClient.apiService.playerSetReady(request)
            } catch (e: Exception) {
                Log.e("setReady", "Ошибка при изменении готовности игрока", e)
            }
        }
    }

    private var webSocket: WebSocket? = null
    private val client = OkHttpClient()

    fun connectToGameWebSocket() {
        viewModelScope.launch {

            webSocket?.close(1000, "Reconnecting")

            val request = Request.Builder()
                .url("ws://192.168.0.122:8081/game-ws")
                .build()

            webSocket = client.newWebSocket(request, object : WebSocketListener() {
                override fun onMessage(webSocket: WebSocket, text: String) {

                    when (text) {
                        "GAME_STARTED" -> _gameStarted.value = true
                        "ALL_PLAYERS_READY" -> {
                            viewModelScope.launch {
                                timeViewModel.setDate(RetrofitClient.apiService.getCurrentData())
                                playerViewModel.updateCapital(RetrofitClient.apiService.getCapital(playerViewModel.player.value.id))
                                houseViewModel.updateHouses(houseViewModel.loadPlayerHouses(playerViewModel.player.value.id))
                                supermarketViewModel.loadPlayerSupermarkets(playerViewModel.player.value.id)
                                playerViewModel.loadWinner()
                                Log.d("Game", "Получна дата: ${timeViewModel.getDate()}")
                            }
                            _isPlayerReady.value = false
                        }
                        "GAME_END" -> {
                            _gameEnd.value = true;
                        }
                    }
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    // Обработка ошибок и переподключение
                    Log.e("WebSocket", "Ошибка: ${t.message}")
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    // Корректное закрытие соединения
                }
            })
        }
    }
}