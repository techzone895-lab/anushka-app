package com.example

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.ui.theme.*
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit
import kotlin.math.sin
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private var isPlayerPrepared = false

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
        
        initMediaPlayer()

        setContent {
            MyApplicationTheme(dynamicColor = false) {
                AppNavigation(
                    onPlayAudio = { resId -> playAudio(resId) },
                    onStopAudio = { stopAudio() }
                )
            }
        }
    }

    private fun initMediaPlayer() {
        try {
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error initializing player", e)
        }
    }

    private fun playAudio(resId: Int) {
        try {
            // Stop any background music from previous activity
            MusicManager.stopAndRelease()

            mediaPlayer?.reset()
            isPlayerPrepared = false
            val uri = Uri.parse("android.resource://${packageName}/$resId")
            mediaPlayer?.setDataSource(this, uri)
            mediaPlayer?.isLooping = true
            mediaPlayer?.prepare()
            isPlayerPrepared = true
            mediaPlayer?.start()
        } catch (e: Exception) {
            Log.e("MainActivity", "Error playing audio", e)
        }
    }

    private fun stopAudio() {
        try {
            if (isPlayerPrepared && mediaPlayer?.isPlaying == true) {
                mediaPlayer?.stop()
            }
            isPlayerPrepared = false
        } catch (e: Exception) {
            Log.e("MainActivity", "Error stopping audio", e)
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            if (isPlayerPrepared && mediaPlayer != null && mediaPlayer?.isPlaying == false) {
                mediaPlayer?.start()
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error in onResume", e)
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            if (isPlayerPrepared && mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error in onPause", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            if (isPlayerPrepared && mediaPlayer?.isPlaying == true) {
                mediaPlayer?.stop()
            }
            mediaPlayer?.release()
        } catch (e: Exception) {
            Log.e("MainActivity", "Error in onDestroy", e)
        } finally {
            mediaPlayer = null
            isPlayerPrepared = false
        }
    }
}

@Composable
fun AppNavigation(
    onPlayAudio: (Int) -> Unit,
    onStopAudio: () -> Unit
) {
    val navController = rememberNavController()
    var isMuted by remember { mutableStateOf(false) }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    DisposableEffect(currentRoute) {
        if (currentRoute == "angel") {
            onPlayAudio(R.raw.bg_audio)
        } else if (currentRoute == "home") {
            onPlayAudio(R.raw.dummy_bgm)
        }
        
        onDispose {
            onStopAudio()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RomanticHomeGreetingScreen(
                        modifier = Modifier.padding(innerPadding),
                        onNavigate = {
                            navController.navigate("angel")
                        }
                    )
                }
            }
            composable("angel") {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HeavenAngelScreen(
                        modifier = Modifier.padding(innerPadding),
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
        
        SparklesOverlay(modifier = Modifier.fillMaxSize())

        FloatingActionButton(
            onClick = {
                isMuted = !isMuted
                MusicManager.setMuted(isMuted)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .padding(bottom = 24.dp),
            containerColor = DeepRose,
            contentColor = Color.White
        ) {
            Icon(
                imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                contentDescription = if (isMuted) "Unmute" else "Mute"
            )
        }
    }
}

@Composable
fun RomanticHomeGreetingScreen(modifier: Modifier = Modifier, onNavigate: () -> Unit = {}) {
    var isGreetingLoaded by remember { mutableStateOf(false) }
    
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
        modifier = modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = heartsComposition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.fillMaxSize()
        )
        
        FloatingBalloonsAnimation()
        ConfettiAnimation(isTriggered = isGreetingLoaded)
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            AnimatedRomanticGreeting(onLoaded = { isGreetingLoaded = true })
            
            Spacer(modifier = Modifier.height(28.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = GlassWhite),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .shadow(8.dp, RoundedCornerShape(24.dp), spotColor = RomanticPink)
                    .border(1.dp, Color.White.copy(alpha = 0.8f), RoundedCornerShape(24.dp))
            ) {
                Text(
                    text = "Every moment with you feels like a dream come true. You make my world infinitely brighter! 🌹✨",
                    style = Typography.bodyMedium,
                    color = TextMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(18.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            GlowingButton(onClick = onNavigate) {
                Text("See My Angel 🕊️💖")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = "Next")
            }
        }
    }
}

@Composable
fun AnimatedRomanticGreeting(onLoaded: () -> Unit = {}) {
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
        label = "scale"
    )

    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1400),
        label = "alpha"
    )

    LaunchedEffect(alpha) {
        if (alpha == 1f) {
            onLoaded()
        }
    }

    val pulseTransition = rememberInfiniteTransition(label = "pulseTransition")
    val pulseScale by pulseTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "TO MY SPECIAL ONE ✨",
            fontSize = 18.sp,
            letterSpacing = 4.sp,
            fontWeight = FontWeight.Bold,
            color = DeepRose,
            modifier = Modifier
                .alpha(alpha)
                .padding(bottom = 6.dp)
        )
        Text(
            text = "Anushka",
            style = Typography.headlineLarge.copy(fontSize = 58.sp),
            color = WineRed,
            modifier = Modifier
                .scale(scale * pulseScale)
                .alpha(alpha)
        )
    }
}

@Composable
fun HeavenAngelScreen(modifier: Modifier = Modifier, onBack: () -> Unit = {}) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            SoftBlush,
            PeachBlush,
            SoftBlush
        )
    )

    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        ConfettiAnimation()
        
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .background(Color.White.copy(alpha = 0.8f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = DeepRose
            )
        }
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = "My Angel Anushka ✨",
                style = Typography.headlineMedium,
                color = DeepRose,
                modifier = Modifier.padding(vertical = 12.dp)
            )
            
            Box(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .shadow(16.dp, RoundedCornerShape(28.dp), spotColor = RomanticPink)
            ) {
                AsyncImage(
                    model = R.drawable.anushka_photo,
                    contentDescription = "Anushka's Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .aspectRatio(3f / 4f)
                        .clip(RoundedCornerShape(28.dp))
                        .background(Color.White)
                        .border(3.dp, Color.White, RoundedCornerShape(28.dp))
                )
            }
            
            Spacer(modifier = Modifier.height(18.dp))
            
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = GlassWhite),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .shadow(8.dp, RoundedCornerShape(24.dp), spotColor = RomanticPink)
                    .border(1.dp, Color.White.copy(alpha = 0.8f), RoundedCornerShape(24.dp))
            ) {
                Text(
                    text = "In a world full of ordinary things, you are my favorite masterpiece. Thank you for being in my life! 🌹❤️",
                    style = Typography.bodyMedium,
                    color = TextDark,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(16.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            GlowingButton(
                onClick = {
                    try {
                        val intent = Intent(context, GalleryActivity::class.java)
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            ) {
                Text("Our Memories & Gallery 📸")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.PhotoLibrary, contentDescription = "Gallery")
            }
        }
    }
}

@Composable
fun GlowingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.88f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400),
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
        List(40) {
            Sparkle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                size = Random.nextFloat() * 5f + 2f,
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
