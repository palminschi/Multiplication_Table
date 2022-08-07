package com.palmdev.learn_math.presentation.screens.games.game_duel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.learn_math.domain.repository.AdditionRepository
import com.palmdev.learn_math.domain.repository.DivisionRepository
import com.palmdev.learn_math.domain.repository.MultiplicationRepository
import com.palmdev.learn_math.domain.repository.SubtractionRepository
import com.palmdev.learn_math.data.model.ExerciseSelect
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameDuelViewModel(
    private val multiplicationRepository: MultiplicationRepository,
    private val divisionRepository: DivisionRepository,
    private val additionRepository: AdditionRepository,
    private val subtractionRepository: SubtractionRepository
) : ViewModel() {

    val exercises = MutableLiveData<List<ExerciseSelect>>()

    fun getMultiplicationExercise(minNumber: Int = 0, maxNumber: Int = 10) {
        viewModelScope.launch {
            val list = ArrayList<ExerciseSelect>()
            for (i in 0 until 10) {
                val random = Random(System.currentTimeMillis() + i)
                val withNumber = random.nextInt(minNumber, maxNumber)
                list.add(
                    multiplicationRepository.getExerciseSelect(
                        withNumber = withNumber,
                        minNumber = minNumber,
                        maxNumber = maxNumber,
                        randomNumber = random.nextInt()
                    )
                )
            }
            exercises.postValue(list)
        }
    }

    fun getDivisionExercise(minNumber: Int = 0, maxNumber: Int = 10) {
        viewModelScope.launch {
            val list = ArrayList<ExerciseSelect>()
            for (i in 0 until 10) {
                val random = Random(System.currentTimeMillis() + i)
                val withNumber = random.nextInt(minNumber, maxNumber)
                list.add(
                    divisionRepository.getExerciseSelect(
                        withNumber = withNumber,
                        minNumber = minNumber,
                        maxNumber = maxNumber,
                        randomNumber = random.nextInt()
                    )
                )
            }
            exercises.postValue(list)
        }
    }

    fun getAdditionExercise(minNumber: Int = 0, maxNumber: Int = 10) {
        viewModelScope.launch {
            val list = ArrayList<ExerciseSelect>()
            for (i in 0 until 10) {
                val random = Random(System.currentTimeMillis() + i)
                val withNumber = random.nextInt(minNumber, maxNumber)
                list.add(
                    additionRepository.getExerciseSelect(
                        withNumber = withNumber,
                        minNumber = minNumber,
                        maxNumber = maxNumber,
                        randomNumber = random.nextInt()
                    )
                )
            }
            exercises.postValue(list)
        }
    }

    fun getSubtractionExercise(minNumber: Int = 0, maxNumber: Int = 10) {
        viewModelScope.launch {
            val list = ArrayList<ExerciseSelect>()
            for (i in 0 until 10) {
                val random = Random(System.currentTimeMillis() + i)
                val withNumber = random.nextInt(minNumber, maxNumber)
                list.add(
                    subtractionRepository.getExerciseSelect(
                        withNumber = withNumber,
                        minNumber = minNumber,
                        maxNumber = maxNumber,
                        randomNumber = random.nextInt()
                    )
                )
            }
            exercises.postValue(list)
        }
    }

}