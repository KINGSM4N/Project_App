package com.example.projectapp.ui.screens.authorization

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectapp.R
import com.example.projectapp.ui.theme.AppBackground
import com.example.projectapp.ui.theme.AppButtonGray
import com.example.projectapp.ui.theme.AppLime
import com.example.projectapp.ui.theme.AppPurple
import com.example.projectapp.ui.theme.AppTextDark
import com.example.projectapp.ui.theme.LeagueSpartan
import com.example.projectapp.ui.theme.Poppins
import com.example.projectapp.viewmodel.SignUpViewModel

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onLoginClick: () -> Unit = {},
    onBack: () -> Unit = {},
    viewModel: SignUpViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .systemBarsPadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthTopBar(title = "Create Account", onBack = onBack)

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Let's Start!",
            color = Color.White,
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppPurple)
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            AuthField(
                label = "Full name",
                value = state.fullName,
                onValueChange = { viewModel.onNameChange(it) },
                placeholder = "Full name"
            )
            Spacer(modifier = Modifier.height(12.dp))
            AuthField(
                label = "Email or Mobile Number",
                value = state.contact,
                onValueChange = { viewModel.onContactChange(it) },
                placeholder = "+123 567 89000"
            )
            Spacer(modifier = Modifier.height(12.dp))
            AuthField(
                label = "Password",
                value = state.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                placeholder = "*************",
                isPassword = true
            )
            Spacer(modifier = Modifier.height(12.dp))
            AuthField(
                label = "Confirm Password",
                value = state.confirmPassword,
                onValueChange = { viewModel.onConfirmChange(it) },
                placeholder = "*************",
                isPassword = true
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

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = buildAnnotatedString {
                append("By continuing, you agree to\n")
                withStyle(SpanStyle(color = AppLime)) { append("Terms of Use") }
                append(" and ")
                withStyle(SpanStyle(color = AppLime)) { append("Privacy Policy.") }
            },
            color = Color.White,
            fontFamily = LeagueSpartan,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { if (viewModel.validate()) onSignUpSuccess() },
            colors = ButtonDefaults.buttonColors(containerColor = AppButtonGray),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(56.dp)
        ) {
            Text(
                text = "Sign Up",
                color = Color.White,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "or sign up with",
            color = Color.White,
            fontFamily = LeagueSpartan,
            fontWeight = FontWeight.Light,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Google",
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(10.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.facebook),
                contentDescription = "Facebook",
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row {
            Text(
                text = "Already have an account? ",
                color = Color.White,
                fontFamily = LeagueSpartan,
                fontWeight = FontWeight.Light,
                fontSize = 18.sp
            )
            Text(
                text = "Log in",
                color = AppLime,
                fontFamily = LeagueSpartan,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier.clickable { onLoginClick() }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun AuthTopBar(title: String, onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.arrowr),
            contentDescription = "Back",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(24.dp)
                .clickable { onBack() }
        )
        Text(
            text = title,
            color = AppLime,
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun AuthField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false
) {
    Text(
        text = label,
        color = AppTextDark,
        fontFamily = LeagueSpartan,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    )
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholder, fontFamily = LeagueSpartan) },
        textStyle = TextStyle(fontFamily = LeagueSpartan, fontSize = 16.sp),
        singleLine = true,
        visualTransformation =
            if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedTextColor = AppTextDark,
            unfocusedTextColor = AppTextDark,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(onSignUpSuccess = {})
}