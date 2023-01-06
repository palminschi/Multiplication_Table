package com.palmdev.learn_math.data.remote.repository

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.palmdev.learn_math.R
import com.palmdev.learn_math.domain.repository.UserDataRepository
import com.palmdev.learn_math.domain.repository.AdsRepository
import com.palmdev.learn_math.utils.MAIN
import com.palmdev.learn_math.utils.REWARD_TYPE

private const val TAG = "ADS"

class AdsRepositoryImpl(
    private val context: Context,
    private val userDataRepository: UserDataRepository
) : AdsRepository {

    private var mInterstitialAd: InterstitialAd? = null
    private var m500CoinsRewardedAd: RewardedAd? = null
    private var mContinueRewardedAd: RewardedAd? = null
    private var m2048RewardedAd: RewardedAd? = null
    private var mInterstitialAd2048: InterstitialAd? = null

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

    override fun loadRewardedAd(type: REWARD_TYPE) {
        val adRequest = AdRequest.Builder().build()
        when (type) {
            REWARD_TYPE.COINS -> {
                RewardedAd.load(
                    context,
                    context.getString(R.string.AD_REWARDED_ID_500_COINS),
                    adRequest,
                    object : RewardedAdLoadCallback() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            Log.d(TAG, adError.message)
                            m500CoinsRewardedAd = null
                        }

                        override fun onAdLoaded(rewardedAd: RewardedAd) {
                            Log.d(TAG, "Ad was loaded.")
                            m500CoinsRewardedAd = rewardedAd
                        }
                    }
                )
            }
            REWARD_TYPE.CONTINUE -> {
                RewardedAd.load(
                    context,
                    context.getString(R.string.AD_REWARDED_ID_CONTINUE),
                    adRequest,
                    object : RewardedAdLoadCallback() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            Log.d(TAG, adError.message)
                            mContinueRewardedAd = null
                        }

                        override fun onAdLoaded(rewardedAd: RewardedAd) {
                            Log.d(TAG, "Ad was loaded.")
                            mContinueRewardedAd = rewardedAd
                        }
                    }
                )
            }
            REWARD_TYPE.GAME2048 -> {
                RewardedAd.load(context, context.getString(R.string.AD_REWARDED_ID_2048), adRequest,
                    object : RewardedAdLoadCallback() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            Log.d(TAG, adError.message)
                            m2048RewardedAd = null
                        }

                        override fun onAdLoaded(rewardedAd: RewardedAd) {
                            Log.d(TAG, "Ad was loaded.")
                            m2048RewardedAd = rewardedAd
                        }
                    }
                )
            }
        }


    }

    override fun showRewardedAd(type: REWARD_TYPE, listener: OnUserEarnedRewardListener) {
        when (type) {
            REWARD_TYPE.COINS -> {
                mContinueRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.d(TAG, "Ad was shown.")
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        // Called when ad fails to show.
                        Log.d(TAG, "Ad failed to show.")
                        m500CoinsRewardedAd = null
                    }

                    override fun onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Set the ad reference to null so you don't show the ad a second time.
                        Log.d(TAG, "Ad was dismissed.")
                        m500CoinsRewardedAd = null
                        loadRewardedAd(REWARD_TYPE.COINS)
                    }
                }

                m500CoinsRewardedAd?.show(MAIN, listener)
            }

            REWARD_TYPE.CONTINUE -> {
                mContinueRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.d(TAG, "Ad was shown.")
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        // Called when ad fails to show.
                        Log.d(TAG, "Ad failed to show.")
                        mContinueRewardedAd = null
                    }

                    override fun onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Set the ad reference to null so you don't show the ad a second time.
                        Log.d(TAG, "Ad was dismissed.")
                        mContinueRewardedAd = null
                        loadRewardedAd(REWARD_TYPE.CONTINUE)
                    }
                }

                mContinueRewardedAd?.show(MAIN, listener)
            }

            REWARD_TYPE.GAME2048 -> {
                mContinueRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.d(TAG, "Ad was shown.")
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        // Called when ad fails to show.
                        Log.d(TAG, "Ad failed to show.")
                        m2048RewardedAd = null
                    }

                    override fun onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Set the ad reference to null so you don't show the ad a second time.
                        Log.d(TAG, "Ad was dismissed.")
                        m2048RewardedAd = null
                        loadRewardedAd(REWARD_TYPE.GAME2048)
                    }
                }

                m2048RewardedAd?.show(MAIN, listener)
            }
        }
    }

    override fun loadInterstitialAd2048() {
        if (userDataRepository.isPremiumUser) return
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context,
            context.getString(R.string.AD_INTERSTITIAL_2048),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    Log.d(TAG, adError.message)
                    mInterstitialAd2048 = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mInterstitialAd2048 = interstitialAd
                }
            }
        )
    }

    override fun showInterstitialAd2048() {
        if (userDataRepository.isPremiumUser) return
        if (mInterstitialAd2048 != null) {
            mInterstitialAd2048?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d(TAG, "Ad was dismissed.")
                    mInterstitialAd2048 = null
                    loadInterstitialAd2048()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.d(TAG, "Ad failed to show.")
                    mInterstitialAd2048 = null
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Ad showed fullscreen content.")
                    // Called when ad is dismissed.
                }
            }
            mInterstitialAd2048?.show(MAIN)
        }
    }
}