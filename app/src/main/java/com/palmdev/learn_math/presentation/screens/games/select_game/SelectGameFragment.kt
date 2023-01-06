package com.palmdev.learn_math.presentation.screens.games.select_game

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentSelectGameBinding
import com.palmdev.learn_math.utils.ARG_IS_GAME
import com.palmdev.learn_math.utils.ARG_MAX_NUMBER
import com.palmdev.learn_math.utils.ARG_MIN_NUMBER
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectGameFragment : Fragment() {

    private lateinit var binding: FragmentSelectGameBinding
    private val viewModel: SelectGameViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectGameBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecords()
        initButtons()
    }

    private fun initButtons() {
        binding.btnGame2048.setOnClickListener {
            findNavController().navigate(R.id.game2048Fragment)
        }
        binding.btnGameDuel.setOnClickListener {
            findNavController().navigate(R.id.gameDuelStartFragment)
        }
        binding.btnGame60sec.setOnClickListener {
            findNavController().navigate(
                R.id.game60secFragment,
                bundleOf(
                    ARG_MIN_NUMBER to 0,
                    ARG_MAX_NUMBER to 5,
                    ARG_IS_GAME to true
                )
            )
        }
        binding.btnGameMoreOrLess.setOnClickListener {
            findNavController().navigate(R.id.gameMoreOrLessFragment)
        }
        binding.btnGameHardMath.setOnClickListener {
            findNavController().navigate(R.id.gameHardMathFragment)
        }
        binding.btnGameCatch.setOnClickListener {
            findNavController().navigate(R.id.gameCatchFragment)
        }
    }

    private fun initRecords() {
        viewModel.getRecords()
        viewModel.bestScoreGame60sec.observe(viewLifecycleOwner) {
            setBestScore(view = binding.scoreGame60sec, score = it)
        }
        viewModel.bestScoreGameMoreOrLess.observe(viewLifecycleOwner) {
            setBestScore(view = binding.scoreGameMoreOrLess, score = it)
        }
        viewModel.bestScoreGameHardMath.observe(viewLifecycleOwner) {
            setBestScore(view = binding.scoreGameHardMath, score = it)
        }
        viewModel.bestScoreGameCatch.observe(viewLifecycleOwner) {
            setBestScore(view = binding.scoreGameCatch, score = it)
        }
    }

    private fun setBestScore(view: TextView, score: Int?) {
        view.text =
            if (score != null) "${getText(R.string.best)} $score"
            else "${getText(R.string.best)} ${getText(R.string.questionMark)}"
    }
}