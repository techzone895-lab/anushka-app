package com.example

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.components.FloatingMusicFab
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Safety catch: prevent unexpected uncaught exceptions from crashing the app
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("MainActivity", "Uncaught exception safely caught: ${throwable.message}", throwable)
            defaultHandler?.uncaughtException(thread, throwable)
        }

        try {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(
                    android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT
                ),
                navigationBarStyle = SystemBarStyle.light(
                    android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT
                )
            )
        } catch (t: Throwable) {
            Log.e("MainActivity", "Failed to enable edge to edge", t)
        }

        setContent {
            MyApplicationTheme(dynamicColor = false) {
                MainAppNavHost()
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

    override fun onDestroy() {
        super.onDestroy()
        MusicManager.stopAndRelease()
    }
}

@Composable
fun MainAppNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val isMuted by MusicManager.isMuted.collectAsState()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: "splash"

    // Synchronize music with current screen
    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            "splash" -> {
                // Pause background music during intro video
                MusicManager.pause()
            }
            "check", "surprise", "home" -> {
                MusicManager.playTrack(context, R.raw.dummy_bgm)
            }
            "angel" -> {
                MusicManager.playTrack(context, R.raw.bg_audio)
            }
            "gallery" -> {
                MusicManager.playTrack(context, R.raw.new_song)
            }
            "letter" -> {
                MusicManager.playTrack(context, R.raw.messeage_music)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = "splash",
            modifier = Modifier.fillMaxSize()
        ) {
            composable("splash") {
                SplashScreen(
                    onVideoComplete = {
                        navController.navigate("check") {
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                )
            }

            composable("check") {
                BackHandler {
                    (context as? ComponentActivity)?.finish()
                }
                AnushkaCheckScreen(
                    onYesClicked = {
                        navController.navigate("surprise") {
                            popUpTo("check") { inclusive = true }
                        }
                    }
                )
            }

            composable("surprise") {
                BackHandler {
                    // Prevent accidental back during surprise
                }
                BirthdaySurpriseScreen(
                    onProceed = {
                        navController.navigate("home") {
                            popUpTo("surprise") { inclusive = true }
                        }
                    }
                )
            }

            composable("home") {
                BackHandler {
                    (context as? ComponentActivity)?.finish()
                }
                HomeScreen(
                    onNavigateToAngel = {
                        navController.navigate("angel")
                    }
                )
            }

            composable("angel") {
                BackHandler {
                    navController.popBackStack()
                }
                AngelScreen(
                    onBack = { navController.popBackStack() },
                    onNavigateToGallery = {
                        navController.navigate("gallery")
                    }
                )
            }

            composable("gallery") {
                BackHandler {
                    navController.popBackStack()
                }
                GalleryScreen(
                    onBack = { navController.popBackStack() },
                    onNavigateToLetter = {
                        navController.navigate("letter")
                    }
                )
            }

            composable("letter") {
                BackHandler {
                    navController.popBackStack()
                }
                SpecialMessageScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }

        // Floating music toggle (only show on interactive screens, hide during splash video)
        if (currentRoute != "splash") {
            FloatingMusicFab(
                isMuted = isMuted,
                onToggleMute = { MusicManager.toggleMute() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(20.dp)
            )
        }
    }
}
