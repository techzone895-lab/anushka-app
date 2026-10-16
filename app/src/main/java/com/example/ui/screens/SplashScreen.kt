package com.example.ui.screens

import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.R

@Composable
fun SplashScreen(
    onVideoComplete: () -> Unit
) {
    var hasCompleted by remember { mutableStateOf(false) }

    fun completeOnce() {
        if (!hasCompleted) {
            hasCompleted = true
            onVideoComplete()
        }
    }

    // Safety timeout: In case VideoView gets stuck or phone cannot decode, auto proceed after 6s
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(6000)
        completeOnce()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                try {
                    VideoView(context).apply {
                        val uri = Uri.parse("android.resource://${context.packageName}/${R.raw.zyvraon_intro}")
                        setVideoURI(uri)
                        setOnCompletionListener {
                            completeOnce()
                        }
                        setOnErrorListener { _, _, _ ->
                            completeOnce()
                            true
                        }
                        start()
                    }
                } catch (t: Throwable) {
                    completeOnce()
                    android.view.View(context)
                }
            }
        )

        // Skip intro button in top right or bottom
        Button(
            onClick = { completeOnce() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
                .testTag("skip_intro_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White.copy(alpha = 0.25f),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("Skip ✨", fontSize = 14.sp)
        }
    }
}
