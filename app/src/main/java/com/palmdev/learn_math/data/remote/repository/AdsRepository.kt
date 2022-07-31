package com.palmdev.learn_math.data.remote.repository

import com.google.android.gms.ads.OnUserEarnedRewardListener

interface AdsRepository {

    fun loadInterstitialAd()
    fun showInterstitialAd()
    fun loadRewardedAd()
    fun showRewardedAd(listener: OnUserEarnedRewardListener)

}