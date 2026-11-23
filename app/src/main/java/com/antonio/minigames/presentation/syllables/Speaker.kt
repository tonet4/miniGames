package com.antonio.minigames.presentation.syllables

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

private val Spanish = Locale("es", "ES")
private const val SPEECH_RATE = 0.8f

class Speaker(context: Context) {

    private var isReady = false
    private var pendingText: String? = null

    private val textToSpeech: TextToSpeech = TextToSpeech(context.applicationContext) { status ->
        if (status == TextToSpeech.SUCCESS) onReady()
    }

    fun speak(text: String) {
        if (isReady) {
            textToSpeech.speak(text.lowercase(Spanish), TextToSpeech.QUEUE_FLUSH, null, text)
        } else {
            pendingText = text
        }
    }

    fun release() {
        textToSpeech.stop()
        textToSpeech.shutdown()
    }

    private fun onReady() {
        textToSpeech.setLanguage(Spanish)
        textToSpeech.setSpeechRate(SPEECH_RATE)
        isReady = true
        pendingText?.let { speak(it) }
        pendingText = null
    }
}

@Composable
fun rememberSpeaker(): Speaker {
    val context = LocalContext.current
    val speaker = remember(context) { Speaker(context) }
    DisposableEffect(speaker) {
        onDispose { speaker.release() }
    }
    return speaker
}
