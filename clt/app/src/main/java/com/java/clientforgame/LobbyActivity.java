package com.java.clientforgame;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompMessage;

public class LobbyActivity extends AppCompatActivity {

    private StompClient stompClient;
    private PlayerAdapter adapter;
    private ProgressBar progressBar;
    private Button startGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        progressBar = findViewById(R.id.progressBar);
        startGameButton = findViewById(R.id.startGameButton);

        // Настройка RecyclerView
        RecyclerView recyclerView = findViewById(R.id.playersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlayerAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Подключение к WebSocket
        connectWebSocket();
    }

    private void connectWebSocket() {
        progressBar.setVisibility(View.VISIBLE);

        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://10.0.2.2:8080/ws");

        stompClient.connect();
        stompClient.topic("/topic/players").subscribe(this::handlePlayersUpdate);

        stompClient.lifecycle().subscribe(lifecycleEvent -> {
            Log.d("WebSocket", lifecycleEvent.getType().name());
            if (lifecycleEvent.getType() == Stomp.LifecycleEvent.Type.OPENED) {
                runOnUiThread(() -> progressBar.setVisibility(View.GONE));
            }
        });
    }

    private void handlePlayersUpdate(StompMessage message) {
        List<String> players = parsePlayers(message.getPayload());
        runOnUiThread(() -> {
            adapter.updatePlayers(players);
            if (players.size() >= 2) {
                startGameButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private List<String> parsePlayers(String payload) {
        // Здесь должна быть логика парсинга JSON
        // Временная заглушка:
        List<String> players = new ArrayList<>();
        players.add("Игрок 1");
        players.add("Игрок 2");
        return players;
    }

    @Override
    protected void onDestroy() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        super.onDestroy();
    }
}
