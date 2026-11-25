package com.example.projectapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.projectapp.data.Meal
import com.example.projectapp.ui.theme.AppBackground
import com.example.projectapp.ui.theme.AppButtonGray
import com.example.projectapp.ui.theme.AppLime
import com.example.projectapp.ui.theme.LeagueSpartan
import com.example.projectapp.ui.theme.Poppins
import com.example.projectapp.viewmodel.DetailsUiState
import com.example.projectapp.viewmodel.DetailsViewModel

@Composable
fun DetailsScreen(
    id: String,
    onBack: () -> Unit,
    viewModel: DetailsViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(id) { viewModel.load(id) }

    DetailsContent(state = state, onBack = onBack, onRetry = { viewModel.load(id) })
}

@Composable
fun DetailsContent(
    state: DetailsUiState,
    onBack: () -> Unit,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is DetailsUiState.Loading ->
                    CircularProgressIndicator(color = AppLime)

                is DetailsUiState.Error ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text(state.message, color = Color.White, fontFamily = Poppins)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onRetry,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppLime,
                                contentColor = Color.Black
                            )
                        ) { Text("Retry", fontFamily = Poppins) }
                    }

                is DetailsUiState.Empty ->
                    Text("Meal not found", color = Color.White, fontFamily = Poppins)

                is DetailsUiState.Success -> MealDetails(state.meal)
            }
        }

        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(containerColor = AppButtonGray),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(56.dp)
        ) {
            Text("Back", color = Color.White, fontFamily = Poppins,
                fontWeight = FontWeight.Bold, fontSize = 22.sp)
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun MealDetails(meal: Meal) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = meal.imageUrl,
            contentDescription = meal.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = meal.name,
            color = AppLime,
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "${meal.category ?: "-"}  •  ${meal.area ?: "-"}",
            color = Color.White,
            fontFamily = LeagueSpartan,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = meal.instructions ?: "No instructions available",
            color = Color.White.copy(alpha = 0.85f),
            fontFamily = LeagueSpartan,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsPreview() {
    DetailsContent(state = DetailsUiState.Loading, onBack = {}, onRetry = {})
}