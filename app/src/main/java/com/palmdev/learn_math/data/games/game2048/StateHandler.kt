package com.palmdev.learn_math.data.games.game2048

import android.content.Context
import com.palmdev.learn_math.data.games.game2048.model.Grid
import com.palmdev.learn_math.data.games.game2048.model.GridState
import java.io.*

object StateHandler {
    var grid = Grid(4)
    var currentGridState = GridState(grid)
    var previousGridState: GridState? = null

    var score = 0
    var highScore = 0
    var moveCount = 0
    var startTime = 0L
    var previouslyElapsedTime = 0L

    var previousScore = 0

    var over = false
    var won = false
    var continuingGame = false

    fun updateState() {
        val elapsedTime = previouslyElapsedTime + System.currentTimeMillis() - startTime
        currentGridState = GridState(grid, moveCount, score, highScore, over, won, continuingGame, elapsedTime)
    }

    fun saveState(context: Context) {
        val file = File(context.filesDir, "save.dat")
        val ois = ObjectOutputStream(FileOutputStream(file))
        ois.writeObject(currentGridState)
        ois.close()
    }

    fun loadState(context: Context): Boolean {
        val file = File(context.filesDir, "save.dat")
        if (!file.createNewFile()) {
            return try {
                val ois = ObjectInputStream(FileInputStream(file))
                val obj = ois.readObject()
                currentGridState = obj as GridState
                previousGridState = null
                true
            } catch (e: Exception) {
                false
            }
        }
        return false
    }

    fun newGame(listener: () -> Unit) {

        previousScore = 0
        score = 0
        moveCount = 0

        over = false
        won = false
        continuingGame = false

        grid = Grid(4)

        previousGridState = null

        previouslyElapsedTime = 0L
        startTime = System.currentTimeMillis()



        listener()
    }

    fun updateToMatchState(updateTime: Boolean = false, listener: () -> Unit) {
        moveCount = currentGridState.moveCount
        score = currentGridState.score
        highScore = currentGridState.bestScore
        over = currentGridState.gameOver
        won = currentGridState.won
        continuingGame = currentGridState.continuingGame


        if (updateTime) {
            previouslyElapsedTime = currentGridState.time
            startTime = System.currentTimeMillis()
        } else {
            currentGridState = GridState(currentGridState, previouslyElapsedTime)
        }

        grid = currentGridState.grid

        listener()
    }

    fun updateDataValues(listener: (Int, Int) -> Unit) {
        Stats.totalScore += score - previousScore
        previousScore = score

        if (score > highScore) {
            highScore = score
        }

        Stats.bestScore = highScore

        val currentMaxVal = grid.maxVal()
        val time = System.currentTimeMillis() - startTime + previouslyElapsedTime


        if (currentMaxVal > Stats.topTile) {
            Stats.topTile = currentMaxVal

            if (currentMaxVal >= 512) {
                Stats.tileStats.add(Stats.TileStats(currentMaxVal, 1, time, moveCount))
            }
        }

        val currentMaxStat = Stats.getStatForValue(currentMaxVal)

        currentMaxStat?.apply {
            gamesReached++
            if (time < shortestTime) {
                shortestTime = time
            }
            if (moveCount < fewestMoves) {
                fewestMoves = moveCount
            }
        }

        Stats.writeToFile()

        listener(score, highScore)
    }


}