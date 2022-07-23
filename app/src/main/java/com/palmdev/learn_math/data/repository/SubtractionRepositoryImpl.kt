package com.palmdev.learn_math.data.repository

import com.palmdev.learn_math.data.model.ExerciseSelect
import com.palmdev.learn_math.data.model.ExerciseInput
import com.palmdev.learn_math.data.model.ExerciseTrueOrFalse
import kotlin.random.Random

class SubtractionRepositoryImpl : SubtractionRepository {

    override fun getExerciseInput(withNumber: Int, minNumber: Int, maxNumber: Int): ExerciseInput {
        val random = Random(System.currentTimeMillis() + 1234)
        val secondNumber = random.nextInt(minNumber, maxNumber + 1)
        val firstNumber = if (withNumber == 0) 1 else withNumber

        val result = firstNumber + secondNumber
        val exerciseCondition = "$result - $firstNumber ="

        return ExerciseInput(
            condition = exerciseCondition,
            answer = secondNumber
        )
    }

    override fun getExerciseSelect(
        withNumber: Int,
        minNumber: Int,
        maxNumber: Int
    ): ExerciseSelect {
        val random = Random(System.currentTimeMillis() + 1234)
        val firstNumber = if (withNumber == 0) 1 else withNumber
        val max = if (maxNumber == 0) 1 else maxNumber
        val result = random.nextInt(minNumber, max + 1)
        val exerciseCondition = "${firstNumber + result} - $firstNumber ="
        val exerciseEquation = "${firstNumber + result} - $firstNumber = $result"
        val positionRightAnswer = random.nextInt(1, 5)

        var wrongResult1 = random.nextInt(minNumber, max + 1)
        while (wrongResult1 == result)
            wrongResult1 = random.nextInt(minNumber, max + 2)

        var wrongResult2 = random.nextInt(minNumber, max + 1)
        while (wrongResult2 == result || wrongResult2 == wrongResult1)
            wrongResult2 = random.nextInt(minNumber, max + 3)

        var wrongResult3 = random.nextInt(minNumber, max + 1)
        while (wrongResult3 == result || wrongResult3 == wrongResult2 || wrongResult3 == wrongResult1)
            wrongResult3 = random.nextInt(minNumber, max + 4)

        return ExerciseSelect(
            condition = exerciseCondition,
            equation = exerciseEquation,
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

    override fun getExerciseTrueOrFalse(
        withNumber: Int,
        minNumber: Int,
        maxNumber: Int
    ): ExerciseTrueOrFalse {
        val random = Random(System.currentTimeMillis() + 1234)
        val firstNumber = if (withNumber == 0) 1 else withNumber
        val secondNumber = random.nextInt(minNumber, maxNumber + 1)

        val result = firstNumber + secondNumber
        val exerciseCondition = "$result - $firstNumber ="
        val isTrue = random.nextBoolean()

        var wrongAnswer = random.nextInt(minNumber, maxNumber + 1)
        while (wrongAnswer == secondNumber) wrongAnswer =
            if (maxNumber == 0) 1
            else random.nextInt(minNumber, maxNumber + 1)

        return ExerciseTrueOrFalse(
            condition = exerciseCondition,
            correctAnswer = secondNumber,
            wrongAnswer = wrongAnswer,
            isTrueOrFalse = isTrue
        )
    }
}