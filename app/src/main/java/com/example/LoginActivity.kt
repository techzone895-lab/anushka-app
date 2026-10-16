package com.example

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.ui.theme.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : ComponentActivity() {

    private var auth: FirebaseAuth? = null
    private var firestore: FirebaseFirestore? = null
    private var googleSignInClient: GoogleSignInClient? = null

    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account)
        } catch (e: ApiException) {
            Log.e("LoginActivity", "Google sign in failed", e)
            Toast.makeText(this, "Welcome Anushka! Continuing ✨", Toast.LENGTH_SHORT).show()
            proceedToNext()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT
            )
        )
        
        try {
            auth = FirebaseAuth.getInstance()
            firestore = FirebaseFirestore.getInstance()
            
            val webClientId = resources.getString(
                resources.getIdentifier("default_web_client_id", "string", packageName)
            )
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientId)
                .requestEmail()
                .build()
            googleSignInClient = GoogleSignIn.getClient(this, gso)
        } catch (e: Exception) {
            Log.w("LoginActivity", "Firebase not fully configured. Defaulting to Guest.", e)
        }

        setContent {
            MyApplicationTheme(dynamicColor = false) {
                var isLoading by remember { mutableStateOf(false) }
                
                LoginScreen(
                    isLoading = isLoading,
                    onSignInClick = {
                        isLoading = true
                        if (googleSignInClient != null) {
                            signInLauncher.launch(googleSignInClient!!.signInIntent)
                        } else {
                            proceedToNext()
                        }
                    },
                    onSkipClick = { proceedToNext() }
                )
            }
        }
        
        // Auto-login check
        try {
            if (auth?.currentUser != null) {
                proceedToNext()
            }
        } catch (e: Exception) {}
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
                            ?.addOnSuccessListener { proceedToNext() }
                            ?.addOnFailureListener { proceedToNext() }
                    } ?: proceedToNext()
                } else {
                    proceedToNext()
                }
            } ?: proceedToNext()
        } catch (e: Exception) {
            proceedToNext()
        }
    }

    private fun proceedToNext() {
        startActivity(Intent(this, BiometricAuthActivity::class.java))
        finish()
    }
}

@Composable
fun LoginScreen(isLoading: Boolean, onSignInClick: () -> Unit, onSkipClick: () -> Unit) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            SoftBlush,
            PeachBlush,
            Color(0xFFFFCCD5),
            SoftBlush
        )
    )

    val heartsComposition by rememberLottieComposition(
        LottieCompositionSpec.Asset("hearts.json")
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
                modifier = Modifier.padding(horizontal = 28.dp, vertical = 36.dp)
            ) {
                Text(
                    text = "Hello Anushka ❤️",
                    style = Typography.headlineLarge,
                    color = DeepRose,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "A special little world crafted with love, just for you ✨",
                    style = Typography.bodyMedium,
                    color = TextMedium,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(36.dp))
                
                if (isLoading) {
                    CircularProgressIndicator(color = DeepRose)
                } else {
                    Button(
                        onClick = onSignInClick,
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DeepRose),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .shadow(8.dp, RoundedCornerShape(24.dp), spotColor = DeepRose)
                    ) {
                        Text(
                            text = "Sign in with Google",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(14.dp))
                    
                    OutlinedButton(
                        onClick = onSkipClick,
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
                            text = "Enter Directly 💖",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
