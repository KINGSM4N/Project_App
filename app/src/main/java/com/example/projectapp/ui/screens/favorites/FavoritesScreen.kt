package com.example.projectapp.ui.screens.favorites

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectapp.data.FavType
import com.example.projectapp.data.FavoriteItem
import com.example.projectapp.ui.screens.home.HomeBottomBar
import com.example.projectapp.ui.theme.*
import com.example.projectapp.viewmodel.FavFilter
import com.example.projectapp.viewmodel.FavoritesUiState
import com.example.projectapp.viewmodel.FavoritesViewModel

@Composable
fun FavoritesScreen(
    onBack: () -> Unit,
    onItemClick: (Int) -> Unit,
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    viewModel: FavoritesViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    FavoritesContent(
        state = state,
        onBack = onBack,
        onFilter = viewModel::setFilter,
        onStar = viewModel::toggleStar,
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
    onStar: (Int) -> Unit,
    onItemClick: (Int) -> Unit,
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

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(state.items, key = { it.id }) { item ->
                    FavoriteCard(
                        item = item,
                        isStarred = item.id in state.starred,
                        onClick = { onItemClick(item.id) },
                        onStarClick = { onStar(item.id) }
                    )
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
        FavFilter.values().forEach { filter ->
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
    item: FavoriteItem,
    isStarred: Boolean,
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
                text = item.title,
                color = Color.Black,
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 19.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))
            if (item.description.isNotEmpty()) {
                Text(
                    text = item.description,
                    color = Color.DarkGray,
                    fontFamily = LeagueSpartan,
                    fontSize = 15.sp,
                    lineHeight = 12.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MetaText("${item.minutes} Minutes")
                    MetaText(if (item.type == FavType.VIDEO) "${item.kcal} Kcal" else "${item.kcal} Cal")
                }
                if (item.exercises > 0) MetaText("${item.exercises} Exercises")
            }
        }

        Box(
            modifier = Modifier
                .width(125.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(20.dp))
        ) {
            Image(
                painter = painterResource(item.image),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Icon(
                Icons.Default.Star,
                contentDescription = "Favorite",
                tint = if (isStarred) AppLime else Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp)
                    .size(20.dp)
                    .clickable { onStarClick() }
            )
            if (item.type == FavType.VIDEO) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(AppPurple),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Play",
                        tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
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
    var state by remember { mutableStateOf(FavoritesUiState()) }
    FavoritesContent(
        state = state,
        onBack = {},
        onFilter = { state = state.copy(filter = it) },
        onStar = { id ->
            state = state.copy(
                starred = if (id in state.starred) state.starred - id else state.starred + id
            )
        },
        onItemClick = {},
        onTabSelected = {}
    )
}