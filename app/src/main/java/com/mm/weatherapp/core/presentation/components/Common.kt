package com.mm.weatherapp.core.presentation.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.mm.weatherapp.ui.theme.BlueLight
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
    onValueChange: (String) -> Unit,
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
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
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
        placeholder = { Text("Search Cities...") },
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
            focusedBorderColor = BlueLight,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary
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
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White
        ),
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}