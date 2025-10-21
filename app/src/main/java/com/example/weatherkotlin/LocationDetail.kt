package com.example.weatherkotlin

import android.health.connect.datatypes.units.Percentage
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.example.weatherkotlin.model.WeatherLocation
import com.example.weatherkotlin.model.WeatherViewModel
import com.example.weatherkotlin.utils.RetrofitInstance
import androidx.compose.ui.unit.TextUnit
import com.example.weatherkotlin.components.CircularProgressBar
import kotlin.time.Duration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetail(
    navController: NavController,
    locationName: String,
    latitude: Double,
    longitude: Double,
    viewModel: WeatherViewModel
){
    val isLocationAdded = viewModel.hasLocation(locationName)
    var currentTemp by remember { mutableStateOf<Double?>(null) }
    var maxTemp by remember { mutableStateOf<Double?>(null) }
    var minTemp by remember { mutableStateOf<Double?>(null) }
    LaunchedEffect(latitude, longitude) {
        fetchWeatherData(latitude, longitude) { current, max, min ->
            currentTemp = current
            maxTemp = max
            minTemp = min
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if(!isLocationAdded){
                TopAppBar(
                    modifier = Modifier.padding(bottom = 16.dp),
                    title = { Text("") },
                    navigationIcon = {
                        Button(
                            onClick = { navController.popBackStack() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Red
                            )
                        ) {
                            Text("Cancel")
                        }
                    },
                    actions = {
                        Button(
                            onClick = {
                                currentTemp?.let { current ->
                                    maxTemp?.let { max ->
                                        minTemp?.let { min ->
                                            viewModel.addLocation(
                                                WeatherLocation(
                                                    name = locationName,
                                                    latitude = latitude,
                                                    longitude = longitude,
                                                    currentTemp = current,
                                                    maxTemp = max,
                                                    minTemp = min,
                                                    description = "Clear"
                                                )
                                            )
                                            navController.navigate("MainScreen") {
                                                popUpTo("MainScreen") { inclusive = false }
                                                launchSingleTop = true
                                            }
                                        }
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Blue
                            )
                        ) {
                            Text("Add")
                        }
                    }
                )
            }
            else{
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
                        text = locationName
                    )
                    Text(
                        text = "${currentTemp?.toInt() ?: "--"}°",
                        fontSize = 135.sp
                    )
                    Text(
                        text = "Clear ${maxTemp?.toInt() ?: "--"}°/${minTemp?.toInt() ?: "--"}°",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "UV",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "Weak",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .padding(start=50.dp)
                            ){
                                CircularProgressBar(percentage = 1f, number=100)
                            }
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Humidity",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "58%",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            Box(
                                modifier = Modifier.padding(start=50.dp)
                            ){
                                CircularProgressBar(percentage = 1f, number=100)
                            }
                        }
                    }
                }
            }
        }
    }
}
suspend fun fetchWeatherData(
    lat: Double,
    lon: Double,
    onResult: (Double, Double, Double) -> Unit
) {
    try {
        val response = RetrofitInstance.api.getWeather(lat, lon)
        val currentTemp = response.current.temperature
        val maxTemp = response.daily.temperatureMax[0]
        val minTemp = response.daily.temperatureMin[0]

        onResult(currentTemp, maxTemp, minTemp)
    } catch (e: Exception) {
        e.printStackTrace()
        onResult(0.0, 0.0, 0.0)
    }
}