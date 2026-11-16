package com.example.projectapp.ui.screens.setup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SetupGenderScreen(onContinue: () -> Unit) {
    Column(Modifier.padding(16.dp)) {
        Text("Setup: Gender")
        Button(onClick = onContinue) { Text("Continue") }
    }
}