package com.gangozero.foodtinder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gangozero.foodtinder.App.Companion.repository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var state: State by mutableStateOf(State.Idle)
        private set

    sealed class State {
        object Idle : State()
        object Loading : State()
        data class Error(val t: Throwable) : State()
        object Success : State()
    }

    fun login(loginData: LoginData) {

        viewModelScope.launch {
            repository.login(loginData)
                .onStart {
                    state = State.Loading
                }
                .catch {
                    state = State.Error(it)
                }
                .onCompletion {
                    state = State.Idle
                }
                .collect {
                    state = State.Success
                }
        }
    }
}