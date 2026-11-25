package com.example.projectapp.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectapp.data.HomeItem
import com.example.projectapp.data.weeklyChallenge
import com.example.projectapp.ui.theme.AppBackground
import com.example.projectapp.ui.theme.AppLime
import com.example.projectapp.ui.theme.AppPurple
import com.example.projectapp.ui.theme.LeagueSpartan
import com.example.projectapp.ui.theme.Poppins
import com.example.projectapp.viewmodel.HomeUiState
import com.example.projectapp.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onItemClick: (Int) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    HomeContent(
        state = state,
        onItemClick = onItemClick,
        onFavoriteClick = { viewModel.toggleFavorite(it) },
        onTabSelected = { viewModel.onTabSelected(it) }
    )
}

@Composable
fun HomeContent(
    state: HomeUiState,
    onItemClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    onTabSelected: (Int) -> Unit
) {
    Scaffold(
        containerColor = AppBackground,
        bottomBar = { HomeBottomBar(state.selectedTab, onTabSelected) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { HomeHeader(state.userName) }

            item { QuickActions() }

            item { SectionTitle("Recommendations", showSeeAll = true) }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.recommendations) { item ->
                        RecommendationCard(
                            item = item,
                            isFavorite = item.id in state.favorites,
                            onClick = { onItemClick(item.id) },
                            onFavoriteClick = { onFavoriteClick(item.id) }
                        )
                    }
                }
            }

            item { WeeklyChallenge(onClick = { onItemClick(weeklyChallenge.id) }) }

            item { SectionTitle("Articles & Tips", showSeeAll = false) }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.articles) { item ->
                        ArticleCard(
                            item = item,
                            isFavorite = item.id in state.favorites,
                            onClick = { onItemClick(item.id) },
                            onFavoriteClick = { onFavoriteClick(item.id) }
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }
        }
    }
}


@Composable
private fun HomeHeader(userName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Text(
                text = "Hi, $userName",
                color = AppPurple,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
            Text(
                text = "It's time to challenge your limits.",
                color = Color.White,
                fontFamily = Poppins,
                fontSize = 13.sp
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(Icons.Default.Search, contentDescription = "Search", tint = AppPurple)
            Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = AppPurple)
            Icon(Icons.Default.Person, contentDescription = "Profile", tint = AppPurple)
        }
    }
}

@Composable
private fun QuickActions() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        QuickAction(Icons.Default.Build, "Workout", AppLime)
        VerticalDivider(color = Color.White, modifier = Modifier.padding(vertical = 8.dp))
        QuickAction(Icons.Default.Check, "Progress\nTracking", AppPurple)
        VerticalDivider(color = Color.White, modifier = Modifier.padding(vertical = 8.dp))
        QuickAction(Icons.Default.Favorite, "Nutrition", AppPurple)
        VerticalDivider(color = Color.White, modifier = Modifier.padding(vertical = 8.dp))
        QuickAction(Icons.Default.Face, "Community", AppPurple)
    }
}

@Composable
private fun QuickAction(icon: ImageVector, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = label, tint = color, modifier = Modifier.size(36.dp))
        Text(
            text = label,
            color = color,
            fontFamily = LeagueSpartan,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun SectionTitle(title: String, showSeeAll: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = AppLime,
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )
        if (showSeeAll) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "See All",
                    color = Color.White,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
                Icon(Icons.Default.PlayArrow, contentDescription = null, tint = AppLime)
            }
        }
    }
}

@Composable
private fun FavoriteStar(isFavorite: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Default.Star,
        contentDescription = "Favorite",
        tint = if (isFavorite) AppLime else Color.White,
        modifier = modifier
            .padding(8.dp)
            .size(24.dp)
            .clickable { onClick() }
    )
}

@Composable
private fun RecommendationCard(
    item: HomeItem,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(180.dp)
            .border(1.dp, Color.White, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
    ) {
        Box {
            Image(
                painter = painterResource(id = item.image),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            )
            FavoriteStar(isFavorite, onFavoriteClick, Modifier.align(Alignment.TopEnd))
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(AppPurple),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White)
            }
        }
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = item.title,
                color = AppLime,
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${item.minutes} Minutes", color = Color.White,
                    fontFamily = LeagueSpartan, fontSize = 12.sp)
                Text("${item.kcal} Kcal", color = Color.White,
                    fontFamily = LeagueSpartan, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun WeeklyChallenge(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppPurple)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(AppBackground)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Weekly Challenge",
                    color = AppLime,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = weeklyChallenge.title,
                    color = Color.White,
                    fontFamily = Poppins,
                    fontSize = 13.sp
                )
            }
            Image(
                painter = painterResource(id = weeklyChallenge.image),
                contentDescription = weeklyChallenge.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
        }
    }
}

@Composable
private fun ArticleCard(
    item: HomeItem,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(180.dp)
            .clickable { onClick() }
    ) {
        Box {
            Image(
                painter = painterResource(id = item.image),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(24.dp))
            )
            FavoriteStar(isFavorite, onFavoriteClick, Modifier.align(Alignment.TopEnd))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.title,
            color = Color.White,
            fontFamily = Poppins,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun HomeBottomBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.DateRange,
        Icons.Default.Star,
        Icons.Default.Face
    )
    NavigationBar(containerColor = AppPurple) {
        icons.forEachIndexed { index, icon ->
            NavigationBarItem(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                icon = { Icon(icon, contentDescription = null, modifier = Modifier.size(30.dp)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppLime,
                    unselectedIconColor = Color.White,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    HomeContent(
        state = HomeUiState(),
        onItemClick = {},
        onFavoriteClick = {},
        onTabSelected = {}
    )
}