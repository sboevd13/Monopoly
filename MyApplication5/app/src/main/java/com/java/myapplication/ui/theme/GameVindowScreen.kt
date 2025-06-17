package com.java.myapplication.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.java.myapplication.MonopolyScreen
import com.java.myapplication.R
import com.java.myapplication.data.HouseListDTO
import com.java.myapplication.data.HouseUiState
import com.java.myapplication.data.PlayerListDTO
import com.java.myapplication.data.SupermarketListDTO
import com.java.myapplication.data.SupermarketUiState
import com.java.myapplication.viewModels.GameReadyViewModel
import com.java.myapplication.viewModels.HouseViewModel
import com.java.myapplication.viewModels.PlayerViewModel
import com.java.myapplication.viewModels.SupermarketViewModel
import com.java.myapplication.viewModels.TimeViewModel
import kotlinx.coroutines.launch

val MyFontFamily = FontFamily(
    Font(R.font.font1) // Укажите ваше имя файла без расширения
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameVindowScreen(
    viewModel: PlayerViewModel,
    gameReadyViewModel: GameReadyViewModel,
    houseViewModel: HouseViewModel,
    supermarketViewModel: SupermarketViewModel,
    timeViewModel: TimeViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier) {

    val playerState by viewModel.player.collectAsState()
    val playersList by viewModel.players.collectAsState()
    val playerAdState by viewModel.showDialog.collectAsState()
    val playerPlayersListState by viewModel.showPlayersDialog.collectAsState()
    val isPlayerReady by gameReadyViewModel.isPlayerReady.collectAsState()
    val dateState by timeViewModel.date.collectAsState()
    val gameEnd by gameReadyViewModel.gameEnd.collectAsState()
    val houses by houseViewModel.houses.collectAsState()
    val supermarkets by supermarketViewModel.supermarkets.collectAsState()

    var isReady by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Image(
        painter = painterResource(id = R.drawable.fongame3),
        contentDescription = "Background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop // или FillBounds, FillWidth, FillHeight и т.д.
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Имя: ${playerState.nickname}",
                    color = Color.White,
                    fontSize = 25.sp,
                    fontFamily = MyFontFamily,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Дата: 01.${dateState.month}.${dateState.year}",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = MyFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Завершение: 01.01.2028",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = MyFontFamily,
                    fontWeight = FontWeight.Bold
                )

            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Капитал:",
                color = Color.White,
                fontSize = 18.sp,
                fontFamily = MyFontFamily,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${playerState.capital}",
                color = Color.White,
                fontSize = 40.sp,
                fontFamily = MyFontFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
        Card(
            shape = RoundedCornerShape(20.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(10.dp)
            ) {

                items(houses) {house ->
                    HouseItem(house = house)
                }
                items(supermarkets) {supermarket ->
                    SupermarketItem(supermarket = supermarket)
                }

            }
        }
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val tooltipState1 = rememberTooltipState(isPersistent = true)
                Tool(
                    playerViewModel = viewModel,
                    houseViewModel = houseViewModel,
                    supermarketViewModel = supermarketViewModel,
                    tooltipState = tooltipState1,
                    iconRes = R.drawable.free_icon_construction_4906274,
                    modifier = Modifier,
                )

                Button(
                    onClick = { viewModel.showBuildDialog() },
                    modifier = Modifier
                        .size(120.dp)
                        .border(
                            width = 5.dp,
                            color = Color.White,
                        ),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.btn2)),
                    shape = RectangleShape
                ) {
                    Image(
                        painter = painterResource(R.drawable.free_icon_online_advertising_1466339),
                        contentDescription = null,
                        modifier = modifier.fillMaxSize(),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
                Button(
                    onClick = {
                        scope.launch {
                            viewModel.loadPlayers()
                            viewModel.showPlayersDialog()
                        }},
                    modifier = Modifier
                        .size(120.dp)
                        .border(
                            width = 5.dp,
                            color = Color.White,
                        ),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.btn2)),
                    shape = RectangleShape
                ) {
                    Image(
                        painter = painterResource(R.drawable.free_icon_team_1540367),
                        contentDescription = null,
                        modifier = modifier.fillMaxSize(),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            }
            Button(
                onClick = {
                    gameReadyViewModel.setReady(true)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .border(
                        shape = RoundedCornerShape(
                            bottomStart = 20.dp,
                            bottomEnd = 20.dp
                        ),
                        width = 5.dp,
                        color = Color.White
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.house)),
                shape = RoundedCornerShape(
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                )
            ) {
                Text(
                    text = if (isReady) "Ждём игроков" else "Готово",
                    fontSize = 25.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
    // Overlay с замочком (показывается только если isReady == true)
    if (isPlayerReady) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .clickable { /* Можно оставить пустым или закрыть по клику */ },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.lock), // твой drawable с замком
                contentDescription = "Замок",
                modifier = Modifier.size(100.dp)
            )
        }
    }
    if (playerAdState) {
        AdModalDialog(
            playerViewModel = viewModel,
            onDismiss = { viewModel.hideBuildDialog() }
        )
    }
    if (playerPlayersListState) {
        PlayersDialog(
            players = playersList,
            onDismiss = { viewModel.hidePlayersDialog()}
        )
    }

    // Обработка перехода при старте игры
    LaunchedEffect(gameEnd) {
        if (gameEnd) {
            navController.navigate(MonopolyScreen.End.name) {

                popUpTo(MonopolyScreen.Game.name) { inclusive = true }
            }
        }
    }
}

@Composable
fun PlayersDialog(
    players: List<PlayerListDTO>,
    onDismiss: () -> Unit // Лямбда для закрытия окна
) {
    AlertDialog(
        onDismissRequest = onDismiss, // Вызывается при клике вне диалога или на кнопку "Назад"
        title = {
            Text("Список игроков")
        },
        text = {
            // Используем LazyColumn для эффективного отображения списка
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(players) { player ->
                    PlayerRow(player = player)
                    Divider() // Разделитель между игроками
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Закрыть")
            }
        }
    )
}

@Composable
fun PlayerRow(player: PlayerListDTO) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween, // Размещает элементы по краям
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {

            }
        ) {
            Text(
                text = player.nickname,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
        Button(
            onClick = {

            }
        ) {
            Text(
                text = "Капитал: ${player.capital}$", // Добавим знак валюты для наглядности
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun HouseItem(house: HouseListDTO) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if(house.construction_time == 0 && house.kolichestvo_kvartir > 0){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Квартир: ${house.kolichestvo_kvartir}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Цена квартиры: ${house.stoimost_kvartyry}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }else if(house.construction_time == 0) {
                Text(
                    text = "Дом продан",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }else{
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Мес. до конца строит.: ${house.construction_time}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "Ежемес. плат.: ${house.kolichestvo_kvartir}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun SupermarketItem(supermarket: SupermarketListDTO) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if(supermarket.construction_time != 0){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Ежем.плата: ${supermarket.monthly_payment}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Мес. до конца строит.: ${supermarket.construction_time}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }else{
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Приносит в месяц: 500,000",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tool(
    playerViewModel: PlayerViewModel,
    houseViewModel: HouseViewModel,
    supermarketViewModel: SupermarketViewModel,
    tooltipState: TooltipState,
    iconRes: Int,
    modifier: Modifier = Modifier,
) {

    val houseShowDialog by houseViewModel.showDialog.collectAsState()
    val supermarketShowDialog by supermarketViewModel.showDialog.collectAsState()
    val scope = rememberCoroutineScope()
    TooltipBox(
        positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(8.dp),
        tooltip = {
            RichTooltip(
                action = {
                    Row() {
                        TextButton(
                            onClick = {
                                houseViewModel.showBuildDialog()
                                scope.launch { tooltipState.dismiss() }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.house))
                        ) {
                            Text("Дом")
                        }
                        TextButton(
                            onClick = {
                                supermarketViewModel.showBuildDialog()
                                scope.launch { tooltipState.dismiss() }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.superm))
                        ) {
                            Text("Супермаркет")
                        }
                    }
                }
            ) {
                Text("Построить:")
            }
        },
        state = tooltipState
    ) {
        Button(
            onClick = { scope.launch { tooltipState.show() } },
            modifier = Modifier
                .size(120.dp)
                .border(
                    width = 5.dp,
                    color = Color.White,
                ),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.btn2)),
            shape = RectangleShape
        ) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = modifier.fillMaxSize(),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }

    if (houseShowDialog) {
        HouseBuildModalDialog(
            playerViewModel = playerViewModel,
            viewModel = houseViewModel,
            onDismiss = { houseViewModel.hideBuildDialog() }
        )
    }
    if (supermarketShowDialog) {
        SupermarketBuildModalDialog(
            playerViewModel = playerViewModel,
            viewModel = supermarketViewModel,
            onDismiss = { supermarketViewModel.hideBuildDialog() }
        )
    }

}


@Composable
fun HouseBuildModalDialog(
    playerViewModel: PlayerViewModel,
    viewModel: HouseViewModel,
    onDismiss: () -> Unit) {

    val houseState by viewModel.house.collectAsState()
    val playerState by playerViewModel.player.collectAsState()
    // Вычисляемые значения
    val price = remember(houseState.kolichestvo_kvartir) {
        houseState.kolichestvo_kvartir * 100000
    }
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
        ) {
            Surface(
                modifier = Modifier

                    .align(Alignment.Center),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    OutlinedTextField(
                        value = houseState.kolichestvo_kvartir.toString(),
                        onValueChange = { viewModel.updateApartmentCount(it) },
                        label = { Text("Кол-во квартир") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    // Поле: Срок строительства
                    OutlinedTextField(
                        value = houseState.construction_time.toString(),
                        onValueChange = { viewModel.updateConstructionTime(it) },
                        label = { Text("Срок строительства (мес.)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    // Поле: Ежемесячный платеж
                    OutlinedTextField(
                        value = houseState.ezemes_platez.toString(),
                        onValueChange = { viewModel.updateMonthlyPayment(it) },
                        label = { Text("Ежемесячный платёж") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    // Автоматически рассчитываемая цена
                    Text(
                        text = "Цена: $price",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Поле: Цена за квартиру
                    OutlinedTextField(
                        value = houseState.stoimost_kvartyry.toString(),
                        onValueChange = { viewModel.updateApartmentPrice(it) },
                        label = { Text("Цена за квартиру") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        TextButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(
                                    R.color.superm
                                )
                            ),
                        ) {
                            Text("Отмена")
                        }

                        TextButton(
                            onClick = {
                                viewModel.buildHouse(
                                    houseState.copy(
                                        player_id = playerState.id,
                                    )
                                )
                                playerViewModel.updateCapital(playerState.capital - houseState.ezemes_platez)
                                viewModel.resetHouseState()
                                onDismiss()
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(
                                    R.color.house
                                )
                            )
                        ) {
                            Text("Готово")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SupermarketBuildModalDialog(
    playerViewModel: PlayerViewModel,
    viewModel: SupermarketViewModel,
    onDismiss: () -> Unit) {

    val supermarketState by viewModel.supermarket.collectAsState()
    val playerState by playerViewModel.player.collectAsState()
    // Вычисляемые значения
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
        ) {
            Surface(
                modifier = Modifier

                    .align(Alignment.Center),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    OutlinedTextField(
                        value = supermarketState.construction_time.toString(),
                        onValueChange = { viewModel.updateConstructionTime(it) },
                        label = { Text("Срок строительства (мес.)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    // Поле: Ежемесячный платеж
                    OutlinedTextField(
                        value = supermarketState.monthly_payment.toString(),
                        onValueChange = { viewModel.updateMonthlyPayment(it) },
                        label = { Text("Ежемесячный платёж") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    // Автоматически рассчитываемая цена
                    Text(
                        text = "Цена: 2,500,000",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        TextButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(
                                    R.color.superm
                                )
                            ),
                        ) {
                            Text("Отмена")
                        }

                        TextButton(
                            onClick = {
                                viewModel.buildSupermarket(
                                    supermarketState.copy(
                                        player_id = playerState.id,
                                    )
                                )
                                viewModel.resetHouseState()
                                onDismiss()
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(
                                    R.color.house
                                )
                            )
                        ) {
                            Text("Готово")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdModalDialog(
    playerViewModel: PlayerViewModel,
    onDismiss: () -> Unit) {

    val playerState by playerViewModel.player.collectAsState()
    var adBudget by remember { mutableStateOf("") }
    // Вычисляемые значения
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
        ) {
            Surface(
                modifier = Modifier

                    .align(Alignment.Center),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    OutlinedTextField(
                        value = adBudget,
                        onValueChange = { adBudget = it },
                        label = { Text("Рекламный бюджет") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(modifier = Modifier.fillMaxWidth()) {
                        TextButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(
                                    R.color.superm
                                )
                            ),
                        ) {
                            Text("Отмена")
                        }

                        TextButton(
                            onClick = {
                                playerViewModel.sendAdBudget(
                                    adBudget
                                )
                                onDismiss()
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(
                                    R.color.house
                                )
                            )
                        ) {
                            Text("Готово")
                        }
                    }
                }
            }
        }
    }
}




