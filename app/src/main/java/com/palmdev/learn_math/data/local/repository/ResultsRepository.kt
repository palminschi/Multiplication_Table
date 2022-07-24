package com.palmdev.learn_math.data.local.repository

import com.palmdev.learn_math.data.model.ExamResults
import com.palmdev.learn_math.data.model.ResultExercise
import com.palmdev.learn_math.utils.Operation
import kotlinx.coroutines.flow.Flow

interface ResultsRepository {
    suspend fun saveResult(resultExercise: ResultExercise)
    suspend fun getResults(): Flow<List<ResultExercise>>
    suspend fun getResultsByOperation(operation: Operation): Flow<List<ResultExercise>>
    fun saveExamResult(isPassed: Boolean)
    fun getExamResults(): ExamResults
}