package com.example.projectapp.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectapp.data.allItems
import com.example.projectapp.ui.theme.AppBackground
import com.example.projectapp.ui.theme.AppButtonGray
import com.example.projectapp.ui.theme.AppLime
import com.example.projectapp.ui.theme.LeagueSpartan
import com.example.projectapp.ui.theme.Poppins

@Composable
fun DetailsScreen(id: Int, onBack: () -> Unit) {
    val item = allItems.find { it.id == id }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (item == null) {
            Spacer(modifier = Modifier.weight(1f))
            Text("Item not found (id = $id)", color = Color.White, fontFamily = Poppins)
            Spacer(modifier = Modifier.weight(1f))
        } else {
            Image(
                painter = painterResource(id = item.image),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = item.title,
                color = AppLime,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            if (item.minutes > 0) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "${item.minutes} Minutes  •  ${item.kcal} Kcal",
                    color = Color.White,
                    fontFamily = LeagueSpartan,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "ID: $id",
                color = Color.White.copy(alpha = 0.6f),
                fontFamily = LeagueSpartan,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(containerColor = AppButtonGray),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(56.dp)
        ) {
            Text("Back", color = Color.White, fontFamily = Poppins,
                fontWeight = FontWeight.Bold, fontSize = 22.sp)
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsPreview() {
    DetailsScreen(id = 1, onBack = {})
}