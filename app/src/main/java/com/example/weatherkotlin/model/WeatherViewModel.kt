package com.example.weatherkotlin.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class WeatherLocation(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val currentTemp: Double,
    val maxTemp: Double,
    val minTemp: Double,
    val description: String = "Clear"
)

class WeatherViewModel : ViewModel() {
    private val _locations = MutableStateFlow<List<WeatherLocation>>(emptyList())
    val locations: StateFlow<List<WeatherLocation>> = _locations.asStateFlow()

    fun addLocation(location: WeatherLocation) {
        val currentList = _locations.value.toMutableList()
        if (!currentList.any { it.name == location.name }) {
            currentList.add(location)
            _locations.value = currentList
        }
    }

    fun removeLocation(name: String) {
        _locations.value = _locations.value.filter { it.name != name }
    }

    fun hasLocation(name: String): Boolean {
        return _locations.value.any { it.name == name }
    }
}