package com.palmdev.learn_math.domain.repository

import com.palmdev.learn_math.data.model.ExerciseSelect
import com.palmdev.learn_math.data.model.ExerciseInput
import com.palmdev.learn_math.data.model.ExerciseTrueOrFalse

interface MultiplicationRepository {

    fun getTable(withNumber: Int): String
    fun getExerciseInput(withNumber: Int, minNumber: Int = 0, maxNumber: Int = 10): ExerciseInput
    fun getExerciseSelect(
        withNumber: Int,
        minNumber: Int = 0,
        maxNumber: Int = 10,
        randomNumber: Int? = null
    ): ExerciseSelect

    fun getExerciseTrueOrFalse(
        withNumber: Int,
        minNumber: Int = 0,
        maxNumber: Int = 10
    ): ExerciseTrueOrFalse

}