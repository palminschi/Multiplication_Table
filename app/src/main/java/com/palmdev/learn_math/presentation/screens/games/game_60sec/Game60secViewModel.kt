package com.palmdev.learn_math.presentation.screens.games.game_60sec

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palmdev.learn_math.data.model.ExerciseSelect
import com.palmdev.learn_math.domain.repository.AdsRepository
import com.palmdev.learn_math.domain.repository.games.Game60secRepository
import com.palmdev.learn_math.utils.FirebaseEvents
import com.palmdev.learn_math.utils.Operation
import kotlin.random.Random

class Game60secViewModel(
    private val game60secRepository: Game60secRepository,
    private val adsRepository: AdsRepository
) : ViewModel() {

    init {
        FirebaseEvents().setScreenViewEvent("Game 60 sec")
    }
    val exercise = MutableLiveData<ExerciseSelect>()

    fun getExercise(minNumber: Int, maxNumber: Int, operation: Operation?) {
        if (operation == null) {
            val randomOperation = when (Random(System.currentTimeMillis()).nextInt(4)) {
                0 -> Operation.MULTIPLICATION
                1 -> Operation.DIVISION
                2 -> Operation.ADDITION
                3 -> Operation.SUBTRACTION
                else -> Operation.MULTIPLICATION
            }
            exercise.value =
                game60secRepository.getExercise(minNumber, maxNumber, randomOperation)
        } else {
            exercise.value =
                game60secRepository.getExercise(minNumber, maxNumber, operation)
        }
    }

    fun showInterstitialAd() {
        adsRepository.showInterstitialAd()
    }
}