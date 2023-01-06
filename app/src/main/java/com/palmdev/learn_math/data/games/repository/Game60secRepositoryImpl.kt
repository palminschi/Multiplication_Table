package com.palmdev.learn_math.data.games.repository

import android.content.Context
import com.palmdev.learn_math.data.model.ExerciseSelect
import com.palmdev.learn_math.domain.repository.AdditionRepository
import com.palmdev.learn_math.domain.repository.DivisionRepository
import com.palmdev.learn_math.domain.repository.MultiplicationRepository
import com.palmdev.learn_math.domain.repository.SubtractionRepository
import com.palmdev.learn_math.domain.repository.games.Game60secRepository
import com.palmdev.learn_math.utils.Operation
import com.palmdev.learn_math.utils.SHARED_PREFS
import kotlin.random.Random

private const val GAME_60_SEC_RECORD = "GAME_60_SEC_RECORD"

class Game60secRepositoryImpl(
    private val context: Context,
    private val multiplicationRepository: MultiplicationRepository,
    private val divisionRepository: DivisionRepository,
    private val additionRepository: AdditionRepository,
    private val subtractionRepository: SubtractionRepository
) : Game60secRepository {

    private val sharedPrefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    override fun getExercise(
        minNumber: Int,
        maxNumber: Int,
        operation: Operation
    ): ExerciseSelect {
        val withNumber = Random(System.currentTimeMillis()).nextInt(minNumber, maxNumber + 1)
        return when (operation) {
            Operation.MULTIPLICATION -> multiplicationRepository.getExerciseSelect(
                withNumber,
                minNumber,
                maxNumber
            )
            Operation.DIVISION -> divisionRepository.getExerciseSelect(
                withNumber,
                minNumber,
                maxNumber
            )
            Operation.ADDITION -> additionRepository.getExerciseSelect(
                withNumber,
                minNumber,
                maxNumber
            )
            Operation.SUBTRACTION -> subtractionRepository.getExerciseSelect(
                withNumber,
                minNumber,
                maxNumber
            )
        }
    }

    override fun getRecord(): Int? {
        val record = sharedPrefs.getInt(GAME_60_SEC_RECORD, 0)
        return if (record == 0) null
        else record
    }

    override fun saveRecord(correctAnswers: Int) {
        sharedPrefs.edit().putInt(GAME_60_SEC_RECORD, correctAnswers).apply()
    }
}