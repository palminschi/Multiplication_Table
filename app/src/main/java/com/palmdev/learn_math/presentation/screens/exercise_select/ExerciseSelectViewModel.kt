package com.palmdev.learn_math.presentation.screens.exercise_select

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.learn_math.data.model.ExerciseSelect
import com.palmdev.learn_math.data.repository.DivisionRepository
import com.palmdev.learn_math.data.repository.MultiplicationRepository
import kotlinx.coroutines.launch

class ExerciseSelectViewModel(
    private val multiplicationRepository: MultiplicationRepository,
    private val divisionRepository: DivisionRepository
    ) : ViewModel() {

    val exercise = MutableLiveData<ExerciseSelect>()

    fun getMultiplicationExercise(withNumber: Int, minNumber: Int = 0, maxNumber: Int = 10) {
        viewModelScope.launch {
            exercise.value = multiplicationRepository.getExerciseSelect(
                withNumber = withNumber,
                minNumber = minNumber,
                maxNumber = maxNumber
            )
        }
    }

    fun getDivisionExercise(withNumber: Int, minNumber: Int = 0, maxNumber: Int = 10) {
        viewModelScope.launch {
            exercise.value = divisionRepository.getExerciseSelect(
                withNumber = withNumber,
                minNumber = minNumber,
                maxNumber = maxNumber
            )
        }
    }
}