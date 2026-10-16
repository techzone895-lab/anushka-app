package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.FloatingBalloonsAnimation
import com.example.ui.components.ConfettiAnimation
import com.example.ui.components.SparklesOverlay
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun BirthdaySurpriseScreen(
    onProceed: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(4500)
        onProceed()
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(
            SoftBlush,
            PeachBlush,
            Color(0xFFFFCCD5),
            SoftBlush
        )
    )

    val infiniteTransition = rememberInfiniteTransition(label = "pulse_crown")
    val crownScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.22f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "crownScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        FloatingBalloonsAnimation()
        SparklesOverlay()
        ConfettiAnimation()

        Card(
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = GlassWhite),
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .widthIn(max = 440.dp)
                .shadow(16.dp, RoundedCornerShape(32.dp), spotColor = RomanticPink)
                .border(1.5.dp, Color.White.copy(alpha = 0.85f), RoundedCornerShape(32.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(28.dp)
            ) {
                Text(
                    text = "👑✨",
                    fontSize = 52.sp,
                    modifier = Modifier.scale(crownScale)
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Yay! Welcome to your special world, Anushka! 💖",
                    style = Typography.headlineMedium,
                    color = DeepRose,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "You bring so much joy & love into my life! Getting your surprise ready... 🌹",
                    style = Typography.bodyLarge,
                    color = TextMedium,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = onProceed,
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepRose),
                    modifier = Modifier
                        .testTag("open_surprise_now_button")
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text(
                        text = "Open My Surprise 🎁",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
