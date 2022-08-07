package com.palmdev.learn_math.data.remote.repository

import android.app.Application
import android.content.Context
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task
import com.palmdev.learn_math.domain.repository.ReviewRepository
import com.palmdev.learn_math.utils.MAIN
import com.palmdev.learn_math.utils.REVIEW_KEY
import com.palmdev.learn_math.utils.SHARED_PREFS

class ReviewRepositoryImpl(private val application: Application) : ReviewRepository {

    private val sharedPrefs = application.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    override fun rateApp() {
        val manager: ReviewManager = ReviewManagerFactory.create(application)
        val request: Task<ReviewInfo> = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                manager.launchReviewFlow(MAIN, reviewInfo)
            }
        }
    }

    override fun hasUserRatedApp(): Boolean {
        return sharedPrefs.getBoolean(REVIEW_KEY, false)
    }

    override fun setUserRatedApp(rated: Boolean) {
        sharedPrefs.edit().putBoolean(REVIEW_KEY, rated).apply()
    }
}