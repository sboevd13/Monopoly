package com.java.myapplication.network

import com.java.myapplication.data.AdBudgetDTO
import com.java.myapplication.data.HouseBuildResponseDTO
import com.java.myapplication.data.HouseListDTO
import com.java.myapplication.data.HouseUiState
import com.java.myapplication.data.PlayerListDTO
import com.java.myapplication.data.PlayerReadyDTO
import com.java.myapplication.data.PlayerUiState
import com.java.myapplication.data.SupermarketListDTO
import com.java.myapplication.data.SupermarketUiState
import com.java.myapplication.data.TimeDTO
import com.java.myapplication.data.WinnerDTO
import retrofit2.Call
import retrofit2.http.*


interface GameApi {
    @POST("/api/player/connect")
    fun connectPlayer(@Body nickname: String): Call<Int>

    @POST("api/houses")
    suspend fun createHouse(@Body request: HouseUiState): Int

    @POST("api/supermarkets")
    suspend fun createSupermarket(@Body request: SupermarketUiState): Int

    @GET("api/houses/player/{playerId}")
    suspend fun getPlayerHouses(@Path("playerId") playerId: Int): List<HouseListDTO>

    @GET("api/supermarkets/player/{playerId}")
    suspend fun getPlayerSupermarkets(@Path("playerId") playerId: Int): List<SupermarketListDTO>

    @POST("api/player/adBudget")
    suspend fun changeAdBudget(@Body adBudget: AdBudgetDTO): Int

    @POST("api/player/setReady")
    suspend fun playerSetReady(@Body isReady: PlayerReadyDTO)

    @GET("api/player/getMonth")
    suspend fun getCurrentData(): TimeDTO

    @GET("api/player/getCapital/{playerId}")
    suspend fun getCapital(@Path("playerId") playerId: Int): Int

    @GET("api/player/getWinner")
    suspend fun getWinner(): WinnerDTO

    @GET("api/player/getPlayers/{playerId}")
    suspend fun getPlayers(@Path("playerId") playerId: Int): List<PlayerListDTO>
}