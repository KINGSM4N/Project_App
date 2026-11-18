package com.example.projectapp.ui.screens.authorization

import androidx.compose.foundation.Image
import com.example.projectapp.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectapp.ui.theme.AppBackground
import com.example.projectapp.ui.theme.AppLime
import com.example.projectapp.ui.theme.AppPurple
import com.example.projectapp.ui.theme.LeagueSpartan
import com.example.projectapp.ui.theme.Poppins
import kotlinx.coroutines.delay


@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    var currentStep by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        delay(2000)
        currentStep = 1
    }

    when (currentStep) {
        0 -> OnboardingA()
        1 -> OnboardingB(onNext = { currentStep = 2 }, onSkip = onFinished)
        2 -> OnboardingC(onNext = { currentStep = 3 }, onSkip = onFinished)
        3 -> OnboardingD(onNext = onFinished)
    }
}


@Composable
fun OnboardingA() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.onboarding_1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground.copy(alpha = 0.6f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to",
                color = AppLime,
                fontFamily = LeagueSpartan,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 24.sp
            )
            Text(
                text = "FT",
                color = AppPurple,
                fontFamily = Poppins,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSize = 86.sp
            )
            Text(
                text = "FoodTracker",
                color = AppLime,
                fontFamily = Poppins,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSize = 36.sp
            )
        }
    }
}

@Composable
fun OnboardingB(onNext: () -> Unit, onSkip: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.onboarding_2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground.copy(alpha = 0.6f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Start Your Journey Towards A More Active Lifestyle",
                color = AppLime,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Button(
                onClick = onNext,
                colors = ButtonDefaults.buttonColors(containerColor = AppPurple),
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(75.dp)
                    .padding(bottom = 20.dp)
            ) {
                Text(
                    text = "Next",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        TextButton(
            onClick = onSkip,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "Skip >",
                color = AppLime,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun OnboardingC(onNext: () -> Unit, onSkip: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.onboarding_3),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground.copy(alpha = 0.6f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Find Nutrition Tips That Fit Your Lifestyle",
                color = AppLime,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Button(
                onClick = onNext,
                colors = ButtonDefaults.buttonColors(containerColor = AppPurple),
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(75.dp)
                    .padding(bottom = 20.dp)
            ) {
                Text(
                    text = "Next",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        TextButton(
            onClick = onSkip,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "Skip >",
                color = AppLime,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun OnboardingD(onNext: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.onboarding_4),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground.copy(alpha = 0.6f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Challenge Yourself",
                color = AppLime,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Button(
                onClick = onNext,
                colors = ButtonDefaults.buttonColors(containerColor = AppPurple),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(75.dp)
                    .padding(bottom = 20.dp)
            ) {
                Text(
                    text = "Get Started",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Preview(showBackground = true, name = "Page A")
@Composable
fun OnboardingAPreview() {
    OnboardingA()
}

@Preview(showBackground = true, name = "Page B")
@Composable
fun OnboardingBPreview() {
    OnboardingB(onNext = {}, onSkip = {})
}

@Preview(showBackground = true, name = "Page C")
@Composable
fun OnboardingCPreview() {
    OnboardingC(onNext = {}, onSkip = {})
}

@Preview(showBackground = true, name = "Page D")
@Composable
fun OnboardingDPreview() {
    OnboardingD(onNext = {})
}