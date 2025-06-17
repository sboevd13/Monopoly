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


public class LobbyActivity extends AppCompatActivity {


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

    }





    private List<String> parsePlayers(String payload) {
        // Здесь должна быть логика парсинга JSON
        // Временная заглушка:
        List<String> players = new ArrayList<>();
        players.add("Игрок 1");
        players.add("Игрок 2");
        return players;
    }

}
