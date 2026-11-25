package com.example.projectapp.ui.screens.setup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectapp.R
import com.example.projectapp.ui.screens.authorization.AuthField
import com.example.projectapp.ui.screens.authorization.AuthTopBar
import com.example.projectapp.ui.theme.AppBackground
import com.example.projectapp.ui.theme.AppButtonGray
import com.example.projectapp.ui.theme.AppLime
import com.example.projectapp.ui.theme.AppPurple
import com.example.projectapp.ui.theme.AppTextDark
import com.example.projectapp.ui.theme.LeagueSpartan
import com.example.projectapp.ui.theme.Poppins
import com.example.projectapp.viewmodel.SetupViewModel

@Composable
private fun SetupLayout(
    title: String,
    onBack: () -> Unit,
    buttonText: String,
    buttonEnabled: Boolean = true,
    onButtonClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthTopBar(title = "", onBack = onBack)

        Text(
            text = title,
            color = Color.White,
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            content = content
        )

        Button(
            onClick = onButtonClick,
            enabled = buttonEnabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = AppButtonGray,
                contentColor = Color.White,
                disabledContainerColor = AppButtonGray,
                disabledContentColor = Color.White.copy(alpha = 0.4f)
            ),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(56.dp)
        ) {
            Text(
                text = buttonText,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun PurplePanel(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppPurple)
            .padding(horizontal = 24.dp, vertical = 24.dp),
        content = content
    )
}

@Composable
private fun BigValue(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 56.sp
    )
}

@Composable
private fun AppSlider(value: Int, range: IntRange, onChange: (Int) -> Unit) {
    Slider(
        value = value.toFloat(),
        onValueChange = { onChange(it.toInt()) },
        valueRange = range.first.toFloat()..range.last.toFloat(),
        colors = SliderDefaults.colors(
            thumbColor = AppLime,
            activeTrackColor = AppLime,
            inactiveTrackColor = AppPurple
        ),
        modifier = Modifier.padding(horizontal = 32.dp)
    )
}


@Composable
fun SetupIntroScreen(onNext: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.setup_1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground.copy(alpha = 0.5f))
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Consistency Is\nThe Key To Progress.\nDon't Give Up!",
                color = AppLime,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            PurplePanel {
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    color = AppTextDark,
                    fontFamily = LeagueSpartan,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onNext,
                colors = ButtonDefaults.buttonColors(containerColor = AppButtonGray),
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(56.dp)
            ) {
                Text("Next", color = Color.White, fontFamily = Poppins,
                    fontWeight = FontWeight.Bold, fontSize = 22.sp)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


@Composable
fun SetupGenderScreen(viewModel: SetupViewModel, onContinue: () -> Unit, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsState()

    SetupLayout(
        title = "What's Your Gender",
        onBack = onBack,
        buttonText = "Continue",
        buttonEnabled = state.gender.isNotEmpty(),
        onButtonClick = onContinue
    ) {
        GenderCircle("♂", "Male", state.gender == "Male") { viewModel.onGender("Male") }
        Spacer(modifier = Modifier.height(32.dp))
        GenderCircle("♀", "Female", state.gender == "Female") { viewModel.onGender("Female") }
    }
}

@Composable
private fun GenderCircle(symbol: String, label: String, selected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(if (selected) AppLime else AppButtonGray)
                .border(2.dp, Color.White.copy(alpha = 0.6f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = symbol,
                color = if (selected) AppBackground else Color.White,
                fontSize = 64.sp
            )
        }
        Text(
            text = label,
            color = Color.White,
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}


@Composable
fun SetupAgeScreen(viewModel: SetupViewModel, onContinue: () -> Unit, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsState()

    SetupLayout("How Old Are You?", onBack, "Continue", onButtonClick = onContinue) {
        BigValue("${state.age}")
        Spacer(modifier = Modifier.height(24.dp))
        AppSlider(state.age, 10..100) { viewModel.onAge(it) }
    }
}


@Composable
fun SetupWeightScreen(viewModel: SetupViewModel, onContinue: () -> Unit, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsState()

    SetupLayout("What Is Your Weight?", onBack, "Continue", onButtonClick = onContinue) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, Color.White.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
        ) {
            UnitChip("KG", state.isKg) { viewModel.onUnit(true) }
            UnitChip("LB", !state.isKg) { viewModel.onUnit(false) }
        }
        Spacer(modifier = Modifier.height(32.dp))
        BigValue("${state.weight} ${if (state.isKg) "Kg" else "Lb"}")
        Spacer(modifier = Modifier.height(24.dp))
        AppSlider(state.weight, 20..300) { viewModel.onWeight(it) }
    }
}

@Composable
private fun UnitChip(text: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(if (selected) AppLime else Color.Transparent)
            .clickable { onClick() }
            .padding(horizontal = 36.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) AppBackground else Color.White,
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun SetupHeightScreen(viewModel: SetupViewModel, onContinue: () -> Unit, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsState()

    SetupLayout("What Is Your Height?", onBack, "Continue", onButtonClick = onContinue) {
        BigValue("${state.height} cm")
        Spacer(modifier = Modifier.height(24.dp))
        AppSlider(state.height, 100..220) { viewModel.onHeight(it) }
    }
}


@Composable
fun SetupGoalScreen(viewModel: SetupViewModel, onContinue: () -> Unit, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsState()
    val goals = listOf("Lose Weight", "Gain Weight", "Muscle Mass Gain", "Shape Body", "Others")

    SetupLayout(
        title = "What Is Your Goal?",
        onBack = onBack,
        buttonText = "Continue",
        buttonEnabled = state.goal.isNotEmpty(),
        onButtonClick = onContinue
    ) {
        PurplePanel {
            goals.forEach { goal ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .clickable { viewModel.onGoal(goal) }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = goal,
                        color = AppTextDark,
                        fontFamily = LeagueSpartan,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                    RadioButton(selected = state.goal == goal, onClick = null)
                }
            }
        }
    }
}


@Composable
fun SetupActivityScreen(viewModel: SetupViewModel, onContinue: () -> Unit, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsState()
    val levels = listOf("Beginner", "Intermediate", "Advance")

    SetupLayout(
        title = "Physical Activity Level",
        onBack = onBack,
        buttonText = "Continue",
        buttonEnabled = state.activity.isNotEmpty(),
        onButtonClick = onContinue
    ) {
        levels.forEach { level ->
            val selected = state.activity == level
            Button(
                onClick = { viewModel.onActivity(level) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selected) AppLime else Color.White,
                    contentColor = if (selected) AppBackground else AppPurple
                ),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(48.dp)
            ) {
                Text(level, fontFamily = Poppins, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun SetupProfileScreen(viewModel: SetupViewModel, onStart: () -> Unit, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsState()

    SetupLayout(
        title = "Fill Your Profile",
        onBack = onBack,
        buttonText = "Start",
        onButtonClick = { if (viewModel.validateProfile()) onStart() }
    ) {
        PurplePanel {
            AuthField("Full name", state.fullName, { viewModel.onFullName(it) }, "Madison Smith")
            Spacer(modifier = Modifier.height(8.dp))
            AuthField("Nickname", state.nickname, { viewModel.onNickname(it) }, "Madison")
            Spacer(modifier = Modifier.height(8.dp))
            AuthField("Email", state.email, { viewModel.onEmail(it) }, "madison@example.com")
            Spacer(modifier = Modifier.height(8.dp))
            AuthField("Mobile Number", state.phone, { viewModel.onPhone(it) }, "+123 567 89000")

            state.error?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(it, color = Color(0xFFB00020), fontFamily = LeagueSpartan, fontSize = 14.sp)
            }
        }
    }
}

@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun SetupGenderPreview() {
    SetupGenderScreen(viewModel = SetupViewModel(), onContinue = {}, onBack = {})
}

@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun SetupGoalPreview() {
    SetupGoalScreen(viewModel = SetupViewModel(), onContinue = {}, onBack = {})
}

@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun SetupProfilePreview() {
    SetupProfileScreen(viewModel = SetupViewModel(), onStart = {}, onBack = {})
}