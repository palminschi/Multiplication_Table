package com.palmdev.learn_math.presentation.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.learn_math.domain.repository.ResultsRepository
import com.palmdev.learn_math.domain.repository.UserDataRepository
import com.palmdev.learn_math.domain.repository.AdsRepository
import com.palmdev.learn_math.utils.FirebaseEvents
import com.palmdev.learn_math.utils.Operation
import kotlinx.coroutines.launch

class MainViewModel(
    private val resultsRepository: ResultsRepository,
    private val adsRepository: AdsRepository,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    val coins = MutableLiveData<Int>()
    val isPremiumUser = MutableLiveData<Boolean>()
    val multiplicationCorrectAnswersPercent = MutableLiveData<Int>()
    val divisionCorrectAnswersPercent = MutableLiveData<Int>()
    val additionCorrectAnswersPercent = MutableLiveData<Int>()
    val subtractionCorrectAnswersPercent = MutableLiveData<Int>()

    fun loadAds(){
        adsRepository.loadInterstitialAd()
        adsRepository.load500coinsRewardedAd()
    }

    init {
        FirebaseEvents().setScreenViewEvent(screenName = "Main")
    }

    fun initData() {
        isPremiumUser.value = userDataRepository.isPremiumUser
        coins.value = userDataRepository.coins
        viewModelScope.launch {
            resultsRepository.getResultsByOperation(operation = Operation.SUBTRACTION)
                .collect { listOfResults ->
                    val listOfCorrectAnswersPercent = listOfResults.map { it.correctAnswersPercent }
                    subtractionCorrectAnswersPercent.value =
                        listOfCorrectAnswersPercent.average().toInt()
                }
        }
        viewModelScope.launch {
            resultsRepository.getResultsByOperation(operation = Operation.MULTIPLICATION)
                .collect { listOfResults ->
                    val listOfCorrectAnswersPercent = listOfResults.map { it.correctAnswersPercent }
                    multiplicationCorrectAnswersPercent.value =
                        listOfCorrectAnswersPercent.average().toInt()
                }
        }
        viewModelScope.launch {
            resultsRepository.getResultsByOperation(operation = Operation.DIVISION)
                .collect { listOfResults ->
                    val listOfCorrectAnswersPercent = listOfResults.map { it.correctAnswersPercent }
                    divisionCorrectAnswersPercent.value =
                        listOfCorrectAnswersPercent.average().toInt()
                }
        }
        viewModelScope.launch {
            resultsRepository.getResultsByOperation(operation = Operation.ADDITION)
                .collect { listOfResults ->
                    val listOfCorrectAnswersPercent = listOfResults.map { it.correctAnswersPercent }
                    additionCorrectAnswersPercent.value =
                        listOfCorrectAnswersPercent.average().toInt()
                }
        }
    }

}