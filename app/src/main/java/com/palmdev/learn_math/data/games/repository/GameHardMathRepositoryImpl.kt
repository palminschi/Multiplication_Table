package com.palmdev.learn_math.data.games.repository

import android.content.Context
import com.palmdev.learn_math.data.model.ExerciseHardMath
import com.palmdev.learn_math.domain.repository.games.GameHardMathRepository
import com.palmdev.learn_math.utils.Operation
import com.palmdev.learn_math.utils.SHARED_PREFS
import kotlin.random.Random

private const val GAME_HARD_MATH = "GAME_HARD_MATH"

class GameHardMathRepositoryImpl(context: Context): GameHardMathRepository {

    private val sharedPrefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    override fun getExercise(minNumber: Int, maxNumber: Int): ExerciseHardMath {
        val minNum = if (minNumber > 0) minNumber else 1
        val operationOne = getRandomOperation(Random(System.currentTimeMillis() * 2))
        var operationTwo = getRandomOperation(Random(System.currentTimeMillis() * 3))
        while (operationTwo == Operation.DIVISION)
            operationTwo = getRandomOperation(Random(System.currentTimeMillis()))
        val numberOne = Random(System.currentTimeMillis() * 4).nextInt(minNum, maxNumber + 1)
        val numberTwo = Random(System.currentTimeMillis() * 5).nextInt(minNum, maxNumber + 1)
        val numberThree = Random(System.currentTimeMillis() * 6).nextInt(minNum, maxNumber + 1)
        val charOne = getOperationChar(operationOne)
        val charTwo = getOperationChar(operationTwo)
        val firstResult = when (operationOne) {
            Operation.MULTIPLICATION -> numberOne * numberTwo
            Operation.DIVISION -> (numberOne * numberTwo) / numberTwo
            Operation.SUBTRACTION -> (numberOne + numberTwo) - numberTwo
            Operation.ADDITION -> numberOne + numberTwo
        }
        val result = when (operationTwo) {
            Operation.MULTIPLICATION -> firstResult * numberThree
            Operation.DIVISION -> firstResult / numberThree
            Operation.SUBTRACTION -> firstResult - numberThree
            Operation.ADDITION -> firstResult + numberThree
        }
        val condition = when (operationOne) {
            Operation.MULTIPLICATION -> "($numberOne $charOne $numberTwo) $charTwo $numberThree ="
            Operation.DIVISION -> "(${numberOne * numberTwo} $charOne $numberTwo) $charTwo $numberThree ="
            Operation.SUBTRACTION -> "(${numberOne + numberTwo} $charOne $numberTwo) $charTwo $numberThree ="
            Operation.ADDITION -> "($numberOne $charOne $numberTwo) $charTwo $numberThree ="
        }
        val random = Random(System.currentTimeMillis() + 1234)
        val positionRightAnswer = random.nextInt(1, 5)
        val wrongResult1 = when (result) {
            in 0..10 -> result + 2
            in 11..50 -> result + 4
            in 51..100 -> result + 9
            in -1 downTo -10000 -> result - 1
            else -> result + (result / 10)
        }
        val wrongResult2 = when (result) {
            in 0..10 -> result + 4
            in 11..50 -> result - 4
            in 51..100 -> result - 2
            in -1 downTo -10000 -> result - 4
            else -> result - (result / 10)
        }
        val wrongResult3 = when (result) {
            in 0..10 -> result + 1
            in 11..50 -> result - 2
            in 51..100 -> result + 6
            in -1 downTo -10000 -> result - 3
            else -> result + 4
        }
        return ExerciseHardMath(
            condition = condition,
            equation = "$condition $result",
            correctAnswer = result,
            correctAnswerPosition = positionRightAnswer,
            choice_1 = when (positionRightAnswer) {
                1 -> result
                2 -> wrongResult1
                3 -> wrongResult2
                4 -> wrongResult3
                else -> result
            },
            choice_2 = when (positionRightAnswer) {
                1 -> wrongResult3
                2 -> result
                3 -> wrongResult1
                4 -> wrongResult2
                else -> wrongResult3
            },
            choice_3 = when (positionRightAnswer) {
                1 -> wrongResult2
                2 -> wrongResult3
                3 -> result
                4 -> wrongResult1
                else -> wrongResult2
            },
            choice_4 = when (positionRightAnswer) {
                1 -> wrongResult1
                2 -> wrongResult2
                3 -> wrongResult3
                4 -> result
                else -> wrongResult1
            }
        )
    }

    override fun getBestScore(): Int? {
        val record = sharedPrefs.getInt(GAME_HARD_MATH, 0)
        return if (record == 0) null
        else record
    }

    override fun saveBestScore(bestScore: Int) {
        sharedPrefs.edit().putInt(GAME_HARD_MATH, bestScore).apply()
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

    private fun getOperationChar(operation: Operation): Char {
        return when (operation) {
            Operation.MULTIPLICATION -> 'x'
            Operation.DIVISION -> '÷'
            Operation.SUBTRACTION -> '-'
            Operation.ADDITION -> '+'
        }
    }
}