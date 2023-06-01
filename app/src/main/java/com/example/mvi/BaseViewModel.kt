package com.example.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<state : IUiState, intent : IUiIntent> : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(initUiState())
    val uiStateFlow: StateFlow<state> = _uiStateFlow

    protected abstract fun initUiState(): state

    protected fun sendUiState(copy: state.() -> state) {
        _uiStateFlow.update { copy(_uiStateFlow.value) }
    }

}