package com.mm.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mm.weatherapp.core.presentation.navigation.ScreenAstronomy
import com.mm.weatherapp.core.presentation.navigation.ScreenSearch
import com.mm.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ScreenSearch
                ) {
                    composable<ScreenSearch> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Search")
                            Button(onClick = {
                                navController.navigate(ScreenAstronomy(name = "Ygn"))
                            }) {
                                Text("Go to astronomy")
                            }
                        }
                    }
                    composable<ScreenAstronomy> {
                        val args = it.toRoute<ScreenAstronomy>()
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Astronomy ${args.name}")
                        }
                    }
                }
            }
        }
    }
}