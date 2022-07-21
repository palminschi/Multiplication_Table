package com.palmdev.learn_math.presentation.screens.end

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.learn_math.data.model.ResultExercise
import com.palmdev.learn_math.data.repository.ResultsRepository
import com.palmdev.learn_math.utils.Operation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EndViewModel(
    private val resultsRepository: ResultsRepository
) : ViewModel() {

    val coins = MutableLiveData<Int>()

    fun saveResults(
        operation: Operation,
        correctAnswers: Int,
        wrongAnswers: Int,
        avgAnswerTime: Double
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            resultsRepository.saveResult(
                ResultExercise(
                    operation = operation,
                    correctAnswersPercent = (correctAnswers * 100) / (correctAnswers + wrongAnswers),
                    avgAnswerTime = avgAnswerTime
                )
            )
        }
    }

    fun addCoins(amount: Int) {
        resultsRepository.addCoins(amount = amount)
    }

    fun getCoins() {
        coins.value = resultsRepository.getCoins()
    }

}