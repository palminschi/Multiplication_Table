package com.palmdev.learn_math.domain.repository

import com.google.android.gms.ads.OnUserEarnedRewardListener

interface AdsRepository {

    fun loadInterstitialAd()
    fun showInterstitialAd()
    fun loadRewardedAd()
    fun showRewardedAd(listener: OnUserEarnedRewardListener)

}