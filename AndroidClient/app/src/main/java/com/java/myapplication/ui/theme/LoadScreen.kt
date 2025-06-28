package com.java.myapplication.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.java.myapplication.MonopolyScreen
import com.java.myapplication.viewModels.GameReadyViewModel
import com.java.myapplication.viewModels.PlayerViewModel


@Composable
fun LoadScreen(
    player: PlayerViewModel,
    gameReadyViewModel: GameReadyViewModel,
    navController: NavHostController
) {
    val gameStarted by gameReadyViewModel.gameStarted.collectAsState()

    LaunchedEffect(Unit) {
        gameReadyViewModel.connectToGameWebSocket()
    }

    // Обработка перехода при старте игры
    LaunchedEffect(gameStarted) {
        if (gameStarted) {
            navController.navigate(MonopolyScreen.Game.name) {
                // Очищаем бэкстек чтобы нельзя было вернуться
                popUpTo(MonopolyScreen.Load.name) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Ждём игроков...", fontSize = 24.sp)
    }
}
