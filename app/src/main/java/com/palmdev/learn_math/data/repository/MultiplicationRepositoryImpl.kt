package com.palmdev.learn_math.data.repository

import com.palmdev.learn_math.data.model.ExerciseSelect
import com.palmdev.learn_math.data.model.ExerciseSimple
import com.palmdev.learn_math.data.model.ExerciseTrueOrFalse
import kotlin.random.Random

class MultiplicationRepositoryImpl : MultiplicationRepository {

    override fun getExerciseSimple(withNumber: Int, maxNumber: Int): ExerciseSimple {
        val random = Random(System.currentTimeMillis())
        val firstNumber = withNumber
        val secondNumber = random.nextInt(0, maxNumber + 1)

        val result = firstNumber * secondNumber
        val exerciseCondition = "$firstNumber x $secondNumber ="

        return ExerciseSimple(
            condition = exerciseCondition,
            answer = result
        )
    }

    /** Get a multiplication exercise with 4 choices (3 of them wrong)
    and random position of the right answer. **/
    override fun getExerciseSelect(withNumber: Int, maxNumber: Int): ExerciseSelect {
        val random = Random(System.currentTimeMillis())
        val firstNumber = withNumber
        val secondNumber = random.nextInt(0, maxNumber + 1)

        val result = firstNumber * secondNumber
        val exerciseCondition = "$firstNumber x $secondNumber ="
        val positionRightAnswer = random.nextInt(1, 5)

        val wrongResult1 = when (result) {
            in 0..10 -> result + 2
            in 11..50 -> result + 4
            in 51..100 -> result + 9
            else -> result + (result / 10)
        }
        val wrongResult2 = when (result) {
            in 0..10 -> result + 4
            in 11..50 -> result - 4
            in 51..100 -> result - 2
            else -> result - (result / 10)
        }
        val wrongResult3 = when (result) {
            in 0..10 -> result + 1
            in 11..50 -> result - 2
            in 51..100 -> result + 6
            else -> result + 4
        }

        return ExerciseSelect(
            condition = exerciseCondition,
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

    /** Get a multiplication exercise True or False **/
    override fun getExerciseTrueOrFalse(withNumber: Int, maxNumber: Int): ExerciseTrueOrFalse {
        val random = Random(System.currentTimeMillis())
        val firstNumber = withNumber
        val secondNumber = random.nextInt(0, maxNumber + 1)

        val result = firstNumber * secondNumber
        val exerciseCondition = "$firstNumber x $secondNumber ="
        val isTrue = random.nextBoolean()

        val wrongAnswer = when (result) {
            in 0..9 -> result + random.nextInt(1, 5)
            else -> {
                val toAdd = random.nextBoolean()
                if (toAdd) result + (result / random.nextInt(1, 11))
                else result - (result / random.nextInt(1, 11))
            }
        }

        return ExerciseTrueOrFalse(
            condition = exerciseCondition,
            correctAnswer = result,
            wrongAnswer = wrongAnswer,
            isTrue = isTrue
        )
    }
}