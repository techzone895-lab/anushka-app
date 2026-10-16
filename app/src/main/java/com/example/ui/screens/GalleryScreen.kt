package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.ui.theme.*

data class MemoryItem(
    val resId: Int,
    val caption: String
)

val memoryItemsList = listOf(
    MemoryItem(R.drawable.anushka_1, "Precious Smile 💕"),
    MemoryItem(R.drawable.anushka_2, "Sweet Moments ✨"),
    MemoryItem(R.drawable.anushka_3, "My Favorite Girl 🌹"),
    MemoryItem(R.drawable.anushka_4, "Cutest Charm 🌸"),
    MemoryItem(R.drawable.anushka_5, "Pure Magic 💖"),
    MemoryItem(R.drawable.anushka_6, "Forever Angel 🕊️"),
    MemoryItem(R.drawable.anushka_7, "Radiant Glow ✨"),
    MemoryItem(R.drawable.anushka_8, "Heart stealer 🥰"),
    MemoryItem(R.drawable.anushka_9, "Dreamy Eyes 🌙"),
    MemoryItem(R.drawable.anushka_10, "Pure Joy 🎈"),
    MemoryItem(R.drawable.anushka_11, "Beautiful Soul 💫"),
    MemoryItem(R.drawable.anushka_12, "My Whole World 👑"),
    MemoryItem(R.drawable.anushka_13, "Forever & Always 💖")
)

@Composable
fun GalleryScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onNavigateToLetter: () -> Unit
) {
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SoftBlush)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Surface(
                color = SoftBlush.copy(alpha = 0.95f),
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .testTag("gallery_back_button")
                            .size(44.dp)
                            .background(Color.White, CircleShape)
                            .shadow(3.dp, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = DeepRose
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Our Memories",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepRose
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "💕", fontSize = 18.sp)
                        }
                        Text(
                            text = "Every moment with you is precious ✨",
                            fontSize = 13.sp,
                            color = TextMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Grid of Photos
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 12.dp, bottom = 96.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                itemsIndexed(memoryItemsList) { index, item ->
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("gallery_item_$index")
                            .clickable { selectedIndex = index }
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(3f / 4f)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(PeachBlush)
                            ) {
                                AsyncImage(
                                    model = item.resId,
                                    contentDescription = item.caption,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item.caption,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextDark,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    tint = RomanticPink,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Bottom Action Button Bar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            SoftBlush.copy(alpha = 0.9f),
                            SoftBlush
                        )
                    )
                )
                .padding(horizontal = 24.dp, vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = onNavigateToLetter,
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DeepRose),
                modifier = Modifier
                    .testTag("open_secret_letter_button")
                    .fillMaxWidth()
                    .widthIn(max = 440.dp)
                    .height(56.dp)
                    .shadow(12.dp, RoundedCornerShape(28.dp), spotColor = DeepRose)
            ) {
                Icon(
                    imageVector = Icons.Default.Mail,
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "A Secret Letter For You 💌",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        // Fullscreen Modal
        selectedIndex?.let { idx ->
            val item = memoryItemsList[idx]
            ImageViewerModal(
                imageResId = item.resId,
                caption = item.caption,
                currentIndex = idx,
                totalCount = memoryItemsList.size,
                onClose = { selectedIndex = null },
                onPrev = {
                    selectedIndex = (idx - 1 + memoryItemsList.size) % memoryItemsList.size
                },
                onNext = {
                    selectedIndex = (idx + 1) % memoryItemsList.size
                }
            )
        }
    }
}
