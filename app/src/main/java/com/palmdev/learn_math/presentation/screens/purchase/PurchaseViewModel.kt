package com.palmdev.learn_math.presentation.screens.purchase

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palmdev.learn_math.R
import com.palmdev.learn_math.domain.repository.UserDataRepository
import com.palmdev.learn_math.domain.repository.AdsRepository
import com.palmdev.learn_math.domain.repository.PurchaseRepository
import com.palmdev.learn_math.utils.FirebaseEvents
import com.palmdev.learn_math.utils.MAIN
import com.palmdev.learn_math.utils.REWARD_TYPE

const val COINS_PRICE = 50000

class PurchaseViewModel(
    private val userDataRepository: UserDataRepository,
    private val purchaseRepository: PurchaseRepository,
    private val adsRepository: AdsRepository
) : ViewModel() {

    val coins = MutableLiveData<Int>()
    val isPremiumUser = MutableLiveData<Boolean>()
    val price = MutableLiveData<String>()

    init {
        isPremiumUser.value = userDataRepository.isPremiumUser
        FirebaseEvents().setScreenViewEvent(screenName = "Purchase")
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
            type = REWARD_TYPE.COINS,
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

    fun getPrice() {
        price.value = purchaseRepository.getPrice()
    }
}