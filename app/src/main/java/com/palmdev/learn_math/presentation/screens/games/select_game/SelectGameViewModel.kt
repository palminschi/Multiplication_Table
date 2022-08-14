package com.palmdev.learn_math.presentation.screens.games.select_game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palmdev.learn_math.domain.repository.AdsRepository
import com.palmdev.learn_math.domain.repository.games.GameScoresRepository
import com.palmdev.learn_math.utils.FirebaseEvents

class SelectGameViewModel(
    private val gameScoresRepository: GameScoresRepository,
    adsRepository: AdsRepository
) : ViewModel() {

    init {
        FirebaseEvents().setScreenViewEvent(screenName = "Select Game")
        adsRepository.loadContinueRewardedAd()
    }

    val bestScoreGame60sec = MutableLiveData<Int?>()
    val bestScoreGameMoreOrLess = MutableLiveData<Int?>()
    val bestScoreGameHardMath = MutableLiveData<Int?>()
    val bestScoreGameCatch = MutableLiveData<Int?>()

    fun getRecords() {
        bestScoreGame60sec.value = gameScoresRepository.game60sec()
        bestScoreGameMoreOrLess.value = gameScoresRepository.gameMoreOrLess()
        bestScoreGameHardMath.value = gameScoresRepository.gameHardMath()
        bestScoreGameCatch.value = gameScoresRepository.gameCatch()
    }
}