package com.mm.weatherapp.sports.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mm.weatherapp.core.presentation.components.AppBar
import com.mm.weatherapp.core.presentation.components.LogoutConfirmDialog
import com.mm.weatherapp.core.presentation.utils.ObserveAsEvents
import com.mm.weatherapp.sports.presentation.components.SportTypesItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportsScreen(
    name: String,
    viewModel: SportsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onLogout: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(events = viewModel.event) { event ->
        when (event) {
            is SportsEvent.Error -> {
                Toast.makeText(
                    context, event.error.message, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    LaunchedEffect(state.isLogOut) {
        if (state.isLogOut) {
            onLogout()
        }
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var isDialogOpen by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppBar(
                title = name,
                isBackIconShown = true,
                scrollBehavior = scrollBehavior,
                modifier = modifier,
                onLogoutClick = {
                    isDialogOpen = true
                },
                onClickBack = {
                    onClickBack()
                }
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
        } else if (isDialogOpen) {
            LogoutConfirmDialog(
                isDialogOpen = isDialogOpen,
                onConfirm = {
                    viewModel.signOut()
                    isDialogOpen = false
                },
                onDismiss = {
                    isDialogOpen = false
                }
            )
        } else {
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(contentPadding),
            ) {
                SportTypesItem(
                    sportType = "Football", list = state.sports?.football
                )
                SportTypesItem(
                    sportType = "Cricket", list = state.sports?.cricket
                )
                SportTypesItem(
                    sportType = "Golf", list = state.sports?.golf
                )
            }

        }
    }
}

