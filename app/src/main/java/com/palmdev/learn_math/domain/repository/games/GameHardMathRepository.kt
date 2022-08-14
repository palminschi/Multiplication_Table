package com.palmdev.learn_math.domain.repository.games

import com.palmdev.learn_math.data.model.ExerciseHardMath

interface GameHardMathRepository {
    fun getExercise(
        minNumber: Int = 0,
        maxNumber: Int = 10
    ): ExerciseHardMath
    fun getBestScore(): Int?
    fun saveBestScore(bestScore: Int)
}