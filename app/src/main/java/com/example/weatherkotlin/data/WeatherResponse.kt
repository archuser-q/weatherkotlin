package com.example.weatherkotlin.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("current")
    val current: Current,
    @SerializedName("daily")
    val daily: Daily
)

data class Current(
    @SerializedName("temperature_2m")
    val temperature: Double
)

data class Daily(
    @SerializedName("temperature_2m_max")
    val temperatureMax: List<Double>,
    @SerializedName("temperature_2m_min")
    val temperatureMin: List<Double>
)