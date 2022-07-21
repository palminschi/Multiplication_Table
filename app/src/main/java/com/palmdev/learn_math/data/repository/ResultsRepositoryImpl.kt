package com.palmdev.learn_math.data.repository

import com.palmdev.learn_math.data.database.ResultsDao
import com.palmdev.learn_math.data.mapper.ResultMapper
import com.palmdev.learn_math.data.model.ExamResults
import com.palmdev.learn_math.data.model.ResultExercise
import com.palmdev.learn_math.data.storage.UserDataStorage
import com.palmdev.learn_math.utils.Operation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ResultsRepositoryImpl(
    private val resultsDao: ResultsDao,
    private val userDataStorage: UserDataStorage
    ) : ResultsRepository {

    private val resultMapper = ResultMapper()
    override suspend fun saveResult(resultExercise: ResultExercise) {
        resultsDao.saveResult(result = resultMapper.mapToData(resultExercise))
    }

    override suspend fun getResults(): Flow<List<ResultExercise>> {
        return resultsDao.getResults().map { list ->
            list.map {
                resultMapper.mapToPresentation(it)
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
                resultMapper.mapToPresentation(it)
            }
        }
    }

    override fun addCoins(amount: Int) {
        userDataStorage.addCoins(amount)
    }

    override fun getCoins(): Int {
        return userDataStorage.getCoins()
    }

    override fun saveExamResult(isPassed: Boolean) {
        userDataStorage.saveExamResult(isPassed)
    }

    override fun getExamResults(): ExamResults {
        return userDataStorage.getExamResult()
    }
}