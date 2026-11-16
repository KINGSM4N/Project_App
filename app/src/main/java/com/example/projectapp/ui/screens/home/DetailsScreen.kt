package com.example.projectapp.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailsScreen(id: Int, onBack: () -> Unit) {
    Column(Modifier.padding(16.dp)) {
        Text("Details, id = $id")
        Button(onClick = onBack) { Text("Back") }
    }
}