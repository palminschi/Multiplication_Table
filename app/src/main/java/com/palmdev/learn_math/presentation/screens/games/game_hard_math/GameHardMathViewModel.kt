package com.palmdev.learn_math.presentation.screens.games.game_hard_math

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palmdev.learn_math.data.model.ExerciseHardMath
import com.palmdev.learn_math.data.model.ExerciseSelect
import com.palmdev.learn_math.domain.repository.AdsRepository
import com.palmdev.learn_math.domain.repository.games.GameHardMathRepository
import com.palmdev.learn_math.utils.FirebaseEvents

class GameHardMathViewModel(
    private val gameGameHardMathRepository: GameHardMathRepository,
    private val adsRepository: AdsRepository
) : ViewModel() {

    val exercise = MutableLiveData<ExerciseHardMath>()
    val bestScore = MutableLiveData<Int?>()

    init {
        FirebaseEvents().setScreenViewEvent("Game Hard Math")
        bestScore.value = gameGameHardMathRepository.getBestScore()
    }

    fun getExercise(minNumber: Int, maxNumber: Int) {
        exercise.value = gameGameHardMathRepository.getExercise(minNumber, maxNumber)
    }

    fun saveResults(correctAnswers: Int) {
        gameGameHardMathRepository.saveBestScore(correctAnswers)
    }

    fun showInterstitialAd() {
        adsRepository.showInterstitialAd()
    }
}