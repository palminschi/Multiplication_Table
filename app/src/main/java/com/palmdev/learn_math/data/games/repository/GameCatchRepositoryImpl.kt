package com.palmdev.learn_math.data.games.repository

import android.content.Context
import com.palmdev.learn_math.data.model.ExerciseCatch
import com.palmdev.learn_math.domain.repository.games.GameCatchRepository
import com.palmdev.learn_math.utils.Operation
import com.palmdev.learn_math.utils.SHARED_PREFS
import kotlin.random.Random

private const val GAME_CATCH = "GAME_CATCH"

class GameCatchRepositoryImpl(context: Context) : GameCatchRepository {

    private val sharedPrefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    override fun getExercise(minNumber: Int, maxNumber: Int): ExerciseCatch {
        val min = if (minNumber <= 0) 1 else minNumber
        val firstNumber = Random(System.currentTimeMillis()).nextInt(min, maxNumber + 1)
        val secondNumber = Random(System.currentTimeMillis() * 2).nextInt(min, maxNumber + 1)
        val operation = getRandomOperation(Random(System.currentTimeMillis()))
        val result = when (operation) {
            Operation.MULTIPLICATION -> firstNumber * secondNumber
            Operation.DIVISION -> firstNumber
            Operation.ADDITION -> firstNumber + secondNumber
            Operation.SUBTRACTION -> firstNumber
        }
        val condition = when (operation) {
            Operation.MULTIPLICATION -> "$firstNumber x $secondNumber ="
            Operation.DIVISION -> "${firstNumber * secondNumber} ÷ $secondNumber ="
            Operation.ADDITION -> "$firstNumber + $secondNumber ="
            Operation.SUBTRACTION -> "${firstNumber + secondNumber} - $secondNumber ="
        }
        val isFirstPosition = Random(System.currentTimeMillis() * 3).nextBoolean()
        val wrongAnswer = when (operation) {
            Operation.MULTIPLICATION -> firstNumber * (secondNumber + 1)
            Operation.DIVISION -> firstNumber + 1
            Operation.ADDITION -> firstNumber + (secondNumber + 2)
            Operation.SUBTRACTION -> firstNumber + 2
        }

        return ExerciseCatch(
            condition = condition,
            correctAnswer = result,
            correctAnswerPosition = if (isFirstPosition) 1 else 2,
            choice_1 = if (isFirstPosition) result else wrongAnswer,
            choice_2 = if (isFirstPosition) wrongAnswer else result
        )
    }

    override fun getBestScore(): Int? {
        val record = sharedPrefs.getInt(GAME_CATCH, 0)
        return if (record == 0) null
        else record
    }

    override fun saveBestScore(correctAnswers: Int) {
        sharedPrefs.edit().putInt(GAME_CATCH, correctAnswers).apply()
    }

    private fun getRandomOperation(random: Random): Operation {
        return when (random.nextInt(4)) {
            0 -> Operation.MULTIPLICATION
            1 -> Operation.DIVISION
            2 -> Operation.SUBTRACTION
            3 -> Operation.ADDITION
            else -> Operation.MULTIPLICATION
        }
    }
}