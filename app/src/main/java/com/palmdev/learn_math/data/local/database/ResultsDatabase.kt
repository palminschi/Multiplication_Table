package com.palmdev.learn_math.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.palmdev.learn_math.data.model.ResultExerciseEntity

@Database(entities = [ResultExerciseEntity::class], version = 1, exportSchema = false)
abstract class ResultsDatabase : RoomDatabase() {
    abstract fun resultsDao(): ResultsDao
}