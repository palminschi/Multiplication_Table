package com.palmdev.learn_math.data.local.repository.games

import com.palmdev.learn_math.domain.repository.games.*

class GameScoresRepositoryImpl(
    private val game60secRepository: Game60secRepository,
    private val gameMoreOrLessRepository: GameMoreOrLessRepository,
    private val gameHardMathRepository: GameHardMathRepository,
    private val gameCatchRepository: GameCatchRepository
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

    override fun gameCatch(): Int? {
        return gameCatchRepository.getBestScore()
    }
}