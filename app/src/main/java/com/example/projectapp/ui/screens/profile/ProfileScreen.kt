package com.example.projectapp.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectapp.ui.screens.home.HomeBottomBar
import com.example.projectapp.ui.theme.*
import com.example.projectapp.viewmodel.ProfileUiState
import com.example.projectapp.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit,
    onEditProfile: () -> Unit,
    onHomeClick: () -> Unit,
    onLogout: () -> Unit,
    onSettings: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    ProfileContent(state, onBack, onEditProfile, onHomeClick, onLogout, onSettings)
}

@Composable
fun ProfileContent(
    state: ProfileUiState,
    onBack: () -> Unit,
    onEditProfile: () -> Unit,
    onHomeClick: () -> Unit,
    onLogout: () -> Unit,
    onSettings: () -> Unit
) {
    val menu = listOf(
        Triple(Icons.Default.Person, "Profile", onEditProfile),
        Triple(Icons.Default.Star, "Favorite", {}),
        Triple(Icons.Default.Lock, "Privacy Policy", {}),
        Triple(Icons.Default.Settings, "Settings", onSettings),
        Triple(Icons.Default.Info, "Help", {}),
        Triple(Icons.AutoMirrored.Filled.ExitToApp, "Logout", onLogout),
    )

    Scaffold(
        containerColor = AppBackground,
        bottomBar = {
            HomeBottomBar(
                selectedTab = 3,
                onTabSelected = { if (it == 0) onHomeClick() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ProfileHeader(state = state, showEditBadge = false, onBack = onBack)

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                menu.forEach { (icon, label, onClick) ->
                    ProfileMenuItem(icon, label, onClick)
                }
            }
        }
    }
}

@Composable
private fun ProfileMenuItem(icon: ImageVector, label: String, onClick: () -> Unit) {
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
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            color = Color.White,
            fontFamily = LeagueSpartan,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = AppLime, modifier = Modifier.size(18.dp))
    }
}

@Composable
fun ProfileHeader(
    state: ProfileUiState,
    showEditBadge: Boolean,
    onBack: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppPurple)
                    .padding(top = 12.dp, bottom = 44.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onBack() }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = AppLime
                    )
                    Text(
                        text = "My Profile",
                        color = Color.White,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Box {
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(AppPurpleDark),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Avatar",
                            tint = Color.White,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                    if (showEditBadge) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(AppLime),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Change photo",
                                tint = Color.Black,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = state.fullName,
                    color = Color.White,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = state.email,
                    color = Color.White.copy(alpha = 0.7f),
                    fontFamily = LeagueSpartan,
                    fontSize = 12.sp
                )
                Text(
                    text = "Birthday: ${state.birthdayLabel}",
                    color = Color.White,
                    fontFamily = LeagueSpartan,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(36.dp))
        }

        StatsCard(
            state = state,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun StatsCard(state: ProfileUiState, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(AppPurpleDark)
            .padding(vertical = 12.dp)
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatItem("${state.weight} Kg", "Weight", Modifier.weight(1f))
        VerticalDivider(color = Color.White.copy(alpha = 0.6f))
        StatItem("${state.age}", "Years Old", Modifier.weight(1f))
        VerticalDivider(color = Color.White.copy(alpha = 0.6f))
        StatItem("${state.height} CM", "Height", Modifier.weight(1f))
    }
}

@Composable
private fun StatItem(value: String, label: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            value,
            color = Color.White,
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )
        Text(
            label,
            color = Color.White,
            fontFamily = LeagueSpartan,
            fontSize = 11.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    ProfileContent(ProfileUiState(), {}, {}, {}, {},{})
}