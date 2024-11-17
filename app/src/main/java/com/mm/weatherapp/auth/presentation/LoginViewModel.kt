package com.mm.weatherapp.auth.presentation

import androidx.lifecycle.viewModelScope
import com.mm.weatherapp.auth.domain.useCase.CheckIsSignedInUseCase
import com.mm.weatherapp.auth.domain.useCase.GoogleSignInUseCase
import com.mm.weatherapp.auth.domain.useCase.PasswordSignInUseCase
import com.mm.weatherapp.core.data.network.utils.GeneralError
import com.mm.weatherapp.core.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val passwordSignInUseCase: PasswordSignInUseCase,
    private val checkIsSignedInUseCase: CheckIsSignedInUseCase,
) : BaseViewModel<LoginEvent>() {

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
                    isLoading = true
                )
            }
            val isLoginSuccess = googleSignInUseCase()
            if (isLoginSuccess) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isLoginSuccess = true
                    )
                }
            } else {
                val errorMessage = "Google login failed"
                _state.update {
                    it.copy(
                        isLoading = false,
                        isLoginSuccess = false
                    )
                }
                val error = GeneralError(
                    message = errorMessage,
                )
                emitEvent(LoginEvent.Error(error))
            }

        }
    }

    fun passwordLogin(email: String, password: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            val response = passwordSignInUseCase(email, password)
            if (response.isSuccess) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isLoginSuccess = true,
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isLoginSuccess = false
                    )
                }
                val error = GeneralError(
                    message = response.message.orEmpty(),
                )
                emitEvent(LoginEvent.Error(error))
            }
        }
    }
}