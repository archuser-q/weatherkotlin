package com.example.weatherkotlin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherkotlin.components.SearchTextField
import com.example.weatherkotlin.components.WeatherCard
import com.example.weatherkotlin.model.WeatherViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: WeatherViewModel
) {
    val locations by viewModel.locations.collectAsState()
    val searchText = remember { mutableStateOf("") }
    var hasNavigated by remember { mutableStateOf(false) }
    var removedItems by remember { mutableStateOf(setOf<String>()) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(bottom = 16.dp),
                title = { Text("") },
                navigationIcon = {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quay lại",
                            modifier = Modifier
                                .size(32.dp)
                                .padding(top = 2.dp)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Text(
                    text = "Manage cities",
                    fontSize = 34.sp,
                    modifier = Modifier.padding(start = 30.dp)
                )
            }

            item {
                SearchTextField(
                    value = searchText.value,
                    onValueChange = { searchText.value = it },
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused && !hasNavigated) {
                                hasNavigated = true
                                navController.navigate("SearchingLocation")
                            }
                        }
                )
            }

            item {
                Text(
                    text = "Added location",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    modifier = Modifier.padding(start = 30.dp)
                )
            }

            items(
                locations.size,
                key = { index -> locations[index].name }
            ) { index ->
                val location = locations[index]
                val isVisible = !removedItems.contains(location.name)
                AnimatedVisibility(  // WRAP TRONG AnimatedVisibility
                    visible = isVisible,
                    exit = shrinkVertically(
                        animationSpec = tween(300)
                    ) + fadeOut(
                        animationSpec = tween(300)
                    )
                ) {
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = { dismissValue ->
                            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                                removedItems = removedItems + location.name
                                kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
                                    kotlinx.coroutines.delay(300)
                                    viewModel.removeLocation(location.name)
                                }
                                true
                            } else {
                                false
                            }
                        }
                    )

                    SwipeToDismissBox(
                        state = dismissState,
                        backgroundContent = {
                            val targetAlpha =
                                if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart ||
                                    dismissState.currentValue == SwipeToDismissBoxValue.EndToStart
                                ) 1f
                                else 0.3f

                            val animatedAlpha by animateFloatAsState(
                                targetValue = targetAlpha,
                                animationSpec = tween(durationMillis = 100)
                            )
                            Card(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 20.dp)
                                    .graphicsLayer(alpha = animatedAlpha),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.Red
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 20.dp),
                                    contentAlignment = androidx.compose.ui.Alignment.CenterEnd
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = Color.White,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                        },
                        enableDismissFromStartToEnd = false
                    ) {
                        WeatherCard(location)
                    }
                }
            }
        }
    }
}