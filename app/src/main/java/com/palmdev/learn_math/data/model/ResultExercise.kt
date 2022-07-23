package com.palmdev.learn_math.data.model

import com.palmdev.learn_math.utils.Operation

data class ResultExercise(
    val id: Long? = null,
    val operation: Operation,
    val correctAnswersPercent: Int, // 0-100
    val avgAnswerTime: Double // 3.50 -> 3.5s
)
