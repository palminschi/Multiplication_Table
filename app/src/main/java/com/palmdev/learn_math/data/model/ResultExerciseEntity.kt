package com.palmdev.learn_math.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.palmdev.learn_math.utils.RESULTS_TABLE

@Entity(tableName = RESULTS_TABLE)
data class ResultExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val operation: String,
    val correctAnswersPercent: Int,
    val avgAnswerTime: Double // seconds
)
