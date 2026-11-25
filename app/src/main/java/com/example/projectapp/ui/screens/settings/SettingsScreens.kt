package com.example.projectapp.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectapp.ui.screens.home.HomeBottomBar
import com.example.projectapp.ui.theme.*
import com.example.projectapp.viewmodel.NotifOption
import com.example.projectapp.viewmodel.SettingsUiState
import com.example.projectapp.viewmodel.SettingsViewModel


@Composable
private fun SettingsScaffold(
    title: String,
    onBack: () -> Unit,
    onTabSelected: (Int) -> Unit,
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        containerColor = AppBackground,
        snackbarHost = snackbarHost,
        bottomBar = { HomeBottomBar(selectedTab = 3, onTabSelected = onTabSelected) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = AppLime,
                    modifier = Modifier.clickable { onBack() }
                )
                Text(
                    text = title,
                    color = AppPurple,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            content()
        }
    }
}


@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onNotifications: () -> Unit,
    onPassword: () -> Unit,
    onAccountDeleted: () -> Unit,
    onTabSelected: (Int) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    SettingsScaffold("Settings", onBack, onTabSelected) {
        Spacer(Modifier.height(24.dp))
        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            SettingsItem(Icons.Default.Notifications, "Notification Setting", onNotifications)
            SettingsItem(Icons.Default.Lock, "Password Setting", onPassword)
            SettingsItem(Icons.Default.Person, "Delete Account") { showDeleteDialog = true }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete account?") },
            text = { Text("This action cannot be undone.") },
            confirmButton = {
                TextButton(onClick = { showDeleteDialog = false; onAccountDeleted() }) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
private fun SettingsItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(AppPurpleDark),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = label, tint = Color.White, modifier = Modifier.size(22.dp))
        }
        Spacer(Modifier.width(16.dp))
        Text(
            text = label,
            color = Color.White,
            fontFamily = LeagueSpartan,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = AppLime)
    }
}


@Composable
fun NotificationSettingsScreen(
    viewModel: SettingsViewModel,
    onBack: () -> Unit,
    onTabSelected: (Int) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    NotificationSettingsContent(state, viewModel::toggleNotif, onBack, onTabSelected)
}

@Composable
fun NotificationSettingsContent(
    state: SettingsUiState,
    onToggle: (NotifOption) -> Unit,
    onBack: () -> Unit,
    onTabSelected: (Int) -> Unit
) {
    SettingsScaffold("Notifications Settings", onBack, onTabSelected) {
        Spacer(Modifier.height(16.dp))
        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            NotifOption.values().forEach { option ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = option.label,
                        color = Color.White,
                        fontFamily = LeagueSpartan,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = option in state.enabledNotifs,
                        onCheckedChange = { onToggle(option) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = AppLime,
                            checkedBorderColor = Color.Transparent,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = AppPurple,
                            uncheckedBorderColor = Color.Transparent
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun PasswordSettingsScreen(
    viewModel: SettingsViewModel,
    onBack: () -> Unit,
    onForgotPassword: () -> Unit,
    onTabSelected: (Int) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val snackbar = remember { SnackbarHostState() }

    LaunchedEffect(state.passwordChanged) {
        if (state.passwordChanged) {
            snackbar.showSnackbar("Password changed")
            viewModel.passwordChangeHandled()
        }
    }

    PasswordSettingsContent(
        state = state,
        snackbar = snackbar,
        onCurrent = viewModel::onCurrentPassword,
        onNew = viewModel::onNewPassword,
        onConfirm = viewModel::onConfirmPassword,
        onChange = viewModel::changePassword,
        onBack = onBack,
        onForgotPassword = onForgotPassword,
        onTabSelected = onTabSelected
    )
}

@Composable
fun PasswordSettingsContent(
    state: SettingsUiState,
    snackbar: SnackbarHostState,
    onCurrent: (String) -> Unit,
    onNew: (String) -> Unit,
    onConfirm: (String) -> Unit,
    onChange: () -> Unit,
    onBack: () -> Unit,
    onForgotPassword: () -> Unit,
    onTabSelected: (Int) -> Unit
) {
    SettingsScaffold(
        title = "Password Settings",
        onBack = onBack,
        onTabSelected = onTabSelected,
        snackbarHost = { SnackbarHost(snackbar) }
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.weight(1f))
            PasswordField("Current Password", state.currentPassword, onCurrent)
            Text(
                text = "Forgot Password?",
                color = Color.White,
                fontFamily = LeagueSpartan,
                fontSize = 12.sp,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onForgotPassword() }
                    .padding(vertical = 8.dp)
            )
            Spacer(Modifier.height(16.dp))
            PasswordField("New Password", state.newPassword, onNew)
            Spacer(Modifier.height(16.dp))
            PasswordField("Confirm New Password", state.confirmPassword, onConfirm)

            state.passwordError?.let {
                Spacer(Modifier.height(12.dp))
                Text(it, color = Color(0xFFFF6B6B), fontFamily = LeagueSpartan, fontSize = 12.sp)
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onChange,
                colors = ButtonDefaults.buttonColors(containerColor = AppLime, contentColor = Color.Black),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 24.dp)
            ) {
                Text("Change Password", fontFamily = LeagueSpartan, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
}

@Composable
private fun PasswordField(label: String, value: String, onChange: (String) -> Unit) {
    var visible by remember { mutableStateOf(false) }
    Column {
        Text(
            text = label,
            color = AppPurple,
            fontFamily = LeagueSpartan,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp
        )
        Spacer(Modifier.height(4.dp))
        TextField(
            value = value,
            onValueChange = onChange,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            textStyle = TextStyle(fontFamily = LeagueSpartan, fontSize = 14.sp),
            trailingIcon = {
                Icon(
                    imageVector = if (visible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = "Toggle password",
                    tint = AppPurple,
                    modifier = Modifier.clickable { visible = !visible }
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color(0xFF1B1B1B),
                unfocusedTextColor = Color(0xFF1B1B1B),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() = SettingsScreen({}, {}, {}, {}, {})

@Preview(showBackground = true)
@Composable
fun NotificationSettingsPreview() =
    NotificationSettingsContent(SettingsUiState(), {}, {}, {})

@Preview(showBackground = true)
@Composable
fun PasswordSettingsPreview() = PasswordSettingsContent(
    SettingsUiState(), remember { SnackbarHostState() }, {}, {}, {}, {}, {}, {}, {}
)