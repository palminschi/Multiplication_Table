package com.palmdev.learn_math.presentation.screens.games.game_2048

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.data.games.game2048.StateHandler
import com.palmdev.learn_math.data.games.game2048.Stats
import com.palmdev.learn_math.data.games.game2048.model.Pos
import com.palmdev.learn_math.data.games.game2048.model.Tile
import com.palmdev.learn_math.databinding.FragmentGame2048Binding
import com.palmdev.learn_math.utils.ARG_START_NEW_GAME
import org.koin.androidx.viewmodel.ext.android.viewModel

class Game2048Fragment : Fragment() {

    private val viewModel: Game2048ViewModel by viewModel()
    private lateinit var binding: FragmentGame2048Binding
    private var tilesToRemove = ArrayList<Tile>()
    private var margin = 0
    private var textBrown = 0
    private var textOffWhite = 0
    private val stateHandler = StateHandler
    private var startNewGame = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGame2048Binding.inflate(layoutInflater, container, false)
        startNewGame = arguments?.getBoolean(ARG_START_NEW_GAME) ?: false
        Stats.init(requireContext())
        viewModel.init()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textBrown = resources.getColor(R.color.game_2048_dark_text, null)
        textOffWhite = resources.getColor(R.color.game_2048_light_text, null)
        margin = binding.tile00.paddingTop
        initButtons()

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
                viewModel.showAd()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    override fun onResume() {
        super.onResume()
        clearViews()

        if (stateHandler.loadState(requireContext())) {
            stateHandler.updateToMatchState(true, this::onUpdateState)
        } else {
            newGame()
        }

        binding.touchReceiver.setOnTouchListener(TileTouchListener(this))

        stateHandler.updateDataValues(this::updateDisplayedData)

        val constraints = ConstraintSet()
        constrainToTarget(constraints, binding.touchReceiver.id, binding.gameContainer.id)
        constraints.applyTo(binding.root)

        if (startNewGame) newGame()

        if (stateHandler.over) {
            dialogGameOver()
        } else if (stateHandler.won && !stateHandler.continuingGame) {
            dialogContinue()
        }

    }

    override fun onPause() {
        super.onPause()
        stateHandler.updateState()
        StateHandler.saveState(requireContext())
        Stats.writeToFile()
    }

    private fun initButtons() {
        // TODO: Share
        binding.newGameButton.setOnClickListener {
            viewModel.showAd()
            newGame()
        }
        binding.undoButton.setOnClickListener {
            viewModel.showAd()
            undoMove()
        }
    }

    private fun undoMove() {
        stateHandler.previousGridState?.let {
            //Revert gamesReached count if user just got there
            val maxTile = stateHandler.currentGridState.grid.maxVal()
            if (maxTile != stateHandler.previousGridState?.grid?.maxVal()) {
                val currentMaxStat =
                    Stats.getStatForValue(stateHandler.currentGridState.grid.maxVal())
                currentMaxStat?.let {
                    it.gamesReached--
                }
            }
            stateHandler.grid.forEach { tile ->
                tile?.let {
                    tilesToRemove.add(tile)
                }
            }
            stateHandler.currentGridState = stateHandler.previousGridState!!
            stateHandler.previousGridState = null

            tilesToRemove.forEach {
                binding.gameContainer.removeView(it.textView)
            }
            tilesToRemove.clear()

            stateHandler.updateToMatchState(listener = this::onUpdateState)
        }
            ?: if (stateHandler.moveCount != 0)
                Toast.makeText(requireContext(), getText(R.string.onlyOneUndo), Toast.LENGTH_SHORT)
                    .show()
    }

    private fun updateDisplayedData(score: Int, highScore: Int) {
        binding.scoreView.text = formatScore(score)
        binding.bestScore.text = formatScore(highScore)
        val movesText = if (stateHandler.moveCount == 1) {
            "1 move"
        } else {
            "${stateHandler.moveCount} moves"
        }
        binding.moveCountTextView.text = movesText
    }

    private fun formatScore(score: Int): String {
        return when {
            score >= 1_000_000_000 -> "${(score / 100_000_000).toFloat() / 10}b"
            score >= 1_000_000 -> "${(score / 100_000).toFloat() / 10}m"
            score >= 1_000 -> "${(score / 100).toFloat() / 10}k"
            else -> score.toString()
        }
    }

    private fun dialogContinue() {
        findNavController().navigate(
            R.id.continue2048DialogFragment
        )
    }

    private fun dialogGameOver() {
        findNavController().navigate(
            R.id.game2048OverDialogFragment
        )
    }

    private fun onUpdateState() {
        clearViews()
        StateHandler.updateDataValues(this::updateDisplayedData)

        stateHandler.grid.forEach { tile ->
            tile?.let {
                addAt(tile.pos, tile.value)
            }
        }
    }

    private fun addRandom() {
        val available = stateHandler.grid.availablePositions()
        if (available.isEmpty()) {
            stateHandler.over = true
            stateHandler.won = false
            stateHandler.continuingGame = false
            dialogGameOver()
        } else {
            val newPos = available.removeAt((0 until available.size).random())
            addAt(newPos)
        }
    }

    private fun addAt(position: Pos, value: Int = if ((0..9).random() < 9) 2 else 4) {
        stateHandler.grid[position] = Tile(position, value)

        val tile = createTileTextView(value)

        val id = tile.id
        stateHandler.grid[position]?.textView = tile

        val constraintSet = ConstraintSet()
        with(constraintSet) {
            applyDefaultConstraints(this, id)
            constrainToTarget(this, id, position)
            applyTo(binding.gameContainer)
        }
    }

    private fun applyDefaultConstraints(constraintSet: ConstraintSet, id: Int) {
        constraintSet.constrainHeight(id, 0)
        constraintSet.constrainWidth(id, 0)
        constraintSet.setDimensionRatio(id, "1:1")
    }

    private fun createTileTextView(tileNum: Int): TextView {
        val tile = layoutInflater.inflate(R.layout.item_tile, null) as TextView
        tile.id = View.generateViewId()

        with(tile) {
            text = tileNum.toString()
            if (tileNum < 8) {
                setTextColor(textBrown)
            } else {
                setTextColor(textOffWhite)
            }

            val tileBackgroundColor = when (tileNum) {
                2 -> resources.getColor(R.color.tile2, null)
                4 -> resources.getColor(R.color.tile4, null)
                8 -> resources.getColor(R.color.tile8, null)
                16 -> resources.getColor(R.color.tile16, null)
                32 -> resources.getColor(R.color.tile32, null)
                64 -> resources.getColor(R.color.tile64, null)
                128 -> resources.getColor(R.color.tile128, null)
                256 -> resources.getColor(R.color.tile256, null)
                512 -> resources.getColor(R.color.tile512, null)
                1024 -> resources.getColor(R.color.tile1024, null)
                2048 -> resources.getColor(R.color.tile2048, null)
                else -> resources.getColor(R.color.tileSuper, null)
            }
            background.mutate().setTint(tileBackgroundColor)

            textSize = when (tileNum.length()) {
                1, 2 -> 40f
                3 -> 30f
                4 -> 24f
                5 -> 18f
                //if you get bigger than this, congrats, you broke it and you definitely cheated
                else -> 12f
            }
            binding.gameContainer.addView(this)
        }
        return tile
    }

    private fun getTargetId(p: Pos): Int {
        val gridLocIdName = "tile_${p.y}_${p.x}"
        return resources.getIdentifier(gridLocIdName, "id", activity?.packageName)
    }

    private fun constrainToTarget(constraintSet: ConstraintSet, sourceId: Int, pos: Pos) {
        constrainToTarget(constraintSet, sourceId, getTargetId(pos))
    }

    private fun constrainToTarget(constraintSet: ConstraintSet, sourceId: Int, targetId: Int) {
        constraintSet.connect(sourceId, ConstraintSet.LEFT, targetId, ConstraintSet.LEFT, margin)
        constraintSet.connect(sourceId, ConstraintSet.RIGHT, targetId, ConstraintSet.RIGHT, margin)
        constraintSet.connect(sourceId, ConstraintSet.TOP, targetId, ConstraintSet.TOP, margin)
        constraintSet.connect(
            sourceId,
            ConstraintSet.BOTTOM,
            targetId,
            ConstraintSet.BOTTOM,
            margin
        )
    }

    private fun getVector(direction: Int): Pair<Int, Int> {
        return when (direction) {
            0 -> Pair(0, -1)// Up
            1 -> Pair(1, 0) // Right
            2 -> Pair(0, 1) // Down
            3 -> Pair(-1, 0)// Left
            else -> throw IllegalArgumentException()
        }
    }

    private fun prepareTiles() {
        stateHandler.grid.forEach { tile ->
            tile?.let {
                tile.mergedFrom = null
                tile.savePosition()
            }
        }
    }

    private fun moveTile(tile: Tile, pos: Pos) {
        stateHandler.grid[tile.pos] = null
        stateHandler.grid[pos] = tile
        tile.updatePosition(pos)
    }

    internal fun move(direction: Int) {
        if (isGameTerminated()) return

        stateHandler.previousGridState = stateHandler.currentGridState.copy()

        stateHandler.grid = stateHandler.currentGridState.grid

        val vector = getVector(direction)
        val traversals = buildTraversals(vector)
        var moved = false

        prepareTiles()

        for (i in traversals.first) {
            for (j in traversals.second) {
                val pos = Pos(i, j)
                val tile = stateHandler.grid[pos]

                tile?.let {
                    val positions = getMaxShift(vector, pos)
//                    Log.v(TAG(this), "max pos if merge: ${positions.first} else: ${positions.second}")
                    val next = stateHandler.grid[positions.second]

                    //Merge tiles; only 1 merger per row traversal
                    if (next != null && next.value == tile.value && next.mergedFrom == null) {
                        val merged = Tile(positions.second, tile.value * 2)
                        merged.mergedFrom = Pair(tile, next)


                        stateHandler.grid[merged.pos] = merged
                        stateHandler.grid[tile.pos] = null

                        //Converge the two tiles' positions
                        tile.updatePosition(positions.second)

                        tilesToRemove.add(tile)
                        tilesToRemove.add(next)

                        StateHandler.score += merged.value

                        //Win condition
                        if (merged.value == 2048) {
                            stateHandler.won = true
                        }
                    } else {
                        moveTile(tile, positions.first)
                    }

                    if (pos != tile.pos) {
                        moved = true
                    }
                }
            }
        }

        if (moved) {
            onMove()
        }
    }

    private fun buildTraversals(vector: Pair<Int, Int>): Pair<ArrayList<Int>, ArrayList<Int>> {
        val x = ArrayList<Int>()
        val y = ArrayList<Int>()

        for (i in 0 until stateHandler.grid.size) {
            x.add(i)
            y.add(i)
        }

        if (vector.first == 1) x.reverse()
        if (vector.second == 1) y.reverse()

        return Pair(x, y)
    }

    private fun getMaxShift(vector: Pair<Int, Int>, pos: Pos): Pair<Pos, Pos> {
        var previous: Pos
        var p = pos
        do {
            previous = p
            p = previous + vector
        } while (stateHandler.grid.withinBounds(p) && stateHandler.grid.isPosAvailable(p))

        return Pair(previous, p)
    }

    private fun onMove() {
        val transition = AutoTransition()
        transition.duration = 100
        val constraintSet = ConstraintSet()

        stateHandler.grid.forEach { tile ->
            tile?.let {
                //Update moved tiles
                if (tile.previousPos != tile.pos) {
                    var textView = tile.textView

                    //Add combined tile
                    if (tile.mergedFrom != null) {
                        textView = createTileTextView(tile.value)
                        applyDefaultConstraints(constraintSet, textView.id)
                        val pop = AnimationUtils.loadAnimation(requireContext(), R.anim.pop)
                        textView.startAnimation(pop)
                    }
                    textView?.let { constrainToTarget(constraintSet, textView.id, tile.pos) }
                    tile.textView = textView
                }
            }
        }

        tilesToRemove.forEach { tile ->
            constrainToTarget(constraintSet, tile.textView!!.id, tile.pos)
        }

        TransitionManager.beginDelayedTransition(binding.gameContainer, transition)
        constraintSet.applyTo(binding.gameContainer)

        tilesToRemove.forEach {
            binding.gameContainer.removeView(it.textView)
        }

        tilesToRemove.clear()

        stateHandler.moveCount++
        StateHandler.updateDataValues(this::updateDisplayedData)

        addRandom()

        if (!movesAvailable()) {
            stateHandler.over = true
        }

        stateHandler.updateState()
        StateHandler.saveState(requireContext())

        if (stateHandler.won && !stateHandler.continuingGame) {
            dialogContinue()
        } else if (stateHandler.over) {
            dialogGameOver()
        }
    }

    private fun movesAvailable(): Boolean {
        return stateHandler.grid.arePositionsAvailable() || tileMatchesAvailable()
    }

    private fun tileMatchesAvailable(): Boolean {
        for (i in 0 until stateHandler.grid.size) {
            for (j in 0 until stateHandler.grid.size) {
                val pos = Pos(i, j)
                val tile = stateHandler.grid[pos]
                tile?.let {
                    for (direction in 0 until 4) {
                        val vector = getVector(direction)
                        val otherPos = pos + vector
                        val other = stateHandler.grid[otherPos]

                        if (other != null && other.value == tile.value) {
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    private fun isGameTerminated(): Boolean {
        return stateHandler.over || (stateHandler.won && !stateHandler.continuingGame)
    }

    private fun newGame() {
        stateHandler.grid.forEach { tile ->
            tile?.let {
                tilesToRemove.add(tile)
            }
        }
        tilesToRemove.forEach {
            binding.gameContainer.removeView(it.textView)
        }

        tilesToRemove.clear()

        clearViews()

        stateHandler.newGame {
            addStartingTiles(2)

            StateHandler.updateDataValues(this::updateDisplayedData)

            stateHandler.updateState()
            StateHandler.saveState(requireContext())
        }
    }

    private fun addStartingTiles(startTiles: Int) {
        repeat(startTiles) {
            addRandom()
        }
    }

    private fun clearViews() {
        stateHandler.grid.forEach { tile ->
            tile?.let {
                tile.textView?.let { binding.gameContainer.removeView(it) }
            }
        }
    }
}

//returns the number of digits
fun Int.length(): Int {
    var copy = this
    var count = 0
    while (copy != 0) {
        copy /= 10
        count++
    }
    return count
}