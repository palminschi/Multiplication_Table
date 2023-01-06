package com.palmdev.learn_math.presentation.screens.games.game_2048

import androidx.lifecycle.ViewModel
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.palmdev.learn_math.data.games.game2048.Game2048RepositoryImpl
import com.palmdev.learn_math.domain.repository.AdsRepository
import com.palmdev.learn_math.utils.FirebaseEvents
import com.palmdev.learn_math.utils.REWARD_TYPE


class Game2048ViewModel(private val adsRepository: AdsRepository) : ViewModel() {
    fun init() {
        FirebaseEvents().setScreenViewEvent("Game 2048")
        adsRepository.loadInterstitialAd2048()
    }
    fun showAd() {
        adsRepository.showInterstitialAd2048()
    }
}