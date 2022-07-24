package com.palmdev.learn_math.presentation.screens.end

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.learn_math.data.model.ResultExercise
import com.palmdev.learn_math.data.local.repository.ResultsRepository
import com.palmdev.learn_math.data.local.repository.UserDataRepository
import com.palmdev.learn_math.data.remote.repository.AdsRepository
import com.palmdev.learn_math.data.remote.repository.ReviewRepository
import com.palmdev.learn_math.utils.Operation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EndViewModel(
    private val resultsRepository: ResultsRepository,
    reviewRepository: ReviewRepository,
    private val adsRepository: AdsRepository,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    val coins = MutableLiveData<Int>()
    val userRatedApp = MutableLiveData<Boolean>()

    init {
        userRatedApp.value = reviewRepository.hasUserRatedApp()
    }

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

    fun saveExamResult(correctAnswers: Int) {
        if (correctAnswers < 5) resultsRepository.saveExamResult(false)
        else resultsRepository.saveExamResult(true)
    }

    fun addCoins(amount: Int) {
        userDataRepository.addCoins(amount = amount)
    }

    fun getCoins() {
        coins.value = userDataRepository.coins
    }

    fun showInterstitialAd() {
        adsRepository.showInterstitialAd()
    }

}