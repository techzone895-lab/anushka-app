package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.ui.components.ConfettiAnimation
import com.example.ui.components.GlowingButton
import com.example.ui.components.SparklesOverlay
import com.example.ui.theme.*

@Composable
fun AngelScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onNavigateToGallery: () -> Unit
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            SoftBlush,
            PeachBlush,
            SoftBlush
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        SparklesOverlay()
        ConfettiAnimation()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Top Bar with back button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .testTag("angel_back_button")
                        .size(44.dp)
                        .background(Color.White.copy(alpha = 0.85f), CircleShape)
                        .shadow(4.dp, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Home",
                        tint = DeepRose
                    )
                }

                Text(
                    text = "🕊️💖",
                    fontSize = 22.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "My Angel Anushka ✨",
                style = Typography.headlineMedium,
                color = DeepRose,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Portrait image
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .widthIn(max = 340.dp)
                    .aspectRatio(3f / 4f)
                    .shadow(18.dp, RoundedCornerShape(28.dp), spotColor = RomanticPink)
            ) {
                AsyncImage(
                    model = R.drawable.anushka_photo,
                    contentDescription = "Anushka's Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(28.dp))
                        .background(Color.White)
                        .border(3.dp, Color.White, RoundedCornerShape(28.dp))
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = GlassWhite),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 440.dp)
                    .shadow(10.dp, RoundedCornerShape(24.dp), spotColor = RomanticPink)
                    .border(1.2.dp, Color.White.copy(alpha = 0.85f), RoundedCornerShape(24.dp))
            ) {
                Text(
                    text = "In a world full of ordinary things, you are my favorite masterpiece. Thank you for being in my life! 🌹❤️",
                    style = Typography.bodyLarge,
                    color = TextDark,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 24.sp,
                    modifier = Modifier.padding(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            GlowingButton(
                onClick = onNavigateToGallery,
                testTag = "go_to_gallery_button"
            ) {
                Text("Our Memories & Gallery 📸")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.PhotoLibrary, contentDescription = "Gallery")
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
