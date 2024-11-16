package com.mm.weatherapp.astronomy.presentation

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mm.weatherapp.astronomy.data.mapper.toSearch
import com.mm.weatherapp.astronomy.presentation.components.SunAndMoonCard
import com.mm.weatherapp.astronomy.presentation.components.TimeDistanceCard
import com.mm.weatherapp.core.data.utils.toStringTime
import com.mm.weatherapp.core.presentation.components.AppBar
import com.mm.weatherapp.core.presentation.components.SearchTextField
import com.mm.weatherapp.core.presentation.utils.ObserveAsEvents
import com.mm.weatherapp.search.presentation.components.SearchItemCard
import com.mm.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun AstronomyScreen(
    viewModel: AstronomyViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onClickItem: (String) -> Unit,
    onClickBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Animation states
    var isLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isLoaded = true
    }

    val context = LocalContext.current

    ObserveAsEvents(events = viewModel.event) { event ->
        when (event) {
            is AstronomyEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    Scaffold(
        topBar = {
            AppBar(
                title = "Astronomy"
            ) {
                onClickBack()
            }
        },
    ) { contentPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(contentPadding),
        ) {
            SearchTextField(
                onSearchQueryChange = {
                    viewModel.getAstronomy(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
            )
            if (state.isLoading) {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    state.astronomy?.let { data ->
                        AnimatedVisibility(
                            visible = isLoaded,
                            enter = fadeIn(animationSpec = tween(500)) +
                                    slideInVertically(
                                        initialOffsetY = { -40 },
                                        animationSpec = tween(500)
                                    )
                        ) {
                            SearchItemCard(item = data.toSearch()) {
                                onClickItem(it.name)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        AnimatedVisibility(
                            visible = isLoaded,
                            enter = fadeIn(animationSpec = tween(700)) +
                                    slideInVertically(
                                        initialOffsetY = { -40 },
                                        animationSpec = tween(700, delayMillis = 200)
                                    )
                        ) {
                            TimeDistanceCard(
                                distance = "${data.distance} m",
                                localTime = data.localTime.toStringTime()
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        AnimatedVisibility(
                            visible = isLoaded,
                            enter = fadeIn(animationSpec = tween(900)) +
                                    slideInVertically(
                                        initialOffsetY = { -40 },
                                        animationSpec = tween(900, delayMillis = 400)
                                    )
                        ) {
                            SunAndMoonCard(data)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAstronomy(modifier: Modifier = Modifier) {
    WeatherAppTheme {
        // AstronomyScreen()
    }
}