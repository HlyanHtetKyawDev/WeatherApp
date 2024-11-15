package com.mm.weatherapp.sports.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mm.weatherapp.core.data.network.utils.Resource
import com.mm.weatherapp.core.presentation.BaseViewModel
import com.mm.weatherapp.sports.domain.useCase.SportsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle = SavedStateHandle(),
    private val sportsUseCase: SportsUseCase
) : BaseViewModel<SportsEvent>() {

    private val cityName = savedStateHandle["name"] ?: "Yangon"
    private val _state = MutableStateFlow(SportsUiState())
    val state = _state.onStart {
        getSports(cityName)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), SportsUiState())

    private var _loading: Boolean
        get() = state.value.isLoading
        set(value) = _state.update { it.copy(isLoading = value) }

    fun getSports(query: String) {
        if (query.isNotEmpty()) {
            _state.update {
                it.copy(
                    isLoading = false,
                    sports = null
                )
            }
            viewModelScope.launch {
                sportsUseCase(query).collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    error = result.error
                                )
                            }
                            emitEvent(SportsEvent.Error(result.error))
                        }

                        is Resource.Loading -> _loading = true
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    sports = result.data
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
