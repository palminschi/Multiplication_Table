package com.palmdev.learn_math.utils

import android.content.Context
import android.media.MediaPlayer
import com.palmdev.learn_math.R

class Sounds(private val context: Context) {

    private lateinit var mediaPlayer: MediaPlayer

    fun playClick() {
        mediaPlayer = MediaPlayer.create(context, R.raw.sound_click)
        mediaPlayer.start()
    }

    fun playWrongAnswer() {
        mediaPlayer = MediaPlayer.create(context, R.raw.sound_wrong)
        mediaPlayer.start()
    }
}