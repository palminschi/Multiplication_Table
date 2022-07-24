package com.palmdev.learn_math.data.local.repository

interface UserDataRepository {

    val coins: Int
    fun addCoins(amount: Int)
    fun removeCoins(amount: Int)
    val isPremiumUser: Boolean
    fun setIsPremiumUser(boolean: Boolean)

}