package com.example

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.ui.theme.*

class AnushkaCheckActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT
            )
        )
        
        MusicManager.start(this, R.raw.dummy_bgm)
        
        setContent {
            MyApplicationTheme(dynamicColor = false) {
                AnushkaCheckScreen(
                    onYesClicked = {
                        try {
                            val intent = Intent(this@AnushkaCheckActivity, BirthdaySurpriseActivity::class.java)
                            startActivity(intent)
                            finish()
                        } catch (e: Throwable) {
                            Toast.makeText(this@AnushkaCheckActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        MusicManager.resume()
    }

    override fun onPause() {
        super.onPause()
        MusicManager.pause()
    }
}

@Composable
fun AnushkaCheckScreen(onYesClicked: () -> Unit) {
    val context = LocalContext.current
    
    val gradient = Brush.verticalGradient(
        colors = listOf(
            SoftBlush,
            PeachBlush,
            Color(0xFFFFD1DC),
            SoftBlush
        )
    )

    val heartsComposition by rememberLottieComposition(
        LottieCompositionSpec.Asset("hearts.json")
    )

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
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
        LottieAnimation(
            composition = heartsComposition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.fillMaxSize()
        )

        Card(
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = GlassWhite),
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .shadow(16.dp, RoundedCornerShape(32.dp), spotColor = RomanticPink)
                .border(1.5.dp, Color.White.copy(alpha = 0.8f), RoundedCornerShape(32.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp)
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

                Spacer(modifier = Modifier.height(36.dp))

                Button(
                    onClick = onYesClicked,
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepRose),
                    modifier = Modifier
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
    }
}
