package com.palmdev.learn_math.data.model

data class ExerciseSelect(
    val condition: String,
    val correctAnswer: Int,
    val correctAnswerPosition: Int,
    val choice_1: Int,
    val choice_2: Int,
    val choice_3: Int,
    val choice_4: Int
)
