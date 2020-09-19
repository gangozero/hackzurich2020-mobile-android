package com.gangozero.foodtinder

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.setContent
import androidx.ui.tooling.preview.Preview
import com.gangozero.foodtinder.ui.FoodtinderTheme

class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<LoginViewModel>()
    val navigator = Navigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Router(navigator.state, navigator, viewModel)
        }
    }
}

@Composable
fun Router(state: NavigationEvent, navigator: Navigator, loginVm: LoginViewModel) {
    if (state is NavigationEvent.ToLogin) {
        LoginScreen(state = loginVm.sZed, { loginVm.login(it) }, { navigator.updateState(it) })
    } else if (state is NavigationEvent.ToHome) {
        HomeScreen()
    }
}

class Navigator {
    var state: NavigationEvent by mutableStateOf(NavigationEvent.ToLogin)
        private set

    fun updateState(e: NavigationEvent) {
        state = e
    }
}

sealed class NavigationEvent {
    object ToLogin : NavigationEvent()
    object ToHome : NavigationEvent()
}

data class LoginData(val email: String, val password: String, val address: String)


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FoodtinderTheme {
        Greeting("Android")
    }
}