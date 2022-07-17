package com.palmdev.learn_math.data.model

data class ExerciseTrueOrFalse(
     val condition: String,
     val correctAnswer: Int,
     val wrongAnswer: Int,
     val isTrueOrFalse: Boolean
)
