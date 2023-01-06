package com.palmdev.learn_math.data.games.game2048.model

import java.io.Serializable

data class GridState(
    val grid: Grid, val moveCount: Int = 0, val score: Int = 0, val bestScore: Int = 0,
    val gameOver: Boolean = false, val won: Boolean = false,
    val continuingGame: Boolean = false, val time: Long = 0
) : Serializable {

    constructor(gridState: GridState, time: Long) : this(
        gridState.grid.copy(), gridState.moveCount, gridState.score, gridState.bestScore,
        gridState.gameOver, gridState.won, gridState.continuingGame, time
    )

    fun copy(): GridState {
        return GridState(grid.copy(), moveCount, score, bestScore, gameOver, won, continuingGame, time)
    }
}