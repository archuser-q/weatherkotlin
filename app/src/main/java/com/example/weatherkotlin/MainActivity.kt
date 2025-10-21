package com.example.weatherkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "MainScreen",
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(400)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(400)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(400)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(400)
                    )
                }
            ) {
                composable("MainScreen") { MainScreen(navController) }
                composable("SearchingLocation") { SearchingLocation(navController) }
                composable(
                    route = "LocationDetail/{name}/{lat}/{lon}",
                    arguments = listOf(
                        navArgument("name") { type = NavType.StringType },
                        navArgument("lat") { type = NavType.FloatType },
                        navArgument("lon") { type = NavType.FloatType }
                    )
                ) { backStackEntry ->
                    val name = backStackEntry.arguments?.getString("name") ?: ""
                    val lat = backStackEntry.arguments?.getFloat("lat") ?: 0f
                    val lon = backStackEntry.arguments?.getFloat("lon") ?: 0f
                    LocationDetail(navController, name, lat.toDouble(), lon.toDouble())
                }
                composable("DetailForecast") {forecast(navController)}
            }
        }
    }
}
