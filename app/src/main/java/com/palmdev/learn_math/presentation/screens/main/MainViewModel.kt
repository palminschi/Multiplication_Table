package com.palmdev.learn_math.presentation.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.learn_math.data.model.ResultExercise
import com.palmdev.learn_math.data.repository.ResultsRepository
import com.palmdev.learn_math.utils.Operation
import kotlinx.coroutines.launch

class MainViewModel(private val resultsRepository: ResultsRepository) : ViewModel() {

    val coins = MutableLiveData<Int>()
    val multiplicationCorrectAnswers = MutableLiveData<Int>()
    val divisionCorrectAnswers = MutableLiveData<Int>()
    val additionCorrectAnswers = MutableLiveData<Int>()
    val subtractionCorrectAnswers = MutableLiveData<Int>()

    init {
        coins.value = resultsRepository.getCoins()
    }

    fun getResults() {
        viewModelScope.launch {
            resultsRepository.getResultsByOperation(operation = Operation.SUBTRACTION)
                .collect { listOfResults ->
                    val listOfCorrectAnswersPercent = listOfResults.map { it.correctAnswersPercent }
                    subtractionCorrectAnswers.value = listOfCorrectAnswersPercent.average().toInt()
                }
        }
        viewModelScope.launch {
            resultsRepository.getResultsByOperation(operation = Operation.MULTIPLICATION)
                .collect { listOfResults ->
                    val listOfCorrectAnswersPercent = listOfResults.map { it.correctAnswersPercent }
                    multiplicationCorrectAnswers.value = listOfCorrectAnswersPercent.average().toInt()
                }
        }
        viewModelScope.launch {
            resultsRepository.getResultsByOperation(operation = Operation.DIVISION)
                .collect { listOfResults ->
                    val listOfCorrectAnswersPercent = listOfResults.map { it.correctAnswersPercent }
                    divisionCorrectAnswers.value = listOfCorrectAnswersPercent.average().toInt()
                }
        }
        viewModelScope.launch {
            resultsRepository.getResultsByOperation(operation = Operation.ADDITION)
                .collect { listOfResults ->
                    val listOfCorrectAnswersPercent = listOfResults.map { it.correctAnswersPercent }
                    additionCorrectAnswers.value = listOfCorrectAnswersPercent.average().toInt()
                }
        }
    }

}