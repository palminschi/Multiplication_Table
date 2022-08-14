package com.palmdev.learn_math.presentation.screens.games.game_catch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palmdev.learn_math.data.model.ExerciseCatch
import com.palmdev.learn_math.domain.repository.AdsRepository
import com.palmdev.learn_math.domain.repository.games.GameCatchRepository
import com.palmdev.learn_math.utils.FirebaseEvents

class GameCatchViewModel(
    private val gameCatchRepository: GameCatchRepository,
    private val adsRepository: AdsRepository
) : ViewModel() {

    val exercise = MutableLiveData<ExerciseCatch>()
    val bestScore = MutableLiveData<Int?>()

    init {
        FirebaseEvents().setScreenViewEvent("Game Catch the Answer")
        bestScore.value = gameCatchRepository.getBestScore()
    }

    fun getExercise(minNumber: Int, maxNumber: Int) {
        exercise.value = gameCatchRepository.getExercise(minNumber, maxNumber)
    }

    fun saveResults(correctAnswers: Int) {
        gameCatchRepository.saveBestScore(correctAnswers)
    }

    fun showInterstitialAd() {
        adsRepository.showInterstitialAd()
    }
}