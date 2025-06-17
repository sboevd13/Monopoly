package com.java.myapplication

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.java.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FullScreen()
                }
            }
        }
    }
}

@Composable
private fun FullScreen() {
    Column(Modifier.fillMaxWidth()){
        Row(Modifier.weight(1f)) {
            Card(
                title = stringResource(R.string.textTitle),
                descr = stringResource(R.string.textDecsr),
                color = colorResource(R.color.first),
                modifier = Modifier.weight(1f)
            )
            Card(
                title = stringResource(R.string.imageTitle),
                descr = stringResource(R.string.imageDescr),
                color = colorResource(R.color.second),
                modifier = Modifier.weight(1f)
            )
        }
        Row(Modifier.weight(1f)){
            Card(
                title = stringResource(R.string.columnTitle),
                descr = stringResource(R.string.columnDescr),
                color = colorResource(R.color.third),
                modifier = Modifier.weight(1f)
            )
            Card(
                title = stringResource(R.string.rowTitle),
                descr = stringResource(R.string.rowDescr),
                color = colorResource(R.color.fourth),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun Card(
    title: String,
    descr: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = title,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = descr,
            textAlign = TextAlign.Justify
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        FullScreen()
    }
}