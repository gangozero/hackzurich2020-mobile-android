package com.gangozero.foodtinder

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.runtime.Composable

@Composable
fun LoginScreen(
    state: LoginViewModel.State,
    onLogin: (LoginData) -> Unit,
    doNav: (NavigationEvent) -> Unit
) {

    Column {

        var email = ""
        var password = ""
        var address = ""

        Text(text = state.toString())
        EditField("e-email", { email = it })
        EditField("password", { password = it })
        EditField("address", { address = it })

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