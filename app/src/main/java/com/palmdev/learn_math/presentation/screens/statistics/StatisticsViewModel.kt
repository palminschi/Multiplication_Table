package com.palmdev.learn_math.presentation.screens.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.learn_math.data.model.ExamResults
import com.palmdev.learn_math.data.repository.ResultsRepository
import com.palmdev.learn_math.utils.Operation
import kotlinx.coroutines.launch

class StatisticsViewModel(
    private val resultsRepository: ResultsRepository
) : ViewModel() {
    val multiplicationCorrectAnswersPercent = MutableLiveData<Int>()
    val divisionCorrectAnswersPercent = MutableLiveData<Int>()
    val additionCorrectAnswersPercent = MutableLiveData<Int>()
    val subtractionCorrectAnswersPercent = MutableLiveData<Int>()

    val multiplicationTotalExercises = MutableLiveData<Int>()
    val divisionTotalExercises = MutableLiveData<Int>()
    val additionTotalExercises = MutableLiveData<Int>()
    val subtractionTotalExercises = MutableLiveData<Int>()

    val multiplicationAvgTime = MutableLiveData<Double>()
    val divisionAvgTime = MutableLiveData<Double>()
    val additionAvgTime = MutableLiveData<Double>()
    val subtractionAvgTime = MutableLiveData<Double>()

    val examResults = MutableLiveData<ExamResults>()


    fun getResults() {
        examResults.value = resultsRepository.getExamResults()

        viewModelScope.launch {
            resultsRepository.getResultsByOperation(operation = Operation.MULTIPLICATION)
                .collect { listOfResults ->
                    multiplicationTotalExercises.value = listOfResults.size
                    val listOfCorrectAnswersPercent = listOfResults.map { it.correctAnswersPercent }
                    multiplicationCorrectAnswersPercent.value =
                        listOfCorrectAnswersPercent.average().toInt()
                    val listOfAvgTime = listOfResults.map { it.avgAnswerTime }
                    multiplicationAvgTime.value = listOfAvgTime.average()
                }
        }
        viewModelScope.launch {
            resultsRepository.getResultsByOperation(operation = Operation.DIVISION)
                .collect { listOfResults ->
                    divisionTotalExercises.value = listOfResults.size
                    val listOfCorrectAnswersPercent = listOfResults.map { it.correctAnswersPercent }
                    divisionCorrectAnswersPercent.value =
                        listOfCorrectAnswersPercent.average().toInt()
                    val listOfAvgTime = listOfResults.map { it.avgAnswerTime }
                    divisionAvgTime.value = listOfAvgTime.average()
                }
        }
        viewModelScope.launch {
            resultsRepository.getResultsByOperation(operation = Operation.ADDITION)
                .collect { listOfResults ->
                    additionTotalExercises.value = listOfResults.size
                    val listOfCorrectAnswersPercent = listOfResults.map { it.correctAnswersPercent }
                    additionCorrectAnswersPercent.value =
                        listOfCorrectAnswersPercent.average().toInt()
                    val listOfAvgTime = listOfResults.map { it.avgAnswerTime }
                    additionAvgTime.value = listOfAvgTime.average()
                }
        }
        viewModelScope.launch {
            resultsRepository.getResultsByOperation(operation = Operation.SUBTRACTION)
                .collect { listOfResults ->
                    subtractionTotalExercises.value = listOfResults.size
                    val listOfCorrectAnswersPercent = listOfResults.map { it.correctAnswersPercent }
                    subtractionCorrectAnswersPercent.value =
                        listOfCorrectAnswersPercent.average().toInt()
                    val listOfAvgTime = listOfResults.map { it.avgAnswerTime }
                    subtractionAvgTime.value = listOfAvgTime.average()
                }
        }
    }
}