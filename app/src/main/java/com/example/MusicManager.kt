package com.example

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object MusicManager {
    private var mediaPlayer: MediaPlayer? = null
    private var currentResId: Int = 0

    private val _isMuted = MutableStateFlow(false)
    val isMuted: StateFlow<Boolean> = _isMuted.asStateFlow()

    fun getCurrentResId(): Int = currentResId

    fun isMuted(): Boolean = _isMuted.value

    fun toggleMute() {
        setMuted(!_isMuted.value)
    }

    fun setMuted(muted: Boolean) {
        _isMuted.value = muted
        try {
            val volume = if (muted) 0f else 1f
            mediaPlayer?.setVolume(volume, volume)
        } catch (e: Exception) {
            Log.e("MusicManager", "Error setting volume", e)
        }
    }

    fun playTrack(context: Context, resId: Int) {
        try {
            if (resId == 0) return

            // If already playing this track, ensure it's playing and volume is right
            if (mediaPlayer != null && currentResId == resId) {
                if (mediaPlayer?.isPlaying == false) {
                    val volume = if (_isMuted.value) 0f else 1f
                    mediaPlayer?.setVolume(volume, volume)
                    mediaPlayer?.start()
                }
                return
            }

            // Stop previous player
            stopAndRelease()

            currentResId = resId
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                val uri = Uri.parse("android.resource://${context.packageName}/$resId")
                setDataSource(context.applicationContext, uri)
                isLooping = true
                prepareAsync()
                setOnPreparedListener { mp ->
                    try {
                        val volume = if (_isMuted.value) 0f else 1f
                        mp.setVolume(volume, volume)
                        mp.start()
                    } catch (e: Exception) {
                        Log.e("MusicManager", "Error starting player after prepare", e)
                    }
                }
                setOnErrorListener { _, what, extra ->
                    Log.w("MusicManager", "MediaPlayer error: what=$what, extra=$extra")
                    true
                }
            }
        } catch (e: Throwable) {
            Log.e("MusicManager", "Error playing track $resId", e)
        }
    }

    fun start(context: Context, resId: Int) {
        playTrack(context, resId)
    }

    fun pause() {
        try {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
        } catch (e: Exception) {
            Log.e("MusicManager", "Error pausing music", e)
        }
    }

    fun resume() {
        try {
            if (mediaPlayer != null && mediaPlayer?.isPlaying == false) {
                val volume = if (_isMuted.value) 0f else 1f
                mediaPlayer?.setVolume(volume, volume)
                mediaPlayer?.start()
            }
        } catch (e: Exception) {
            Log.e("MusicManager", "Error resuming music", e)
        }
    }

    fun stopAndRelease() {
        try {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.stop()
                }
                it.release()
            }
        } catch (e: Exception) {
            Log.e("MusicManager", "Error releasing player", e)
        } finally {
            mediaPlayer = null
            currentResId = 0
        }
    }
}
