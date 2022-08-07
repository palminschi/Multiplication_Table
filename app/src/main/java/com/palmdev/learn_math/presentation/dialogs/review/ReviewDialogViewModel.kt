package com.palmdev.learn_math.presentation.dialogs.review

import androidx.lifecycle.ViewModel
import com.palmdev.learn_math.domain.repository.ReviewRepository
import com.palmdev.learn_math.utils.FirebaseEvents

class ReviewDialogViewModel(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    init {
        FirebaseEvents().setScreenViewEvent(screenName = "Dialog Review")
    }

    fun rateApp() {
        reviewRepository.rateApp()
    }

    fun setUserRatedApp() {
        reviewRepository.setUserRatedApp(true)
    }
}