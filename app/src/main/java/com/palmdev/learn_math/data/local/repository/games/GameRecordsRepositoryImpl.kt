package com.palmdev.learn_math.data.local.repository.games

import com.palmdev.learn_math.domain.repository.games.Game60secRepository
import com.palmdev.learn_math.domain.repository.games.GameRecordsRepository

class GameRecordsRepositoryImpl(
    private val game60secRepository: Game60secRepository
) : GameRecordsRepository {

    override fun game60sec(): Int? { return game60secRepository.getRecord() }

}