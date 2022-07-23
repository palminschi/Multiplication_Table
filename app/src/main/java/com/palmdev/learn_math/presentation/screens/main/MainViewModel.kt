package com.palmdev.learn_math.presentation.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.learn_math.data.repository.ResultsRepository
import com.palmdev.learn_math.utils.Operation
import kotlinx.coroutines.launch

class MainViewModel(private val resultsRepository: ResultsRepository) : ViewModel() {

    val coins = MutableLiveData<Int>()
    val multiplicationCorrectAnswersPercent = MutableLiveData<Int>()
    val divisionCorrectAnswersPercent = MutableLiveData<Int>()
    val additionCorrectAnswersPercent = MutableLiveData<Int>()
    val subtractionCorrectAnswersPercent = MutableLiveData<Int>()

    init {
        coins.value = resultsRepository.getCoins()
    }

    fun getResults() {
        viewModelScope.launch {
            resultsRepository.getResultsByOperation(operation = Operation.SUBTRACTION)
                .collect { listOfResults ->
                    val listOfCorrectAnswersPercent = listOfResults.map { it.correctAnswersPercent }
                    subtractionCorrectAnswersPercent.value = listOfCorrectAnswersPercent.average().toInt()
                }
        }
        viewModelScope.launch {
            resultsRepository.getResultsByOperation(operation = Operation.MULTIPLICATION)
                .collect { listOfResults ->
                    val listOfCorrectAnswersPercent = listOfResults.map { it.correctAnswersPercent }
                    multiplicationCorrectAnswersPercent.value = listOfCorrectAnswersPercent.average().toInt()
                }
        }
        viewModelScope.launch {
            resultsRepository.getResultsByOperation(operation = Operation.DIVISION)
                .collect { listOfResults ->
                    val listOfCorrectAnswersPercent = listOfResults.map { it.correctAnswersPercent }
                    divisionCorrectAnswersPercent.value = listOfCorrectAnswersPercent.average().toInt()
                }
        }
        viewModelScope.launch {
            resultsRepository.getResultsByOperation(operation = Operation.ADDITION)
                .collect { listOfResults ->
                    val listOfCorrectAnswersPercent = listOfResults.map { it.correctAnswersPercent }
                    additionCorrectAnswersPercent.value = listOfCorrectAnswersPercent.average().toInt()
                }
        }
    }

}