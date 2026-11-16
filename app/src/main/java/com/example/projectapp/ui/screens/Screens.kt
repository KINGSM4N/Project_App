package com.example.projectapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    Column(Modifier.padding(16.dp)) {
        Text("Splash")
        Button(onClick = onFinished) { Text("Continue") }
    }
}

@Composable
fun LoginScreen(onLoginClick: () -> Unit) {
    Column(Modifier.padding(16.dp)) {
        Text("Login")
        Button(onClick = onLoginClick) { Text("Log in") }
    }
}

@Composable
fun SetupGenderScreen(onContinue: () -> Unit) {
    Column(Modifier.padding(16.dp)) {
        Text("Setup: Gender")
        Button(onClick = onContinue) { Text("Continue") }
    }
}

@Composable
fun HomeScreen(onItemClick: (Int) -> Unit) {
    Column(Modifier.padding(16.dp)) {
        Text("Home")
        Button(onClick = { onItemClick(1) }) { Text("Open item 1") }
    }
}

@Composable
fun DetailsScreen(id: Int, onBack: () -> Unit) {
    Column(Modifier.padding(16.dp)) {
        Text("Details, id = $id")
        Button(onClick = onBack) { Text("Back") }
    }
}