package com.example

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.components.FloatingMusicFab
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private var auth: FirebaseAuth? = null
    private var firestore: FirebaseFirestore? = null
    private var googleSignInClient: GoogleSignInClient? = null
    private var onAuthSuccessCallback: (() -> Unit)? = null

    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account)
        } catch (e: Throwable) {
            Log.e("MainActivity", "Google sign in failed", e)
            Toast.makeText(this, "Welcome Anushka! Continuing ✨", Toast.LENGTH_SHORT).show()
            onAuthSuccessCallback?.invoke()
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
        } catch (t: Throwable) {
            Log.e("MainActivity", "Failed to enable edge to edge", t)
        }

        try {
            initFirebaseAndAuth()
        } catch (t: Throwable) {
            Log.w("MainActivity", "Firebase init skipped: ${t.message}")
        }

        setContent {
            MyApplicationTheme(dynamicColor = false) {
                MainAppNavHost(
                    onGoogleSignIn = { onComplete ->
                        onAuthSuccessCallback = onComplete
                        if (googleSignInClient != null) {
                            try {
                                signInLauncher.launch(googleSignInClient!!.signInIntent)
                            } catch (t: Throwable) {
                                Log.e("MainActivity", "Failed to launch Google Sign-In", t)
                                onComplete()
                            }
                        } else {
                            onComplete()
                        }
                    }
                )
            }
        }
    }

    private fun initFirebaseAndAuth() {
        try {
            val webClientIdRes = resources.getIdentifier("default_web_client_id", "string", packageName)
            if (webClientIdRes != 0) {
                val webClientId = resources.getString(webClientIdRes)
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(webClientId)
                    .requestEmail()
                    .build()
                googleSignInClient = GoogleSignIn.getClient(this, gso)
                auth = FirebaseAuth.getInstance()
                firestore = FirebaseFirestore.getInstance()
            }
        } catch (t: Throwable) {
            Log.w("MainActivity", "Firebase / Google Sign-In not configured: ${t.message}")
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        try {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth?.signInWithCredential(credential)?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth?.currentUser
                    user?.let {
                        val userData = hashMapOf(
                            "uid" to it.uid,
                            "email" to (it.email ?: ""),
                            "displayName" to (it.displayName ?: "Anushka"),
                            "lastLogin" to System.currentTimeMillis()
                        )
                        firestore?.collection("users")?.document(it.uid)?.set(userData)
                    }
                }
                onAuthSuccessCallback?.invoke()
            } ?: onAuthSuccessCallback?.invoke()
        } catch (t: Throwable) {
            Log.e("MainActivity", "Error in firebaseAuthWithGoogle", t)
            onAuthSuccessCallback?.invoke()
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
fun MainAppNavHost(
    onGoogleSignIn: (onSuccess: () -> Unit) -> Unit
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val isMuted by MusicManager.isMuted.collectAsStateWithLifecycle()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: "login"

    // Synchronize music with current screen
    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            "login", "check", "surprise", "home" -> {
                MusicManager.playTrack(context, R.raw.dummy_bgm)
            }
            "splash" -> {
                // Pause background music during intro video
                MusicManager.pause()
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
            startDestination = "login",
            modifier = Modifier.fillMaxSize()
        ) {
            composable("login") {
                var isSigningIn by remember { mutableStateOf(false) }
                LoginScreen(
                    isLoading = isSigningIn,
                    onSignInClick = {
                        isSigningIn = true
                        onGoogleSignIn {
                            isSigningIn = false
                            navController.navigate("biometric") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    },
                    onSkipClick = {
                        navController.navigate("biometric") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }

            composable("biometric") {
                BiometricScreen(
                    onAuthenticated = {
                        navController.navigate("splash") {
                            popUpTo("biometric") { inclusive = true }
                        }
                    }
                )
            }

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
                    // Do nothing or exit
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
                    // Exit app
                    (context as? FragmentActivity)?.finish()
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
