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
import com.gangozero.foodtinder.App.Companion.repository
import com.gangozero.foodtinder.NavigationEvent.*
import com.gangozero.foodtinder.ui.FoodtinderTheme

class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<LoginViewModel>()
    val navigator = Navigator()
    val isLoggedIn = IsLoggedInUpdater()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repository.updater = isLoggedIn

        setContent {
            Router(navigator.event, isLoggedIn.isLoggedIn, navigator, viewModel)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}

class IsLoggedInUpdater {
    var isLoggedIn: Boolean by mutableStateOf(repository.isLoggedIn())
        private set

    fun update(value: Boolean) {
        isLoggedIn = value
    }
}

@Composable
fun Router(
    navEvent: NavigationEvent,
    isLoggedIn: Boolean,
    navigator: Navigator,
    loginVm: LoginViewModel
) {
    println("router: $navEvent")
    if (navEvent is CheckLogin) {
        if (isLoggedIn) {
            HomeScreen(navigator)
        } else {
            LoginScreen(state = loginVm.state, { loginVm.login(it) }, { navigator.updateState(it) })
        }
    } else if (navEvent is ToLogin) {
        LoginScreen(state = loginVm.state, { loginVm.login(it) }, { navigator.updateState(it) })
    } else if (navEvent is ToHome) {
        HomeScreen(navigator)
    } else if (navEvent is ToRecipeMatchScreen) {
        RecipeMatchScreen()
    }
}

class Navigator {
    var event: NavigationEvent by mutableStateOf(CheckLogin)
        private set

    fun updateState(e: NavigationEvent) {
        event = e
    }
}

sealed class NavigationEvent {
    object CheckLogin : NavigationEvent()
    object ToLogin : NavigationEvent()
    object ToHome : NavigationEvent()
    object ToRecipeMatchScreen : NavigationEvent()
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