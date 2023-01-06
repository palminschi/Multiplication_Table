package com.palmdev.learn_math.domain.repository

import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.palmdev.learn_math.utils.REWARD_TYPE

interface AdsRepository {

    fun loadInterstitialAd()
    fun showInterstitialAd()
    fun loadRewardedAd(type: REWARD_TYPE)
    fun showRewardedAd(type: REWARD_TYPE, listener: OnUserEarnedRewardListener)
    fun loadInterstitialAd2048()
    fun showInterstitialAd2048()
}