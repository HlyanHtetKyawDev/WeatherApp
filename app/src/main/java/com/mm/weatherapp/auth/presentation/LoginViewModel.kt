package com.mm.weatherapp.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mm.weatherapp.auth.domain.useCase.CheckIsSignedInUseCase
import com.mm.weatherapp.auth.domain.useCase.GoogleSignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val checkIsSignedInUseCase: CheckIsSignedInUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()

    init {
        _state.update {
            it.copy(
                isLoginSuccess = checkIsSignedInUseCase()
            )
        }
    }

    fun googleLogin() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoginSuccess = googleSignInUseCase()
                )
            }
        }
    }
}