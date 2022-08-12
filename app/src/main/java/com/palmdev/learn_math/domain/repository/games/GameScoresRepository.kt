package com.palmdev.learn_math.domain.repository.games

interface GameScoresRepository {
    fun game60sec(): Int?
    fun gameMoreOrLess(): Int?
}