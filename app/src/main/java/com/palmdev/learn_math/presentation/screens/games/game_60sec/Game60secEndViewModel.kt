package com.palmdev.learn_math.presentation.screens.games.game_60sec

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palmdev.learn_math.domain.repository.AdsRepository
import com.palmdev.learn_math.domain.repository.UserDataRepository
import com.palmdev.learn_math.domain.repository.games.Game60secRepository

class Game60secEndViewModel(
    private val adsRepository: AdsRepository,
    private val userDataRepository: UserDataRepository,
    private val game60secRepository: Game60secRepository
) : ViewModel() {

    val coins = MutableLiveData<Int>()
    val record = MutableLiveData<Int?>()

    init {
        coins.value = userDataRepository.coins
        record.value = game60secRepository.getRecord()
    }

    fun saveResult(correctAnswers: Int) {
        game60secRepository.saveRecord(correctAnswers = correctAnswers)
        record.value = game60secRepository.getRecord()
    }

    fun addCoins(amount: Int) {
        userDataRepository.addCoins(amount = amount)
        getCoins()
    }

    fun getCoins() {
        coins.value = userDataRepository.coins
    }

    fun showInterstitialAd() {
        adsRepository.showInterstitialAd()
    }
}