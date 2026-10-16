package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.MusicManager
import com.example.ui.theme.*
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun GlowingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = "glowing_button",
    content: @Composable RowScope.() -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("button_glitter_blink.json")
    )

    Box(modifier = modifier) {
        val gradientBrush = Brush.linearGradient(
            colors = listOf(DeepRose, WineRed)
        )
        Button(
            onClick = onClick,
            modifier = Modifier
                .testTag(testTag)
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    alpha = alpha
                )
                .shadow(
                    elevation = 14.dp,
                    shape = RoundedCornerShape(24.dp),
                    spotColor = DeepRose,
                    ambientColor = DeepRose
                )
                .background(gradientBrush, RoundedCornerShape(24.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(24.dp),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp)
        ) {
            ProvideTextStyle(
                value = LocalTextStyle.current.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            ) {
                content()
            }
        }

        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.matchParentSize()
        )
    }
}

@Composable
fun SparklesOverlay(modifier: Modifier = Modifier) {
    val sparkles = remember {
        List(35) {
            Sparkle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                size = Random.nextFloat() * 4.5f + 2f,
                phase = Random.nextFloat() * 2f * Math.PI.toFloat(),
                speed = Random.nextFloat() * 1.5f + 1f
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "sparkles")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        sparkles.forEach { sparkle ->
            val alpha = ((sin(time * sparkle.speed + sparkle.phase) + 1f) / 2f).coerceIn(0f, 1f)
            val driftY = (sparkle.y * height - time * sparkle.speed * 18f) % height
            val finalY = if (driftY < 0) driftY + height else driftY

            drawCircle(
                color = Color.White.copy(alpha = alpha * 0.75f),
                radius = sparkle.size * (alpha * 0.5f + 0.5f),
                center = Offset(sparkle.x * width, finalY)
            )
        }
    }
}

data class Sparkle(
    val x: Float,
    val y: Float,
    val size: Float,
    val phase: Float,
    val speed: Float
)

@Composable
fun ConfettiAnimation(isTriggered: Boolean = true) {
    if (!isTriggered) return

    val party = remember {
        Party(
            speed = 0f,
            maxSpeed = 15f,
            damping = 0.9f,
            angle = 90,
            spread = 90,
            colors = listOf(0xFFFF758F.toInt(), 0xFFFFD166.toInt(), 0xFFFF4D6D.toInt(), 0xFFFFCCD5.toInt()),
            emitter = Emitter(duration = 3, TimeUnit.SECONDS).perSecond(70),
            position = Position.Relative(0.0, -0.1).between(Position.Relative(1.0, -0.1))
        )
    }

    KonfettiView(
        modifier = Modifier.fillMaxSize(),
        parties = listOf(party)
    )
}

@Composable
fun HeartsBackground(modifier: Modifier = Modifier) {
    val heartsComposition by rememberLottieComposition(
        LottieCompositionSpec.Asset("hearts.json")
    )
    LottieAnimation(
        composition = heartsComposition,
        iterations = LottieConstants.IterateForever,
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun FloatingMusicFab(
    modifier: Modifier = Modifier,
    isMuted: Boolean,
    onToggleMute: () -> Unit
) {
    FloatingActionButton(
        onClick = onToggleMute,
        modifier = modifier
            .testTag("mute_fab_button")
            .size(52.dp),
        containerColor = DeepRose,
        contentColor = Color.White,
        shape = RoundedCornerShape(18.dp)
    ) {
        Icon(
            imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
            contentDescription = if (isMuted) "Unmute music" else "Mute music"
        )
    }
}
