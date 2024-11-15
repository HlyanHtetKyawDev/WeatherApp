package com.mm.weatherapp.astronomy.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mm.weatherapp.astronomy.domain.useCase.AstronomyUseCase
import com.mm.weatherapp.core.data.network.utils.Resource
import com.mm.weatherapp.core.presentation.BaseViewModel
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
class AstronomyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle = SavedStateHandle(),
    private val astronomyUseCase: AstronomyUseCase
) : BaseViewModel<AstronomyEvent>() {

    private val cityName = savedStateHandle["name"] ?: "Yangon"
    val _state = MutableStateFlow(AstronomyUiState())
    val state = _state.onStart {
        getAstronomy(cityName)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), AstronomyUiState())

    private var _loading: Boolean
        get() = state.value.isLoading
        set(value) = _state.update { it.copy(isLoading = value) }

    fun getAstronomy(query: String) {
        if (query.isNotEmpty()) {
            _state.update {
                it.copy(
                    isLoading = false,
                    astronomy = null
                )
            }
            viewModelScope.launch {
                astronomyUseCase(query).collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    error = result.error
                                )
                            }
                            emitEvent(AstronomyEvent.Error(result.error))
                        }

                        is Resource.Loading -> _loading = true
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    astronomy = result.data
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
