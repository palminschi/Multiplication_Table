package com.palmdev.learn_math.presentation.screens.games.game_2048

import androidx.lifecycle.ViewModel
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.palmdev.learn_math.domain.repository.AdsRepository
import com.palmdev.learn_math.utils.FirebaseEvents
import com.palmdev.learn_math.utils.REWARD_TYPE

class Game2048OverViewModel(
    private val adsRepository: AdsRepository
) : ViewModel() {
    init {
        FirebaseEvents().setScreenViewEvent("Dialog 2048 GameOver")
    }


    fun showRewardedAd() {
        adsRepository.showInterstitialAd2048()
    }
}