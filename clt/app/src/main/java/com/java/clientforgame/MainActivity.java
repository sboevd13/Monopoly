package com.java.clientforgame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText nicknameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nicknameInput = findViewById(R.id.nicknameInput);
        Button connectButton = findViewById(R.id.connectButton);

        connectButton.setOnClickListener(v -> {
            String nickname = nicknameInput.getText().toString().trim();
            if (!nickname.isEmpty()) {
                connectToSession(nickname);
            }
        });
    }

    private void connectToSession(String nickname) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<Void> call = apiService.connectPlayer(nickname);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(MainActivity.this, LobbyActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to connect", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Connection error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
