package com.palmdev.learn_math.data.local.repository.games

import android.content.Context
import com.palmdev.learn_math.data.model.ExerciseMoreOrLess
import com.palmdev.learn_math.domain.repository.games.GameMoreOrLessRepository
import com.palmdev.learn_math.utils.Operation
import com.palmdev.learn_math.utils.SHARED_PREFS
import kotlin.random.Random

private const val GAME_MORE_OR_LESS = "GAME_MORE_OR_LESS"

class GameMoreOrLessRepositoryImpl(context: Context) : GameMoreOrLessRepository {

    private val sharedPrefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    private data class Equation(val string: String, val answer: Int)

    override fun getExercise(minNumber: Int, maxNumber: Int): ExerciseMoreOrLess {
        val random = Random(System.currentTimeMillis())
        val isTrueOrFalse = random.nextBoolean()
        val randomOperation1 = getRandomOperation(Random(System.currentTimeMillis() * 2))
        val randomOperation2 = getRandomOperation(Random(System.currentTimeMillis() * 3))
        val equation1 = getEquation(operation = randomOperation1, minNumber, maxNumber)
        val equation2 = getEquation(operation = randomOperation2, minNumber, maxNumber)
        val correctSymbol =
            if (equation1.answer > equation2.answer) ">"
            else if (equation1.answer < equation2.answer) "<"
            else "="
        val wrongSymbol =
            if (equation1.answer > equation2.answer) "<"
            else if (equation1.answer < equation2.answer) "="
            else ">"

        return when (isTrueOrFalse) {
            true -> ExerciseMoreOrLess(
                    condition = "${equation1.string} $correctSymbol ${equation2.string}",
                    correctAnswer = "${equation1.string} $correctSymbol ${equation2.string}",
                    isTrue = true
                )
            false -> ExerciseMoreOrLess(
                condition = "${equation1.string} $wrongSymbol ${equation2.string}",
                correctAnswer = "${equation1.string} $correctSymbol ${equation2.string}",
                isTrue = false
            )
        }
    }

    override fun getBestScore(): Int? {
        val record = sharedPrefs.getInt(GAME_MORE_OR_LESS, 0)
        return if (record == 0) null
        else record
    }

    override fun saveBestScore(bestScore: Int) {
        sharedPrefs.edit().putInt(GAME_MORE_OR_LESS, bestScore).apply()
    }

    private fun getEquation(operation: Operation, minNumber: Int, maxNumber: Int): Equation {
        val firstNumber = Random(System.currentTimeMillis() * 2)
            .nextInt(minNumber, maxNumber + 1)
        val secondNumber = Random(System.currentTimeMillis() * 3)
            .nextInt(minNumber, maxNumber + 1)

        return when (operation) {
            Operation.MULTIPLICATION -> Equation(
                string = "$firstNumber x $secondNumber",
                answer = firstNumber * secondNumber
            )
            Operation.DIVISION -> Equation(
                string = "${firstNumber * secondNumber} ÷ $secondNumber",
                answer = firstNumber
            )
            Operation.ADDITION -> Equation(
                string = "$firstNumber + $secondNumber",
                answer = firstNumber + secondNumber
            )
            Operation.SUBTRACTION -> Equation(
                string = "${firstNumber + secondNumber} - $secondNumber",
                answer = firstNumber
            )
        }
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