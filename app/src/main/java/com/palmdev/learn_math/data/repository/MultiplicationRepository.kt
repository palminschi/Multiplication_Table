package com.palmdev.learn_math.data.repository

import com.palmdev.learn_math.data.model.ExerciseSelect
import com.palmdev.learn_math.data.model.ExerciseSimple
import com.palmdev.learn_math.data.model.ExerciseTrueOrFalse

interface MultiplicationRepository {

    fun getExerciseSimple(withNumber: Int, maxNumber: Int = 10): ExerciseSimple
    fun getExerciseSelect(withNumber: Int, maxNumber: Int = 10): ExerciseSelect
    fun getExerciseTrueOrFalse(withNumber: Int, maxNumber: Int = 10): ExerciseTrueOrFalse

}