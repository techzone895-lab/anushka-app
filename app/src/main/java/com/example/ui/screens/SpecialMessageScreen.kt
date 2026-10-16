package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ConfettiAnimation
import com.example.ui.components.SparklesOverlay
import com.example.ui.theme.*

@Composable
fun SpecialMessageScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SoftBlush)
    ) {
        SparklesOverlay()
        ConfettiAnimation()

        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar
            Surface(
                color = SoftBlush.copy(alpha = 0.95f),
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier
                                .testTag("letter_back_button")
                                .size(44.dp)
                                .background(Color.White, CircleShape)
                                .shadow(3.dp, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = DeepRose
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "A Letter For You 💌",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepRose
                        )
                    }

                    Text(text = "🌹", fontSize = 24.sp)
                }
            }

            // Scrollable Letter Body
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 520.dp)
                        .border(1.dp, PeachBlush, RoundedCornerShape(28.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 26.dp, vertical = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "My Dearest Anushka ❤️",
                            style = Typography.headlineMedium,
                            color = DeepRose,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(36.dp)
                                    .height(1.5.dp)
                                    .background(RomanticPink)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = RomanticPink,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .width(36.dp)
                                    .height(1.5.dp)
                                    .background(RomanticPink)
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        val paragraphs = listOf(
                            "Pata nahi kaise shuru karoon... Jab se tum meri life mein aayi ho, sab kuch aur bhi khoobsurat aur pyara lagne laga hai.",
                            "Tumhari choti choti baatein, tumhari cute si smile, aur tumhara mere saath hona hi mera poora din bana deta hai. Jab bhi tumse baat hoti hai, aisa lagta hai waqt wahin tham jaye.",
                            "Mujhe bohot badi badi baatein karna toh nahi aata, par itna dil se keh sakta hoon ki tum mere liye bohot zyada special ho.",
                            "Maine ye chota sa app sirf tumhare liye banaya hai — taaki jab bhi tum ise dekho, tumhare chehre par ek pyaari si muskaan aa jaye! 😊✨",
                            "Har ek lamha jo tumhare sath bitaya hai, wo mere liye ek blessing hai. I promise to always cherish you, make you laugh, and stand by you."
                        )

                        paragraphs.forEach { para ->
                            Text(
                                text = para,
                                fontSize = 16.sp,
                                color = TextDark,
                                lineHeight = 26.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp)
                            )
                        }

                        Text(
                            text = "Thank you for being in my life and making it so beautiful, Anushka! 🌹",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextMedium,
                            lineHeight = 26.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 24.dp)
                        )

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "Forever Yours,",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "With all my love ❤️",
                                style = Typography.headlineMedium.copy(fontSize = 24.sp),
                                color = DeepRose
                            )
                        }

                        Spacer(modifier = Modifier.height(28.dp))

                        Box(
                            modifier = Modifier
                                .width(64.dp)
                                .height(3.dp)
                                .background(RomanticPink, RoundedCornerShape(2.dp))
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Text(
                            text = "\"You are my favorite feeling ✨\"",
                            fontStyle = FontStyle.Italic,
                            fontFamily = FontFamily.Serif,
                            fontSize = 17.sp,
                            color = DeepRose,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
