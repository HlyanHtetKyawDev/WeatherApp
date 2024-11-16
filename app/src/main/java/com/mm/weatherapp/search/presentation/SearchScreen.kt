package com.mm.weatherapp.search.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mm.weatherapp.core.presentation.components.AppBar
import com.mm.weatherapp.core.presentation.components.LogoutConfirmDialog
import com.mm.weatherapp.core.presentation.components.SearchTextField
import com.mm.weatherapp.core.presentation.utils.ObserveAsEvents
import com.mm.weatherapp.search.presentation.components.SearchItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
    onLogout: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(events = viewModel.event) { event ->
        when (event) {
            is SearchEvent.Error -> {
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
                title = "Search",
                isBackIconShown = false,
                scrollBehavior = scrollBehavior,
                modifier = modifier,
                onLogoutClick = {
                    isDialogOpen = true
                }
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { contentPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(contentPadding)
        ) {
            SearchTextField(
                onSearchQueryChange = {
                    viewModel.searchCities(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp, vertical = 8.dp
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
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(state.searchList) { item ->
                        SearchItemCard(item = item) {
                            onClick(it.name)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

