package com.java.myapplication

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.java.myapplication.ui.theme.GameVindowScreen
import com.java.myapplication.ui.theme.LoadScreen
import com.java.myapplication.viewModels.PlayerViewModel
import com.java.myapplication.ui.theme.StartScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.java.myapplication.ui.theme.GameEndScreen
import com.java.myapplication.viewModels.GameReadyViewModel
import com.java.myapplication.viewModels.GameReadyViewModelFactory
import com.java.myapplication.viewModels.HouseViewModel
import com.java.myapplication.viewModels.HouseViewModelFactory
import com.java.myapplication.viewModels.SupermarketViewModel
import com.java.myapplication.viewModels.SupermarketViewModelFactory
import com.java.myapplication.viewModels.TimeViewModel

/**
 * enum values that represent the screens in the app
 */
enum class MonopolyScreen(@StringRes val title: Int) {
    Start(R.string.start_screen),
    Load(R.string.load_screen),
    Game(R.string.game_screen),
    End(R.string.end_screen)
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */

@Composable
fun MonopolyApp(
    viewModel: PlayerViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {

    val timeViewModel: TimeViewModel = viewModel()
    val houseViewModel: HouseViewModel = viewModel(
        factory = HouseViewModelFactory(viewModel)
    )
    val supermarketViewModel: SupermarketViewModel = viewModel(
        factory = SupermarketViewModelFactory(viewModel)
    )
    val gameReadyViewModel: GameReadyViewModel = viewModel(
        factory = GameReadyViewModelFactory(viewModel, timeViewModel, houseViewModel, supermarketViewModel)
    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MonopolyScreen.valueOf(
        backStackEntry?.destination?.route ?: MonopolyScreen.Start.name
    )

    Scaffold(
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MonopolyScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = MonopolyScreen.Start.name) {
                StartScreen(
                    viewModel = viewModel,
                    gameReadyViewModel = gameReadyViewModel,
                    navController = navController
                )
            }
            composable(route = MonopolyScreen.Load.name) {
                LoadScreen(
                    player = viewModel,
                    gameReadyViewModel = gameReadyViewModel,
                    navController = navController
                )
            }
            composable(route = MonopolyScreen.Game.name) {
                GameVindowScreen(
                    viewModel = viewModel,
                    houseViewModel = houseViewModel,
                    supermarketViewModel = supermarketViewModel,
                    timeViewModel = timeViewModel,
                    gameReadyViewModel = gameReadyViewModel,
                    navController = navController
                )
            }
            composable(route = MonopolyScreen.End.name) {
                GameEndScreen(playerViewModel = viewModel)
            }
        }
    }
}



/**
 * Resets the [OrderUiState] and pops up to [CupcakeScreen.Start]

private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(CupcakeScreen.Start.name, inclusive = false)
}

/**
 * Creates an intent to share order details
 */
private fun shareOrder(context: Context, subject: String, summary: String) {
    // Create an ACTION_SEND implicit intent with order details in the intent extras
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_cupcake_order)
        )
    )
}
*/