package com.palmdev.learn_math.data.mapper

import com.palmdev.learn_math.data.model.ResultExercise
import com.palmdev.learn_math.data.model.ResultExerciseEntity
import com.palmdev.learn_math.utils.Operation

class ResultExerciseMapper {
    fun mapToData(resultExercise: ResultExercise): ResultExerciseEntity {
        return ResultExerciseEntity(
            operation = when (resultExercise.operation) {
                Operation.MULTIPLICATION -> "MULTIPLICATION"
                Operation.DIVISION -> "DIVISION"
                Operation.ADDITION -> "ADDITION"
                Operation.SUBTRACTION -> "SUBTRACTION"
                else -> "MULTIPLICATION"
            },
            correctAnswersPercent = resultExercise.correctAnswersPercent,
            avgAnswerTime = resultExercise.avgAnswerTime
        )
    }
    fun mapToPresentation(resultExerciseEntity: ResultExerciseEntity): ResultExercise {
        return ResultExercise(
            id = resultExerciseEntity.id,
            operation = when (resultExerciseEntity.operation) {
                "MULTIPLICATION" -> Operation.MULTIPLICATION
                "DIVISION" -> Operation.DIVISION
                "ADDITION" -> Operation.ADDITION
                "SUBTRACTION" -> Operation.SUBTRACTION
                else -> Operation.MULTIPLICATION
            },
            correctAnswersPercent = resultExerciseEntity.correctAnswersPercent,
            avgAnswerTime = resultExerciseEntity.avgAnswerTime
        )
    }
}