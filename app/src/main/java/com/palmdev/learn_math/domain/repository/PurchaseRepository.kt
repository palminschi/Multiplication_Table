package com.palmdev.learn_math.domain.repository

interface PurchaseRepository {

    fun buyPremiumAccount()

    fun buyPremiumAccountByCoins(price: Int): Boolean

    fun getPrice(): String
}