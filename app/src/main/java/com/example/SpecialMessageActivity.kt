package com.example

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat

class SpecialMessageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_special_message)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        val closeButton = findViewById<ImageButton>(R.id.closeButton)
        closeButton.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        MusicManager.start(this, R.raw.messeage_music)
    }

    override fun onPause() {
        super.onPause()
        MusicManager.pause()
    }
}
