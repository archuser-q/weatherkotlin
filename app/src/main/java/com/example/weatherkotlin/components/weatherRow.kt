package com.example.weatherkotlin.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun WeatherRow(
    day: String,
    icon: ImageVector,
    highTemp: String,
    lowTemp: String,
    iconTint: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp, horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = day,
            color = Color.Black,
            modifier = Modifier.weight(2f) // chiếm 2 phần
        )
        Icon(
            imageVector = icon,
            contentDescription = day,
            tint = iconTint,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp)
        )
        Text(
            text = lowTemp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = highTemp,
            color = Color.Gray
        )
    }
}

