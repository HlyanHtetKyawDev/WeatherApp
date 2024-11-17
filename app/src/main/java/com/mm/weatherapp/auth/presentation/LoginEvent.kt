package com.mm.weatherapp.auth.presentation

import com.mm.weatherapp.core.data.network.utils.GeneralError

sealed class LoginEvent {
    data class Error(val error: GeneralError) : LoginEvent()
}