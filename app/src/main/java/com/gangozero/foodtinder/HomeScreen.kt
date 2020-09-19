package com.gangozero.foodtinder

import androidx.compose.foundation.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import com.gangozero.foodtinder.App.Companion.repository

@Composable
fun HomeScreen(navigator: Navigator) {
    Button(onClick = {
        repository.logOut()
    }) {
        Text(text = "Logout")
    }
}