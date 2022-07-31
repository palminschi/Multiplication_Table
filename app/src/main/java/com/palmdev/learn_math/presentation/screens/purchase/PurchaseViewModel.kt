package com.palmdev.learn_math.presentation.screens.purchase

import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.palmdev.learn_math.R
import com.palmdev.learn_math.data.local.repository.UserDataRepository
import com.palmdev.learn_math.data.remote.repository.AdsRepository
import com.palmdev.learn_math.data.remote.repository.PurchaseRepository
import com.palmdev.learn_math.utils.MAIN

const val COINS_PRICE = 50000

class PurchaseViewModel(
    private val userDataRepository: UserDataRepository,
    private val purchaseRepository: PurchaseRepository,
    private val adsRepository: AdsRepository
) : ViewModel() {

    val coins = MutableLiveData<Int>()
    val isPremiumUser = MutableLiveData<Boolean>()

    init {
        isPremiumUser.value = userDataRepository.isPremiumUser
        adsRepository.loadRewardedAd()
    }

    fun initCoins() {
        coins.value = userDataRepository.coins
    }

    fun buyPremium() {
        purchaseRepository.buyPremiumAccount()
        isPremiumUser.value = userDataRepository.isPremiumUser
    }

    fun buyPremiumByCoins() {
        val successfully = purchaseRepository.buyPremiumAccountByCoins(price = COINS_PRICE)
        if (!successfully) Toast.makeText(
            MAIN,
            MAIN.getText(R.string.toastNoCoins),
            Toast.LENGTH_SHORT
        ).show()
        isPremiumUser.value = userDataRepository.isPremiumUser
    }

    fun showRewardedAd() {
        adsRepository.showRewardedAd(
            listener = { rewardItem ->
                userDataRepository.addCoins(rewardItem.amount)
                coins.value = userDataRepository.coins
                Toast.makeText(
                    MAIN.applicationContext,
                    "${MAIN.getText(R.string.coinsReceived)} ${rewardItem.amount}",
                    Toast.LENGTH_LONG
                ).show()
            }
        )
    }
}