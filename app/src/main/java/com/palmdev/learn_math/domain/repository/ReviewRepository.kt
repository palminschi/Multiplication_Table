package com.palmdev.learn_math.domain.repository

interface ReviewRepository {
    fun rateApp()
    fun hasUserRatedApp(): Boolean
    fun setUserRatedApp(rated: Boolean)
}