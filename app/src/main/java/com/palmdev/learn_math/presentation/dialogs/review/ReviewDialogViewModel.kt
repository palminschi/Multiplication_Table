package com.palmdev.learn_math.presentation.dialogs.review

import androidx.lifecycle.ViewModel
import com.palmdev.learn_math.data.remote.repository.ReviewRepository

class ReviewDialogViewModel(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    fun rateApp() {
        reviewRepository.rateApp()
    }

    fun setUserRatedApp() {
        reviewRepository.setUserRatedApp(true)
    }
}