package com.palmdev.learn_math.presentation.screens.exercise_true_or_false

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.learn_math.data.model.ExerciseTrueOrFalse
import com.palmdev.learn_math.data.repository.DivisionRepository
import com.palmdev.learn_math.data.repository.MultiplicationRepository
import kotlinx.coroutines.launch

class ExerciseTrueOrFalseViewModel(
    private val multiplicationRepository: MultiplicationRepository,
    private val divisionRepository: DivisionRepository
) : ViewModel() {

    val exercise = MutableLiveData<ExerciseTrueOrFalse>()

    fun getMultiplicationExercise(withNumber: Int, minNumber: Int = 0, maxNumber: Int = 10) {
        viewModelScope.launch {
            exercise.value = multiplicationRepository.getExerciseTrueOrFalse(
                withNumber = withNumber,
                minNumber = minNumber,
                maxNumber = maxNumber
            )
        }
    }

    fun getDivisionExercise(withNumber: Int, minNumber: Int = 0, maxNumber: Int = 10) {
        viewModelScope.launch {
            exercise.value = divisionRepository.getExerciseTrueOrFalse(
                withNumber = withNumber,
                minNumber = minNumber,
                maxNumber = maxNumber
            )
        }
    }

}