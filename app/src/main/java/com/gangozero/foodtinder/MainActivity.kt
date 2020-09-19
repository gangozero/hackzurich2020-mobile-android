package com.gangozero.foodtinder

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.ui.tooling.preview.Preview
import com.gangozero.foodtinder.ui.FoodtinderTheme

class MainActivity : AppCompatActivity() {

    var loggedIn = false

    val viewModel by viewModels<LoginViewModel>()
    val navVm by viewModels<NavVm>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Router(navVm.state, navVm, viewModel)
        }

//        if (loggedIn) {
//            setContent {
//                MainScreen()
//            }
//        } else {
//            setContent {
//                LoginScreen(viewModel.sZed) { viewModel.login(it) }
//            }
//        }

//        setContent {
//            FoodtinderTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//                    Greeting("Android")
//                }
//            }
//        }
    }
}

@Composable
fun Router(state: NavigationEvent, navVm: NavVm, loginVm: LoginViewModel) {
    if (state is NavigationEvent.ToLogin) {
        LoginScreen(state = loginVm.sZed, { loginVm.login(it) }, { navVm.updateState(it) })
    } else if (state is NavigationEvent.ToHome) {
        HomeScreen()
    }
}

class NavVm : ViewModel() {
    var state: NavigationEvent by mutableStateOf(NavigationEvent.ToLogin)
        private set

    fun updateState(e: NavigationEvent) {
        state = e
    }
}

sealed class Screen {
    object Login : Screen()
    object Home : Screen()
}

sealed class NavigationEvent {
    object ToLogin : NavigationEvent()
    object ToHome : NavigationEvent()
}

//@Composable
//fun Root(state: LoginViewModel.State, viewModel: LoginViewModel, navVm: NavVm) {
//    if (state is LoginViewModel.State.Success) {
//        HomeScreen()
//    } else if (state is LoginViewModel.State.Idle) {
//        LoginScreen(state, { viewModel.login(it) }, {
//            navVm.updateState(it)
//        })
//    }
//}


@Composable
fun EditField(label: String, onChange: (String) -> Unit, initValue: String = "") {

    var value by remember { mutableStateOf(TextFieldValue("")) }

    TextField(value = value, onValueChange = {
        value = it
        onChange(it.text)
    }, label = { Text(text = label) })
}

data class LoginData(val email: String, val password: String, val address: String)

@Composable
fun LoginScreen(
    state: LoginViewModel.State,
    onLogin: (LoginData) -> Unit,
    doNav: (NavigationEvent) -> Unit
) {

    if (state is LoginViewModel.State.Success) {
        doNav(NavigationEvent.ToHome)
    }

    Column {

        var email = ""
        var password = ""
        var address = ""

        Text(text = state.toString())
        EditField("e-email", { email = it })
        EditField("password", { password = it })
        EditField("address", { address = it })

//        TextField(
//                value = email,
//                onValueChange = { newValue -> email = newValue },
//                label = { Text("e-mail") })
//
//        TextField(
//                value = password,
//                onValueChange = { newValue -> password = newValue },
//                label = { Text("password") })
//
//        TextField(
//                value = address,
//                onValueChange = { newValue -> address = newValue },
//                label = { Text("address") })

        Button(
            onClick = {
                onLogin(LoginData(email, password, address))
            },
            enabled = state !is LoginViewModel.State.Loading
        ) {
            Text(text = "login")
        }

    }
}

@Composable
fun HomeScreen() {

}

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