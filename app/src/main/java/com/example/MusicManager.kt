package com.example

import android.content.Context
import android.media.MediaPlayer
import android.util.Log

object MusicManager {
    private var mediaPlayer: MediaPlayer? = null
    private var currentResId: Int = 0
    private var isUserMuted: Boolean = false

    fun getCurrentResId(): Int = currentResId
    fun isMuted(): Boolean = isUserMuted

    fun setMuted(muted: Boolean) {
        isUserMuted = muted
        try {
            val volume = if (muted) 0f else 1f
            mediaPlayer?.setVolume(volume, volume)
        } catch (e: Exception) {
            Log.e("MusicManager", "Error setting volume", e)
        }
    }

    fun start(context: Context, resId: Int) {
        try {
            if (mediaPlayer != null && currentResId != resId) {
                stopAndRelease()
            }

            if (mediaPlayer == null) {
                currentResId = resId
                mediaPlayer = MediaPlayer.create(context.applicationContext, resId)
                mediaPlayer?.isLooping = true
                val volume = if (isUserMuted) 0f else 1f
                mediaPlayer?.setVolume(volume, volume)
                mediaPlayer?.start()
            } else if (mediaPlayer?.isPlaying == false) {
                mediaPlayer?.start()
            }
        } catch (e: Exception) {
            Log.e("MusicManager", "Error starting music", e)
        }
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
