package com.palmdev.learn_math.domain.repository.games

import com.palmdev.learn_math.data.model.ExerciseMoreOrLess
import com.palmdev.learn_math.data.model.ExerciseSelect

interface GameMoreOrLessRepository {

    fun getExercise(
        minNumber: Int = 0,
        maxNumber: Int = 10
    ): ExerciseMoreOrLess
    fun getBestScore(): Int?
    fun saveBestScore(bestScore: Int)

}