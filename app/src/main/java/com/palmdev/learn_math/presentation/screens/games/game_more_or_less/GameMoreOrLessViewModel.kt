package com.palmdev.learn_math.presentation.screens.games.game_more_or_less

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palmdev.learn_math.data.model.ExerciseMoreOrLess
import com.palmdev.learn_math.domain.repository.AdsRepository
import com.palmdev.learn_math.domain.repository.games.GameMoreOrLessRepository
import com.palmdev.learn_math.utils.FirebaseEvents

class GameMoreOrLessViewModel(
    private val gameMoreOrLessRepository: GameMoreOrLessRepository,
    private val adsRepository: AdsRepository
) : ViewModel() {

    val exercise = MutableLiveData<ExerciseMoreOrLess>()
    val bestScore = MutableLiveData<Int?>()

    init {
        FirebaseEvents().setScreenViewEvent("Game More or Less")
        bestScore.value = gameMoreOrLessRepository.getBestScore()
    }

    fun getExercise(minNumber: Int, maxNumber: Int) {
        exercise.value = gameMoreOrLessRepository.getExercise(minNumber, maxNumber)
    }

    fun saveResults(correctAnswers: Int) {
        gameMoreOrLessRepository.saveBestScore(correctAnswers)
    }

    fun showInterstitialAd() {
        adsRepository.showInterstitialAd()
    }
}