package com.mm.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mm.weatherapp.astronomy.presentation.AstronomyScreen
import com.mm.weatherapp.auth.presentation.LoginScreen
import com.mm.weatherapp.core.presentation.navigation.ScreenAstronomy
import com.mm.weatherapp.core.presentation.navigation.ScreenLogin
import com.mm.weatherapp.core.presentation.navigation.ScreenSearch
import com.mm.weatherapp.core.presentation.navigation.ScreenSports
import com.mm.weatherapp.search.presentation.SearchScreen
import com.mm.weatherapp.sports.presentation.SportsScreen
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
                    startDestination = ScreenSports("London")
                ) {
                    composable<ScreenLogin> {
                        LoginScreen(
                            onLoginSuccess = {
                                navController.navigate(ScreenSearch) {
                                    popUpTo(ScreenLogin) {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }
                    composable<ScreenSearch> {
                        SearchScreen { name ->
                            navController.navigate(ScreenAstronomy(name))
                        }
                    }
                    composable<ScreenAstronomy> {
                        AstronomyScreen(
                            onClickItem = {
                                navController.navigate(ScreenSports(it))
                            },
                            onClickBack = {
                                navController.navigateUp()
                            }
                        )
                    }
                    composable<ScreenSports> {
                        val name = it.toRoute<ScreenSports>().name.orEmpty()
                        SportsScreen(name) {
                            navController.navigateUp()
                        }
                    }
                }
            }
        }
    }
}