package com.example.projectapp.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.verticalScroll
import com.example.projectapp.ui.screens.home.HomeBottomBar
import com.example.projectapp.ui.theme.*
import com.example.projectapp.viewmodel.ProfileUiState
import com.example.projectapp.viewmodel.ProfileViewModel

@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit,
    onHomeClick: () -> Unit
) {
    val saved by viewModel.uiState.collectAsState()
    EditProfileContent(
        saved = saved,
        onUpdate = { viewModel.save(it); onBack() },
        onBack = onBack,
        onHomeClick = onHomeClick
    )
}

@Composable
fun EditProfileContent(
    saved: ProfileUiState,
    onUpdate: (ProfileUiState) -> Unit,
    onBack: () -> Unit,
    onHomeClick: () -> Unit
) {
    var draft by remember { mutableStateOf(saved) }

    Scaffold(
        containerColor = AppBackground,
        bottomBar = {
            HomeBottomBar(selectedTab = 3, onTabSelected = { if (it == 0) onHomeClick() })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            ProfileHeader(state = draft, showEditBadge = true, onBack = onBack)

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileField("Full name", draft.fullName,
                    onChange = { draft = draft.copy(fullName = it) })
                ProfileField("Email", draft.email, KeyboardType.Email,
                    onChange = { draft = draft.copy(email = it) })
                ProfileField("Mobile Number", draft.phone, KeyboardType.Phone,
                    onChange = { draft = draft.copy(phone = it) })
                ProfileField("Date of birth", draft.birthDate,
                    onChange = { draft = draft.copy(birthDate = it) })
                ProfileField("Weight", draft.weight, KeyboardType.Decimal, suffix = "Kg",
                    onChange = { draft = draft.copy(weight = it) })
                ProfileField("Height", draft.height, KeyboardType.Decimal, suffix = "CM",
                    onChange = { draft = draft.copy(height = it) })
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { onUpdate(draft) },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppLime,
                    contentColor = Color.Black
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    "Update Profile",
                    fontFamily = LeagueSpartan,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ProfileField(
    label: String,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    suffix: String? = null,
    onChange: (String) -> Unit
) {
    Column {
        Text(
            text = label,
            color = AppPurpleDark,
            fontFamily = LeagueSpartan,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            value = value,
            onValueChange = onChange,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            suffix = suffix?.let { { Text(it) } },
            textStyle = androidx.compose.ui.text.TextStyle(
                fontFamily = LeagueSpartan,
                fontSize = 14.sp
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color(0xFF1B1B1B),
                unfocusedTextColor = Color(0xFF1B1B1B),
                focusedSuffixColor = Color(0xFF1B1B1B),
                unfocusedSuffixColor = Color(0xFF1B1B1B),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfilePreview() {
    EditProfileContent(ProfileUiState(), {}, {}, {})
}