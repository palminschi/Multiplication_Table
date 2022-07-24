package com.palmdev.learn_math.data.local.repository

import com.palmdev.learn_math.data.model.ExerciseSelect
import com.palmdev.learn_math.data.model.ExerciseInput
import com.palmdev.learn_math.data.model.ExerciseTrueOrFalse

interface DivisionRepository {

    fun getTable(withNumber: Int): String
    fun getExerciseInput(withNumber: Int, minNumber: Int = 0, maxNumber: Int = 10): ExerciseInput
    fun getExerciseSelect(withNumber: Int, minNumber: Int = 0, maxNumber: Int = 10): ExerciseSelect
    fun getExerciseTrueOrFalse(withNumber: Int, minNumber: Int = 0, maxNumber: Int = 10): ExerciseTrueOrFalse

}