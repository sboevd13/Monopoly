package com.java.myapplication.ui.theme

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.java.myapplication.MonopolyScreen
import com.java.myapplication.R
import com.java.myapplication.viewModels.GameReadyViewModel
import com.java.myapplication.viewModels.PlayerViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    viewModel: PlayerViewModel,
    gameReadyViewModel: GameReadyViewModel,
    navController: NavHostController
) {
    var name by remember { mutableStateOf("name") }
    Image(
        painter = painterResource(R.drawable.fonstart),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color.Black.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(20.dp))
        ){
            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .padding(10.dp)
                    .width(250.dp)
                    .height(400.dp)

                ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(R.color.startcard)),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.LightGray.copy(alpha = 0.2f),),
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        textStyle = TextStyle(color = colorResource(R.color.background))
                    )

                    // Кнопка с абсолютным прижатием
                    Button(
                        onClick = {
                            viewModel.updateName(name)

                            viewModel.sendNicknameToServer(
                                onSuccess = {
                                    gameReadyViewModel.connectToGameWebSocket()
                                    navController.navigate(MonopolyScreen.Load.name)
                                },
                                onError = { error ->
                                    Log.e("NetworkError", error)
                                }
                            )

                            },
                        modifier = Modifier,
                        shape = RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp,
                            bottomStart = 20.dp,
                            bottomEnd = 20.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.btnStart))
                    ) {
                        Text(
                            text = "Подключиться",
                            modifier = Modifier.padding(vertical = 8.dp) // Ручная настройка отступов текста
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    MyApplicationTheme {

    }
}