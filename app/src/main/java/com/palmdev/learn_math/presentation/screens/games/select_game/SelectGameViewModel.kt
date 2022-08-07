package com.palmdev.learn_math.presentation.screens.games.select_game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palmdev.learn_math.domain.repository.games.GameRecordsRepository
import com.palmdev.learn_math.utils.FirebaseEvents

class SelectGameViewModel(
    private val gameRecordsRepository: GameRecordsRepository
) : ViewModel() {

    init {
        FirebaseEvents().setScreenViewEvent(screenName = "Select Game")
    }

    val recordGame60sec = MutableLiveData<Int?>()

    fun getRecords() {
        val game60sec = gameRecordsRepository.game60sec()
        if (game60sec != null) recordGame60sec.value = game60sec
        else recordGame60sec.value = null
    }
}