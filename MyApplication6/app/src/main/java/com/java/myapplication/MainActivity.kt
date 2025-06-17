package com.java.myapplication

import android.os.Bundle
import android.os.ParcelFileDescriptor
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.java.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DiceWithButtonAndImage()
                }
            }
        }
    }
}

val MyFontFamily = FontFamily(
    Font(R.font.font1) // Укажите ваше имя файла без расширения
)

@Preview
@Composable
fun DiceRollerApp() {
    DiceWithButtonAndImage(modifier = Modifier
        .fillMaxSize()

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiceWithButtonAndImage(
    modifier: Modifier = Modifier) {

    Image(
        painter = painterResource(id = R.drawable.fongame3),
        contentDescription = "Background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop // или FillBounds, FillWidth, FillHeight и т.д.
    )
    Column(
        modifier = modifier.fillMaxSize().padding(12.dp),
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
                    text = "Имя: Dima",
                    color = Color.White,
                    fontSize = 25.sp,
                    fontFamily = MyFontFamily,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Дата: 01.01.2025",
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
                text = "6,546,354",
                color = Color.White,
                fontSize = 60.sp,
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
                // Add a single item
                item {
                    Text(text = "First item")
                }

                items(50) { index ->
                    Text(text = "Item: $index")
                }

                // Add another single item
                item {
                    Text(text = "Last item")
                }
            }
        }
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val tooltipState1 = rememberTooltipState(isPersistent = true)
                Tool(
                    tooltipState = tooltipState1,
                    iconRes = R.drawable.free_icon_construction_4906274,
                    modifier = Modifier,
                    )

                val tooltipState2 = rememberTooltipState(isPersistent = true)
                Tool(
                    tooltipState = tooltipState2,
                    iconRes = R.drawable.free_icon_online_advertising_1466339,
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp),

                    )
                val tooltipState3 = rememberTooltipState(isPersistent = true)
                Tool(
                    tooltipState = tooltipState3,
                    iconRes = R.drawable.free_icon_team_1540367,
                    modifier = Modifier,
                    )
            }
            Button(
                onClick = {},
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
                    text = "Готово",
                    fontSize = 25.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tool(
    tooltipState: TooltipState,
    iconRes: Int,
    modifier: Modifier = Modifier,
) {
    var showHouseBuildDialog by remember { mutableStateOf(false) }
    var showSupermarketBuildDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    TooltipBox(
        positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(8.dp),
        tooltip = {
            RichTooltip(
                caretSize = DpSize(32.dp, 16.dp),
                action = {
                    Row(){
                        TextButton(
                            onClick = {
                                showHouseBuildDialog = true
                                scope.launch { tooltipState.dismiss() } },
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.house))
                        ) {
                            Text("Дом")
                        }
                        TextButton(
                            onClick = {
                                showSupermarketBuildDialog = true
                                scope.launch { tooltipState.dismiss() } },
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
    if (showHouseBuildDialog) {
        HouseBuildModalDialog(
            onDismiss = { showHouseBuildDialog = false }
        )
    }
    if (showSupermarketBuildDialog) {
        SupermarketBuildModalDialog(
            onDismiss = { showSupermarketBuildDialog = false }
        )
    }
}

@Composable
fun HouseBuildModalDialog(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        val scope = rememberCoroutineScope()
        var fletInput by remember { mutableStateOf("") }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
        ) {
            Surface(
                modifier = Modifier

                    .align(Alignment.Center),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(){
                        Text("Кол-во квартир: ")
                        OutlinedTextField(
                            value = fletInput,
                            onValueChange = { fletInput = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                    Text("Срок строительства: 11 мес.")
                    Row(){
                        Text("Кол-во квартир: ")
                        OutlinedTextField(
                            value = fletInput,
                            onValueChange = { fletInput = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                    Row(){
                        Text("Цена: ")
                        OutlinedTextField(
                            value = fletInput,
                            onValueChange = { fletInput = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                    Row(){
                        Text("Ежемес. платёж: ")
                        OutlinedTextField(
                            value = fletInput,
                            onValueChange = { fletInput = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                    Row(){
                        Text("Цена за кв. метр: ")
                        OutlinedTextField(
                            value = fletInput,
                            onValueChange = { fletInput = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        TextButton(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.superm)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Отмена")
                        }
                        TextButton(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.house)),
                            modifier = Modifier.weight(1f)
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
fun SupermarketBuildModalDialog(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        var price by remember { mutableStateOf("5000000") }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
        ) {
            Surface(
                modifier = Modifier

                    .align(Alignment.Center),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(){
                        Text("Цена: ")
                        OutlinedTextField(
                            value = price,
                            onValueChange = { price = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                    Text("Срок строительства: 11 мес.")
                    Text("Ежемес. платёж: 720000")
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        TextButton(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.superm)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Отмена")
                        }
                        TextButton(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.house)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Готово")
                        }
                    }

                }
            }
        }
    }
}