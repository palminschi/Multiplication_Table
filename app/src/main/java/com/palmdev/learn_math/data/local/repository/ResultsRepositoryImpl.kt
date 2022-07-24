package com.palmdev.learn_math.data.local.repository

import com.palmdev.learn_math.data.local.database.ResultsDao
import com.palmdev.learn_math.data.mapper.ResultExerciseMapper
import com.palmdev.learn_math.data.model.ExamResults
import com.palmdev.learn_math.data.model.ResultExercise
import com.palmdev.learn_math.data.local.storage.UserDataStorage
import com.palmdev.learn_math.utils.Operation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ResultsRepositoryImpl(
    private val resultsDao: ResultsDao,
    private val userDataStorage: UserDataStorage
    ) : ResultsRepository {

    private val resultExerciseMapper = ResultExerciseMapper()
    override suspend fun saveResult(resultExercise: ResultExercise) {
        resultsDao.saveResult(result = resultExerciseMapper.mapToData(resultExercise))
    }

    override suspend fun getResults(): Flow<List<ResultExercise>> {
        return resultsDao.getResults().map { list ->
            list.map {
                resultExerciseMapper.mapToPresentation(it)
            }
        }
    }

    override suspend fun getResultsByOperation(operation: Operation): Flow<List<ResultExercise>> {
        return resultsDao.getResultsByOperation(
            operation = when (operation) {
                Operation.MULTIPLICATION -> "MULTIPLICATION"
                Operation.DIVISION -> "DIVISION"
                Operation.ADDITION -> "ADDITION"
                Operation.SUBTRACTION -> "SUBTRACTION"
                else -> "MULTIPLICATION"
            }
        ).map { list ->
            list.map {
                resultExerciseMapper.mapToPresentation(it)
            }
        }
    }

    override fun saveExamResult(isPassed: Boolean) {
        userDataStorage.saveExamResult(isPassed)
    }

    override fun getExamResults(): ExamResults {
        return userDataStorage.getExamResult()
    }
}