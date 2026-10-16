package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.HeartsBackground
import com.example.ui.components.SparklesOverlay
import com.example.ui.theme.*

@Composable
fun AnushkaCheckScreen(
    onYesClicked: () -> Unit
) {
    val context = LocalContext.current
    var showDenialDialog by remember { mutableStateOf(false) }

    val gradient = Brush.verticalGradient(
        colors = listOf(
            SoftBlush,
            PeachBlush,
            Color(0xFFFFD1DC),
            SoftBlush
        )
    )

    val infiniteTransition = rememberInfiniteTransition(label = "pulse_heart")
    val heartScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heartScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        HeartsBackground()
        SparklesOverlay()

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
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 34.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .scale(heartScale)
                        .background(Color(0xFFFFE0E9), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "💖", fontSize = 42.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Kya aap sach me meri Anushka ho? 🙈❤️",
                    style = Typography.headlineMedium,
                    color = DeepRose,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "A secret surprise is waiting only for the prettiest girl ✨",
                    style = Typography.bodyMedium,
                    color = TextMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(34.dp))

                Button(
                    onClick = onYesClicked,
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepRose),
                    modifier = Modifier
                        .testTag("yes_anushka_button")
                        .fillMaxWidth()
                        .height(54.dp)
                        .shadow(8.dp, RoundedCornerShape(24.dp), spotColor = DeepRose)
                ) {
                    Text(
                        text = "Yes, tumhari Anushka! 🥰",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedButton(
                    onClick = {
                        showDenialDialog = true
                        Toast.makeText(
                            context,
                            "Arey itni pyaari aur khubsurat toh bas meri Anushka hi hai! Jhooth mat bolo! 😉❤️",
                            Toast.LENGTH_LONG
                        ).show()
                    },
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = DeepRose),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.horizontalGradient(listOf(RomanticPink, DeepRose))
                    ),
                    modifier = Modifier
                        .testTag("not_anushka_button")
                        .fillMaxWidth()
                        .height(54.dp)
                ) {
                    Text(
                        text = "Nahi, koi aur hoon 😜",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        if (showDenialDialog) {
            AlertDialog(
                onDismissRequest = { showDenialDialog = false },
                title = {
                    Text("Jhooth pakda gaya! 🙈", style = Typography.titleLarge, color = DeepRose)
                },
                text = {
                    Text(
                        "Itni cute, pyaari aur sweet ladki duniya mein sirf ek hi ho sakti hai — meri Anushka! Ab jaldi se 'Yes' pe click karo! 🥰💖",
                        style = Typography.bodyMedium,
                        color = TextDark
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDenialDialog = false
                            onYesClicked()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DeepRose)
                    ) {
                        Text("Haan, main hi hoon! 🥰", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDenialDialog = false }) {
                        Text("Hehe achha 🙈", color = DeepRose)
                    }
                },
                shape = RoundedCornerShape(24.dp),
                containerColor = Color.White
            )
        }
    }
}
