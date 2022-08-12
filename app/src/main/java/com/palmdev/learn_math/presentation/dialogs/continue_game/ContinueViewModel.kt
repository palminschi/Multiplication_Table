package com.palmdev.learn_math.presentation.dialogs.continue_game

import androidx.lifecycle.ViewModel
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.palmdev.learn_math.domain.repository.AdsRepository
import com.palmdev.learn_math.utils.FirebaseEvents

class ContinueViewModel(
    private val adsRepository: AdsRepository
) : ViewModel() {

    init {
        FirebaseEvents().setScreenViewEvent("Continue Dialog")
    }

    fun showRewardedAd(listener: OnUserEarnedRewardListener) {
        adsRepository.showContinueRewardedAd(listener)
    }
}