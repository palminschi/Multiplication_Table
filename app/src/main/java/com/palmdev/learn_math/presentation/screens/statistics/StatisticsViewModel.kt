package com.palmdev.learn_math.presentation.screens.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.learn_math.data.model.ExamResults
import com.palmdev.learn_math.domain.repository.ResultsRepository
import com.palmdev.learn_math.domain.repository.AdsRepository
import com.palmdev.learn_math.utils.FirebaseEvents
import com.palmdev.learn_math.utils.Operation
import kotlinx.coroutines.launch

class StatisticsViewModel(
    private val resultsRepository: ResultsRepository,
    private val adsRepository: AdsRepository
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

    init {
        FirebaseEvents().setScreenViewEvent(screenName = "Statistics")
    }

    fun showInterstitialAd(){
        adsRepository.showInterstitialAd()
    }

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
                    multiplicationAvgTime.value =
                        if (listOfAvgTime.isEmpty()) 0.0
                        else listOfAvgTime.average()
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
                    divisionAvgTime.value =
                        if (listOfAvgTime.isEmpty()) 0.0
                        else listOfAvgTime.average()
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
                    additionAvgTime.value =
                        if (listOfAvgTime.isEmpty()) 0.0
                        else listOfAvgTime.average()
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
                    subtractionAvgTime.value =
                        if (listOfAvgTime.isEmpty()) 0.0
                        else listOfAvgTime.average()
                }
        }
    }
}