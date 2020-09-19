package com.gangozero.foodtinder

import androidx.compose.foundation.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun EditField(label: String, onChange: (String) -> Unit, initValue: String = "") {

    var value by remember { mutableStateOf(TextFieldValue("")) }

    TextField(value = value, onValueChange = {
        value = it
        onChange(it.text)
    }, label = { Text(text = label) })
}