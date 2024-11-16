package com.mm.weatherapp.core.presentation.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import kotlinx.coroutines.delay

@Composable
fun CommonOutlinedTextFiled(
    hint: String,
    text: String = "",
    leadingIcon: ImageVector = Icons.Default.Email,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit = {},
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        label = { Text(hint) },
        leadingIcon = {
            Icon(leadingIcon, contentDescription = hint)
        },
        modifier = modifier,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(
            onDone = { onDone() }
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Composable
fun SearchTextField(
    onSearchQueryChange: (String) -> Unit,
    delayMillis: Long = 500L,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }
    var debouncedText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    // This launches a coroutine for debouncing the text change
    LaunchedEffect(text) {
        delay(delayMillis) // 500ms delay
        debouncedText = text
    }

    // Trigger the action when debouncedText changes
    LaunchedEffect(debouncedText) {
        onSearchQueryChange(debouncedText)
    }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        modifier = modifier,
        placeholder = { Text("Search Locations...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = { text = "" }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear search"
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                onSearchQueryChange(text)
            }
        ),
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            unfocusedLabelColor = MaterialTheme.colorScheme.outline,
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    isBackIconShown: Boolean = true,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    modifier: Modifier = Modifier,
    onLogoutClick: () -> Unit,
    onClickBack: () -> Unit = {},
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (isBackIconShown) {
                IconButton(onClick = onClickBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = Color.White,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = {
                onLogoutClick()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Logout",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White
        ),
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}


@Composable
fun LogoutConfirmDialog(
    isDialogOpen: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = onDismiss, // Dismisses the dialog if clicked outside
            title = {
                Text(text = "Logout")
            },
            text = {
                Text(text = "Are you sure to logout?")
            },
            confirmButton = {
                TextButton(onClick = {
                    onConfirm()
                }) {
                    Text("Yes", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onDismiss() // Handle dismiss action
                }) {
                    Text("No", color = Color.Black) // Customize the cancel button color
                }
            },
        )
    }
}