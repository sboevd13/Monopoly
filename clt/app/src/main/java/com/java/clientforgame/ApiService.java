package com.java.clientforgame;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/session/connect")
    Call<Void> connectPlayer(@Body String nickname);

    @GET("api/session/players")
    Call<List<Player>> getConnectedPlayers();
}
