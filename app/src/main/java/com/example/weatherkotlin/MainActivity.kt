package com.example.weatherkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
             val navController = rememberNavController()
             NavHost(navController=navController, startDestination = "MainScreen", builder = {
                 composable ("MainScreen",){
                      MainScreen(navController)
                 }
                 composable("SearchingLocation",){
                     SearchingLocation(navController)
                 }
             })
        }
    }
}