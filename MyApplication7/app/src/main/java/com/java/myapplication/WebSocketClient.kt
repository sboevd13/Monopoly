package com.java.myapplication
import android.util.Log
import ua.naiksoftware.stomp.Stomp


class WebSocketClient(
    private val serverUrl: String = "ws://your_ip_address:8080/ws/websocket",
    private val onConnected: () -> Unit = {},
    private val onGameStart: () -> Unit = {},
    private val onError: (Throwable?) -> Unit = {}
) {

    private val disposables = CompositeDisposable()
    private val stompClient = Stomp.over(WebSocketTransport(), serverUrl)

    fun connect(nickname: String) {
        // Подписываемся на события жизненного цикла
        disposables.add(
            stompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ lifecycleEvent ->
                    when (lifecycleEvent.type) {
                        LifecycleEvent.Type.OPENED -> {
                            Log.d("Stomp", "Соединение установлено")
                            onConnected()
                            sendNickname(nickname)
                        }

                        LifecycleEvent.Type.CLOSED -> Log.d("Stomp", "Соединение закрыто")

                        LifecycleEvent.Type.ERROR -> {
                            Log.e("Stomp", "Ошибка STOMP", lifecycleEvent.throwable)
                            onError(lifecycleEvent.throwable)
                        }
                    }
                }, { error ->
                    Log.e("Stomp", "Ошибка подписки", error)
                    onError(error)
                })
        )

        // Подписка на событие начала игры
        disposables.add(
            stompClient.topic("/topic/game.start")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ topicMessage ->
                    Log.d("Stomp", "Получено событие: ${topicMessage.payload}")
                    onGameStart()
                }, { error ->
                    Log.e("Stomp", "Ошибка подписки /topic/game.start", error)
                    onError(error)
                })
        )

        // Начинаем подключение
        stompClient.connect()
    }

    private fun sendNickname(nickname: String) {
        stompClient.send("/app/player.connect", nickname).subscribe()
    }

    fun disconnect() {
        stompClient.disconnect()
        disposables.clear()
    }
}