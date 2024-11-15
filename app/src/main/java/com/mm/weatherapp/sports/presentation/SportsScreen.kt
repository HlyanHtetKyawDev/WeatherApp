package com.mm.weatherapp.sports.presentation

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mm.weatherapp.core.presentation.components.AppBar
import com.mm.weatherapp.core.presentation.utils.ObserveAsEvents
import com.mm.weatherapp.sports.presentation.components.SportTypesItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SportsScreen(
    name: String,
    viewModel: SportsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(events = viewModel.event) { event ->
        when (event) {
            is SportsEvent.Error -> {
                Toast.makeText(
                    context, event.error.message, Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    Scaffold(
        topBar = {
            AppBar(
                title = name, isBackIconShown = true
            ) {
                onClickBack()
            }
        },
    ) { contentPadding ->
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
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(contentPadding),
            ) {
                SportTypesItem(
                    sportType = "Cricket", list = state.sports?.cricket
                )
                SportTypesItem(
                    sportType = "Golf", list = state.sports?.golf
                )
                SportTypesItem(
                    sportType = "Football", list = state.sports?.football
                )
            }
        }
    }
}

