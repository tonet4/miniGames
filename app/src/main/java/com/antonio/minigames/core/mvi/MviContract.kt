package com.antonio.minigames.core.mvi

/** Everything the screen needs to draw itself. Immutable data class. */
interface UiState

/** An action coming from the user or the screen (tap, back, retry...). */
interface UiIntent

/** A one-shot event for the screen (navigate, play a sound, show a snackbar...). */
interface UiEffect
