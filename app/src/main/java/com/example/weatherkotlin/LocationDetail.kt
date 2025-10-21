package com.example.weatherkotlin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import com.example.weatherkotlin.components.WeatherRow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetail(navController: NavController){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(bottom = 16.dp),
                title = { Text("") },
                actions = {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Quay lại",
                            modifier = Modifier
                                .size(32.dp)
                                .padding(top = 2.dp)
                        )
                    }
                    IconButton(
                        onClick = {  },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Tùy chọn",
                            modifier = Modifier
                                .size(28.dp)
                                .padding(top = 2.dp)
                        )
                    }
                }
            )
        }
    ){innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item{
                Column(
                    modifier = Modifier
                        .padding(top=44.dp)
                        .padding(horizontal = 20.dp)
                ){
                    Text(
                        text = "Dong Da"
                    )
                    Text(
                        text = "26°",
                        fontSize = 135.sp
                    )
                    Text(
                        text = "Clear 30°/23°",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 260.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.5.dp)
                    ) {
                        WeatherRow(
                            day = "Friday",
                            icon = Icons.Default.WbSunny,
                            highTemp = "30°",
                            lowTemp = "23°",
                            iconTint = Color(0xFFFFC107)
                        )
                        WeatherRow(
                            day = "Saturday",
                            icon = Icons.Default.Cloud,
                            highTemp = "28°",
                            lowTemp = "22°",
                            iconTint = Color.Gray
                        )
                        WeatherRow(
                            day = "Sunday",
                            icon = Icons.Default.WbCloudy,
                            highTemp = "27°",
                            lowTemp = "21°",
                            iconTint = Color(0xFF90CAF9)
                        )

                        Button(
                            onClick = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp)
                                .height(52.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE0E0E0),
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(16.5.dp)
                        ) {
                            Text("5-day forecast")
                        }
                    }
                }
            }
        }
    }
}