package com.palmdev.learn_math.data.remote.repository

interface PurchaseRepository {

    fun buyPremiumAccount()

    fun buyPremiumAccountByCoins(price: Int): Boolean

}