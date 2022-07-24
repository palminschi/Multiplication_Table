package com.palmdev.learn_math.presentation.screens.exercise_input

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.learn_math.data.model.ExerciseInput
import com.palmdev.learn_math.data.local.repository.AdditionRepository
import com.palmdev.learn_math.data.local.repository.DivisionRepository
import com.palmdev.learn_math.data.local.repository.MultiplicationRepository
import com.palmdev.learn_math.data.local.repository.SubtractionRepository
import kotlinx.coroutines.launch

class ExerciseInputViewModel(
    private val multiplicationRepository: MultiplicationRepository,
    private val divisionRepository: DivisionRepository,
    private val additionRepository: AdditionRepository,
    private val subtractionRepository: SubtractionRepository
) : ViewModel() {

    val exercise = MutableLiveData<ExerciseInput>()

    fun getMultiplicationExercise(withNumber: Int, minNumber: Int = 0, maxNumber: Int = 10) {
        viewModelScope.launch {
            exercise.value = multiplicationRepository.getExerciseInput(
                withNumber = withNumber,
                minNumber = minNumber,
                maxNumber = maxNumber
            )
        }
    }

    fun getDivisionExercise(withNumber: Int, minNumber: Int = 0, maxNumber: Int = 10) {
        viewModelScope.launch {
            exercise.value = divisionRepository.getExerciseInput(
                withNumber = withNumber,
                minNumber = minNumber,
                maxNumber = maxNumber
            )
        }
    }

    fun getAdditionExercise(withNumber: Int, minNumber: Int = 0, maxNumber: Int = 10) {
        viewModelScope.launch {
            exercise.value = additionRepository.getExerciseInput(
                withNumber = withNumber,
                minNumber = minNumber,
                maxNumber = maxNumber
            )
        }
    }

    fun getSubtractionExercise(withNumber: Int, minNumber: Int = 0, maxNumber: Int = 10) {
        viewModelScope.launch {
            exercise.value = subtractionRepository.getExerciseInput(
                withNumber = withNumber,
                minNumber = minNumber,
                maxNumber = maxNumber
            )
        }
    }

}