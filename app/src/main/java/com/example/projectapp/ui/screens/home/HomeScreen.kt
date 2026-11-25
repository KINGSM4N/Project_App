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
import com.example.projectapp.data.weeklyChallenge
import com.example.projectapp.ui.theme.AppBackground
import com.example.projectapp.ui.theme.AppLime
import com.example.projectapp.ui.theme.AppPurple
import com.example.projectapp.ui.theme.LeagueSpartan
import com.example.projectapp.ui.theme.Poppins
import com.example.projectapp.viewmodel.HomeUiState
import com.example.projectapp.viewmodel.HomeViewModel
import coil.compose.AsyncImage
import com.example.projectapp.data.Meal
import com.example.projectapp.viewmodel.MealsContent

@Composable
fun HomeScreen(
    onItemClick: (String) -> Unit,
    onProfileClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    HomeContent(
        state = state,
        onItemClick = onItemClick,
        onFavoriteClick = { viewModel.toggleFavorite(it) },
        onTabSelected = {
            when (it) {
                2 -> onFavoritesClick()
                3 -> onProfileClick()
                else -> viewModel.onTabSelected(it)
            }
        },
        onRetry = { viewModel.loadMeals() }
    )
}

@Composable
fun HomeContent(
    state: HomeUiState,
    onItemClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    onTabSelected: (Int) -> Unit,
    onRetry: () -> Unit
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

            when (val content = state.content) {
                is MealsContent.Loading -> item { LoadingBox() }
                is MealsContent.Error -> item { ErrorBox(content.message, onRetry) }
                is MealsContent.Empty -> item { EmptyBox() }
                is MealsContent.Success -> {
                    item { SectionTitle("Recommendations", showSeeAll = true) }
                    item {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(content.recommendations) { meal ->
                                RecommendationCard(
                                    item = meal,
                                    isFavorite = meal.id in state.favorites,
                                    onClick = { onItemClick(meal.id) },
                                    onFavoriteClick = { onFavoriteClick(meal.id) }
                                )
                            }
                        }
                    }

                    item { WeeklyChallenge() }

                    item { SectionTitle("Articles & Tips", showSeeAll = false) }
                    item {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(content.articles) { meal ->
                                ArticleCard(
                                    item = meal,
                                    isFavorite = meal.id in state.favorites,
                                    onClick = { onItemClick(meal.id) },
                                    onFavoriteClick = { onFavoriteClick(meal.id) }
                                )
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun LoadingBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = AppLime)
    }
}

@Composable
private fun ErrorBox(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            color = Color.White,
            fontFamily = Poppins,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = AppLime,
                contentColor = Color.Black
            )
        ) {
            Text("Retry", fontFamily = Poppins)
        }
    }
}

@Composable
private fun EmptyBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No meals available",
            color = Color.White,
            fontFamily = Poppins,
            fontSize = 14.sp
        )
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
    item: Meal,
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
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
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
                text = item.name,
                color = AppLime,
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
private fun WeeklyChallenge() {
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
                .background(AppBackground),
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
    item: Meal,
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
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            )
            FavoriteStar(isFavorite, onFavoriteClick, Modifier.align(Alignment.TopEnd))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.name,
            color = Color.White,
            fontFamily = Poppins,
            fontSize = 14.sp
        )
    }
}

@Composable
fun HomeBottomBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
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
        onTabSelected = {},
        onRetry = {}
    )
}