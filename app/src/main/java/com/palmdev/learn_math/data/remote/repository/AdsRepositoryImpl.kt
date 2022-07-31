package com.palmdev.learn_math.data.remote.repository

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.palmdev.learn_math.R
import com.palmdev.learn_math.data.local.repository.UserDataRepository
import com.palmdev.learn_math.utils.MAIN

private const val TAG = "ADS"

class AdsRepositoryImpl(
    private val context: Context,
    private val userDataRepository: UserDataRepository
) : AdsRepository {

    private var mInterstitialAd: InterstitialAd? = null
    private var mRewardedAd: RewardedAd? = null

    override fun loadInterstitialAd() {
        if (userDataRepository.isPremiumUser) return
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context,
            context.getString(R.string.AD_INTERSTITIAL_ID),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    Log.d(TAG, adError.message)
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            }
        )
    }

    override fun showInterstitialAd() {
        if (userDataRepository.isPremiumUser) return
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d(TAG, "Ad was dismissed.")
                    mInterstitialAd = null
                    loadInterstitialAd()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.d(TAG, "Ad failed to show.")
                    mInterstitialAd = null
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Ad showed fullscreen content.")
                    // Called when ad is dismissed.
                }
            }
            mInterstitialAd?.show(MAIN)
        }
    }

    override fun loadRewardedAd() {

        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(context, context.getString(R.string.AD_REWARDED_ID), adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.message)
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mRewardedAd = rewardedAd
                }
            }
        )
    }

    override fun showRewardedAd(listener: OnUserEarnedRewardListener) {
        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad was shown.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                // Called when ad fails to show.
                Log.d(TAG, "Ad failed to show.")
                mRewardedAd = null
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d(TAG, "Ad was dismissed.")
                mRewardedAd = null
                loadRewardedAd()
            }
        }

        mRewardedAd?.show(MAIN, listener)
    }
}