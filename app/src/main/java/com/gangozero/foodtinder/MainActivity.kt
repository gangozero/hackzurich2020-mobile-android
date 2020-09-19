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
import androidx.ui.tooling.preview.Preview
import com.gangozero.foodtinder.ui.FoodtinderTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MainActivity : AppCompatActivity() {

    var loggedIn = false

    val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginScreen(viewModel.sZed) { viewModel.login(it) }
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
fun EditField(label: String, onChange: (String) -> Unit, initValue: String = "") {

    var value by remember { mutableStateOf(TextFieldValue("")) }

    TextField(value = value, onValueChange = {
        value = it
        onChange(it.text)
    }, label = { Text(text = label) })
}

data class LoginData(val email: String, val password: String, val address: String)

@Composable
fun LoginScreen(state: LoginViewModel.State, onLogin: (LoginData) -> Unit) {

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

data class Product(val id: String)
data class User(val id: String)







@Composable
fun MainScreen() {

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