package com.gangozero.foodtinder

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = Repository()
    val state = MutableLiveData<State>()
    val s: MutableState<State> = mutableStateOf(State.Idle)
    val k: State by mutableStateOf(State.Idle)

    var sZed: State by mutableStateOf(State.Idle)
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
                    s.value = State.Loading
                    sZed = State.Loading
                    //state.value = State.Loading
                }
                .catch {
                    sZed = State.Error(it)
                    state.value = State.Error(it)
                }
                .collect {
                    sZed = State.Success
                    state.value = State.Success
                }
        }
    }
}