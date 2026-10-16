package com.example

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

class BirthdaySurpriseActivity : ComponentActivity() {
    private val handler = Handler(Looper.getMainLooper())
    private val navigateRunnable = Runnable {
        try {
            startActivity(Intent(this@BirthdaySurpriseActivity, MainActivity::class.java))
            finish()
        } catch (e: Throwable) {
            android.widget.Toast.makeText(this@BirthdaySurpriseActivity, "Error: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(
                    android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT
                ),
                navigationBarStyle = SystemBarStyle.light(
                    android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT
                )
            )
            
            handler.postDelayed(navigateRunnable, 4500)
    
            setContent {
                MyApplicationTheme(dynamicColor = false) {
                    RomanticSurpriseScreen()
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
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
    
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(navigateRunnable)
    }
}

@Composable
fun RomanticSurpriseScreen() {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            SoftBlush,
            PeachBlush,
            Color(0xFFFFCCD5),
            SoftBlush
        )
    )

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val crownScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
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
                modifier = Modifier.padding(28.dp)
            ) {
                Text(
                    text = "👑✨",
                    fontSize = 48.sp,
                    modifier = Modifier.scale(crownScale)
                )

                Spacer(modifier = Modifier.height(16.dp))

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
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
