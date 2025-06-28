package com.java.myapplication.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.java.myapplication.viewModels.PlayerViewModel

// Определим кастомные цвета для стилизации
val DarkGreen = Color(0xFF004D40)
val LightGreen = Color(0xFF00796B)
val Gold = Color(0xFFFFD700)
val OffWhite = Color(0xFFF8F8F8)

@Composable
fun GameEndScreen(
    playerViewModel: PlayerViewModel
) {
    val winner by playerViewModel.winner.collectAsState()

    // Box используется как основной контейнер для возможности наложения элементов (например, фона)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        DarkGreen,
                        LightGreen
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Заголовок экрана
            Text(
                text = "Игра Окончена",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = OffWhite,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Карточка для выделения информации о победителе
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = OffWhite)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Иконка трофея для визуального акцента
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = "Победитель",
                        tint = Gold,
                        modifier = Modifier.size(80.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Имя победителя
                    Text(
                        text = winner.nickname,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = DarkGreen,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "абсолютный монополист!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                    Divider(color = Color.LightGray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(24.dp))

                    // Информация о капитале
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MonetizationOn,
                            contentDescription = "Капитал",
                            tint = DarkGreen,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            // Форматируем число для лучшей читаемости
                            text = "Капитал: ${"%,d".format(winner.capital)} $",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DarkGreen
                        )
                    }
                }
            }

            // Опционально: можно добавить кнопку "Играть снова"
            /*
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = { /* TODO: Логика для начала новой игры или возврата в меню */ },
                colors = ButtonDefaults.buttonColors(containerColor = Gold),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Играть снова",
                    color = DarkGreen,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
            }
            */
        }
    }
}
