package com.palmdev.learn_math.data.local.repository.games

import com.palmdev.learn_math.domain.repository.games.Game60secRepository
import com.palmdev.learn_math.domain.repository.games.GameHardMathRepository
import com.palmdev.learn_math.domain.repository.games.GameMoreOrLessRepository
import com.palmdev.learn_math.domain.repository.games.GameScoresRepository

class GameScoresRepositoryImpl(
    private val game60secRepository: Game60secRepository,
    private val gameMoreOrLessRepository: GameMoreOrLessRepository,
    private val gameHardMathRepository: GameHardMathRepository
) : GameScoresRepository {

    override fun game60sec(): Int? {
        return game60secRepository.getRecord()
    }

    override fun gameMoreOrLess(): Int? {
        return gameMoreOrLessRepository.getBestScore()
    }

    override fun gameHardMath(): Int? {
        return gameHardMathRepository.getBestScore()
    }
}