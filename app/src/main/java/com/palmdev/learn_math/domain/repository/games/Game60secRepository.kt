package com.palmdev.learn_math.domain.repository.games

import com.palmdev.learn_math.data.model.ExerciseSelect
import com.palmdev.learn_math.utils.Operation


interface Game60secRepository {

    fun getExercise(
        minNumber: Int = 0,
        maxNumber: Int = 10,
        operation: Operation
    ): ExerciseSelect
    fun getRecord(): Int?
    fun saveRecord(correctAnswers: Int)

}