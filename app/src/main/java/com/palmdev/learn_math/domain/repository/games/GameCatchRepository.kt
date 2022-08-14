package com.palmdev.learn_math.domain.repository.games

import com.palmdev.learn_math.data.model.ExerciseCatch

interface GameCatchRepository {
    fun getExercise(
        minNumber: Int = 0,
        maxNumber: Int = 10
    ): ExerciseCatch
    fun getBestScore(): Int?
    fun saveBestScore(correctAnswers: Int)
}