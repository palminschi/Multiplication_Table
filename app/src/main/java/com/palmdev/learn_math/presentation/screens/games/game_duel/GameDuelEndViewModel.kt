package com.palmdev.learn_math.presentation.screens.games.game_duel

import androidx.lifecycle.ViewModel
import com.palmdev.learn_math.utils.FirebaseEvents

class GameDuelEndViewModel : ViewModel() {
    init {
        FirebaseEvents().setScreenViewEvent(screenName = "Duel End")
    }
}