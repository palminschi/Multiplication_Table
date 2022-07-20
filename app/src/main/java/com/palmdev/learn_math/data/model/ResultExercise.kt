package com.palmdev.learn_math.data.model

import com.palmdev.learn_math.utils.Operation

data class ResultExercise(
    val id: Long? = null,
    val operation: Operation,
    val correctAnswersPercent: Float, // 0.5f -> 50%
    val avgAnswerTime: Float // 3.5f -> 3.5s
)
