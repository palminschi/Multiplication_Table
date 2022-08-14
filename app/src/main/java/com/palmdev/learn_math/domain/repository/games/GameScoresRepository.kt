package com.palmdev.learn_math.domain.repository.games

interface GameScoresRepository {
    fun game60sec(): Int?
    fun gameMoreOrLess(): Int?
    fun gameHardMath(): Int?
    fun gameCatch(): Int?
}