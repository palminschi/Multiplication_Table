package com.palmdev.learn_math.data.remote.repository

interface ReviewRepository {
    fun rateApp()
    fun hasUserRatedApp(): Boolean
    fun setUserRatedApp(rated: Boolean)
}