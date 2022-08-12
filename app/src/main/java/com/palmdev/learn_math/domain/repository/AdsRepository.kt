package com.palmdev.learn_math.domain.repository

import com.google.android.gms.ads.OnUserEarnedRewardListener

interface AdsRepository {

    fun loadInterstitialAd()
    fun showInterstitialAd()
    fun load500coinsRewardedAd()
    fun show500coinsRewardedAd(listener: OnUserEarnedRewardListener)
    fun loadContinueRewardedAd()
    fun showContinueRewardedAd(listener: OnUserEarnedRewardListener)
}