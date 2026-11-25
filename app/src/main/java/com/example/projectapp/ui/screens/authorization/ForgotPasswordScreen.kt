package com.example.projectapp.ui.screens.authorization

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectapp.ui.theme.AppBackground
import com.example.projectapp.ui.theme.AppButtonGray
import com.example.projectapp.ui.theme.AppPurple
import com.example.projectapp.ui.theme.LeagueSpartan
import com.example.projectapp.ui.theme.Poppins
import com.example.projectapp.viewmodel.ForgotPasswordViewModel

@Composable
fun ForgotPasswordScreen(
    onContinueClick: () -> Unit,
    onBack: () -> Unit = {},
    viewModel: ForgotPasswordViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthTopBar(title = "Forgotten Password", onBack = onBack)

        Spacer(modifier = Modifier.weight(0.5f))

        Text(
            text = "Forgot Password?",
            color = Color.White,
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.weight(0.5f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppPurple)
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            AuthField(
                label = "Enter your email address",
                value = state.email,
                onValueChange = { viewModel.onEmailChange(it) },
                placeholder = "example@example.com"
            )

            state.error?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    color = Color(0xFFB00020),
                    fontFamily = LeagueSpartan,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { if (viewModel.validate()) onContinueClick() },
            colors = ButtonDefaults.buttonColors(containerColor = AppButtonGray),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(56.dp)
        ) {
            Text(
                text = "Continue",
                color = Color.White,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen(onContinueClick = {})
}