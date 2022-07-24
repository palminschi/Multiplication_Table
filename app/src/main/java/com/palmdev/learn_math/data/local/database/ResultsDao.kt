package com.palmdev.learn_math.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.palmdev.learn_math.data.model.ResultExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveResult(result: ResultExerciseEntity)

    @Query("SELECT * FROM results_table")
    fun getResults(): Flow<List<ResultExerciseEntity>>

    @Query("SELECT * FROM results_table WHERE operation = :operation")
    fun getResultsByOperation(operation: String): Flow<List<ResultExerciseEntity>>

}