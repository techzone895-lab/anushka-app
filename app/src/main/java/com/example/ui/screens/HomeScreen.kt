package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.FloatingBalloonsAnimation
import com.example.ui.components.ConfettiAnimation
import com.example.ui.components.GlowingButton
import com.example.ui.components.HeartsBackground
import com.example.ui.components.SparklesOverlay
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToAngel: () -> Unit
) {
    var isGreetingLoaded by remember { mutableStateOf(false) }

    val gradient = Brush.verticalGradient(
        colors = listOf(
            SoftBlush,
            PeachBlush,
            Color(0xFFFFCCD5),
            SoftBlush
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        HeartsBackground()
        FloatingBalloonsAnimation()
        SparklesOverlay()
        ConfettiAnimation(isTriggered = isGreetingLoaded)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .widthIn(max = 500.dp)
        ) {
            AnimatedRomanticGreeting(onLoaded = { isGreetingLoaded = true })

            Spacer(modifier = Modifier.height(28.dp))

            Card(
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = GlassWhite),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .shadow(12.dp, RoundedCornerShape(26.dp), spotColor = RomanticPink)
                    .border(1.2.dp, Color.White.copy(alpha = 0.85f), RoundedCornerShape(26.dp))
            ) {
                Text(
                    text = "Every moment with you feels like a dream come true. You make my world infinitely brighter! 🌹✨",
                    style = Typography.bodyLarge,
                    color = TextMedium,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 24.sp,
                    modifier = Modifier.padding(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(34.dp))

            GlowingButton(
                onClick = onNavigateToAngel,
                testTag = "see_my_angel_button"
            ) {
                Text("See My Angel 🕊️💖")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = "Next")
            }
        }
    }
}

@Composable
private fun AnimatedRomanticGreeting(onLoaded: () -> Unit = {}) {
    var startAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        startAnimation = true
    }

    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.5f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "greeting_scale"
    )

    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1200),
        label = "greeting_alpha"
    )

    LaunchedEffect(alpha) {
        if (alpha == 1f) {
            onLoaded()
        }
    }

    val pulseTransition = rememberInfiniteTransition(label = "pulseTransition")
    val pulseScale by pulseTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.07f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "TO MY SPECIAL ONE ✨",
            fontSize = 17.sp,
            letterSpacing = 4.sp,
            fontWeight = FontWeight.ExtraBold,
            color = DeepRose,
            modifier = Modifier
                .alpha(alpha)
                .padding(bottom = 6.dp)
        )
        Text(
            text = "Anushka",
            style = Typography.headlineLarge.copy(fontSize = 62.sp),
            color = WineRed,
            modifier = Modifier
                .scale(scale * pulseScale)
                .alpha(alpha)
        )
    }
}
