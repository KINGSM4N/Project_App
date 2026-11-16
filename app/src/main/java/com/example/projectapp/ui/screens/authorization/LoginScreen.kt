package com.example.projectapp.ui.screens.authorization

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(onLoginClick: () -> Unit) {
    Column(Modifier.padding(16.dp)) {
        Text("Login")
        Button(onClick = onLoginClick) { Text("Log in") }
    }
}