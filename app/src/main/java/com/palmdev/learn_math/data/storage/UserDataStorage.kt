package com.palmdev.learn_math.data.storage

import android.content.Context
import com.palmdev.learn_math.data.model.ExamResults
import com.palmdev.learn_math.utils.COINS_KEY
import com.palmdev.learn_math.utils.PASSED_EXAMS
import com.palmdev.learn_math.utils.SHARED_PREFS
import com.palmdev.learn_math.utils.TOTAL_EXAMS

class UserDataStorage(context: Context) {

    private val sharedPrefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    fun addCoins(amount: Int) {
        val currentAmount = getCoins()
        sharedPrefs.edit().putInt(COINS_KEY, currentAmount + amount).apply()
    }

    fun getCoins(): Int {
        return sharedPrefs.getInt(COINS_KEY, 50)
    }

    fun saveExamResult(isPassed: Boolean) {
        val examResults = getExamResult()
        sharedPrefs.edit().putInt(TOTAL_EXAMS, examResults.totalAmount + 1).apply()
        if (isPassed) sharedPrefs.edit().putInt(PASSED_EXAMS, examResults.passed + 1).apply()
    }

    fun getExamResult(): ExamResults {
        val totalExams = sharedPrefs.getInt(TOTAL_EXAMS, 0)
        val passedExams = sharedPrefs.getInt(PASSED_EXAMS, 0)
        return ExamResults(
            totalAmount = totalExams,
            passed = passedExams
        )
    }
}