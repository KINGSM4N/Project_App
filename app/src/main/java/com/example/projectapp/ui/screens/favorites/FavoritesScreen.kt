package com.example.projectapp.ui.screens.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.projectapp.data.Meal
import com.example.projectapp.ui.screens.home.HomeBottomBar
import com.example.projectapp.ui.theme.*
import com.example.projectapp.viewmodel.FavFilter
import com.example.projectapp.viewmodel.FavoritesUiState
import com.example.projectapp.viewmodel.FavoritesViewModel

@Composable
fun FavoritesScreen(
    onBack: () -> Unit,
    onItemClick: (String) -> Unit,
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    viewModel: FavoritesViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    FavoritesContent(
        state = state,
        onBack = onBack,
        onFilter = viewModel::setFilter,
        onRemove = viewModel::remove,
        onItemClick = onItemClick,
        onTabSelected = {
            when (it) {
                0 -> onHomeClick()
                3 -> onProfileClick()
            }
        }
    )
}

@Composable
fun FavoritesContent(
    state: FavoritesUiState,
    onBack: () -> Unit,
    onFilter: (FavFilter) -> Unit,
    onRemove: (String) -> Unit,
    onItemClick: (String) -> Unit,
    onTabSelected: (Int) -> Unit
) {
    Scaffold(
        containerColor = AppBackground,
        bottomBar = { HomeBottomBar(selectedTab = 2, onTabSelected = onTabSelected) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            FavoritesHeader(onBack)
            SortRow(state.filter, onFilter)
            Spacer(Modifier.height(16.dp))

            when {
                state.isLoading -> Box(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator(color = AppLime) }

                state.items.isEmpty() -> Box(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No favorites yet.\nTap the star on any meal to save it.",
                        color = Color.White,
                        fontFamily = Poppins,
                        fontSize = 14.sp
                    )
                }

                else -> LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(state.items, key = { it.id }) { meal ->
                        FavoriteCard(
                            meal = meal,
                            onClick = { onItemClick(meal.id) },
                            onStarClick = { onRemove(meal.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FavoritesHeader(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "Back",
            tint = AppLime,
            modifier = Modifier.clickable { onBack() }
        )
        Text(
            text = "Favorites",
            color = AppPurple,
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.weight(1f)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(Icons.Default.Search, contentDescription = "Search", tint = AppPurple)
            Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = AppPurple)
            Icon(Icons.Default.Person, contentDescription = "Profile", tint = AppPurple)
        }
    }
}

@Composable
private fun SortRow(selected: FavFilter, onFilter: (FavFilter) -> Unit) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Sort By", color = AppLime, fontFamily = LeagueSpartan, fontSize = 16.sp)
        FavFilter.entries.forEach { filter ->
            val isSelected = filter == selected
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(CircleShape)
                    .background(if (isSelected) AppLime else Color.White)
                    .clickable { onFilter(filter) }
                    .padding(vertical = 3.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    filter.label,
                    color = Color.Black,
                    fontFamily = LeagueSpartan,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun FavoriteCard(
    meal: Meal,
    onClick: () -> Unit,
    onStarClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(108.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = meal.name,
                color = Color.Black,
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 19.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))
            MetaText("Tap to view recipe")
        }

        Box(
            modifier = Modifier
                .width(125.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(20.dp))
        ) {
            AsyncImage(
                model = meal.imageUrl,
                contentDescription = meal.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Icon(
                Icons.Default.Star,
                contentDescription = "Remove from favorites",
                tint = AppLime,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp)
                    .size(20.dp)
                    .clickable { onStarClick() }
            )
        }
    }
}

@Composable
private fun MetaText(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(6.dp).clip(CircleShape).background(AppPurple))
        Spacer(Modifier.width(4.dp))
        Text(text, color = Color.Black, fontFamily = LeagueSpartan, fontSize = 10.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesPreview() {
    FavoritesContent(
        state = FavoritesUiState(
            isLoading = false,
            items = listOf(Meal("1", "Sample meal", null, null, null, null))
        ),
        onBack = {},
        onFilter = {},
        onRemove = {},
        onItemClick = {},
        onTabSelected = {}
    )
}