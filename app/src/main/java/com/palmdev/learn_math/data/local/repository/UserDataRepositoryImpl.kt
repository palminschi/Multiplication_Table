package com.palmdev.learn_math.data.local.repository

import android.content.Context
import com.palmdev.learn_math.data.local.storage.UserDataStorage
import com.palmdev.learn_math.utils.PREMIUM_USER_KEY
import com.palmdev.learn_math.utils.SHARED_PREFS

class UserDataRepositoryImpl(
    private val context: Context,
    private val userDataStorage: UserDataStorage
) : UserDataRepository {

    private val sharedPrefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)


    override val coins: Int get() = userDataStorage.getCoins()

    override fun addCoins(amount: Int) { userDataStorage.addCoins(amount) }

    override fun removeCoins(amount: Int) { userDataStorage.removeCoins(amount) }

    override val isPremiumUser get() = sharedPrefs.getBoolean(PREMIUM_USER_KEY, false)

    override fun setIsPremiumUser(boolean: Boolean) {
        sharedPrefs.edit().putBoolean(PREMIUM_USER_KEY, boolean).apply()
    }

}