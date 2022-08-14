package com.palmdev.learn_math.presentation.screens.games.game_duel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentGameDuelEndBinding
import com.palmdev.learn_math.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameDuelEndFragment : Fragment() {

    private val viewModel: GameDuelEndViewModel by viewModel()
    private lateinit var binding: FragmentGameDuelEndBinding
    private var redCorrectAnswers = 0
    private var redWrongAnswers = 0
    private var blueCorrectAnswers = 0
    private var blueWrongAnswers = 0
    private var operation = Operation.MULTIPLICATION
    private var minNumber = 0
    private var maxNumber = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameDuelEndBinding.inflate(layoutInflater, container, false)
        redCorrectAnswers = arguments?.getInt(ARG_RED_CORRECT_ANSWERS) ?: 0
        redWrongAnswers = arguments?.getInt(ARG_RED_WRONG_ANSWERS) ?: 0
        blueCorrectAnswers = arguments?.getInt(ARG_BLUE_CORRECT_ANSWERS) ?: 0
        blueWrongAnswers = arguments?.getInt(ARG_BLUE_WRONG_ANSWERS) ?: 0
        operation = arguments?.getSerializable(ARG_OPERATION) as Operation
        minNumber = arguments?.getInt(ARG_MIN_NUMBER) ?: 0
        maxNumber = arguments?.getInt(ARG_MAX_NUMBER) ?: 10
        initOnBackPressedCallback()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.redCorrectAnswers.text = redCorrectAnswers.toString()
        binding.redWrongAnswers.text = redWrongAnswers.toString()
        binding.blueCorrectAnswers.text = blueCorrectAnswers.toString()
        binding.blueWrongAnswers.text = blueWrongAnswers.toString()

        if (redCorrectAnswers == blueCorrectAnswers) {
            binding.tvWhoWins.text = getText(R.string.bothWon)
            binding.tvWhoWins.setTextColor(resources.getColor(R.color.green, null))
        } else if (redCorrectAnswers > blueCorrectAnswers) {
            binding.tvWhoWins.text = getText(R.string.redWins)
            binding.tvWhoWins.setTextColor(resources.getColor(R.color.red, null))
        } else if (redCorrectAnswers < blueCorrectAnswers) {
            binding.tvWhoWins.text = getText(R.string.blueWins)
            binding.tvWhoWins.setTextColor(resources.getColor(R.color.main_color, null))
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
            findNavController().popBackStack()
            findNavController().popBackStack()
            viewModel.showInterstitialAd()
        }
        binding.btnAgain.setOnClickListener {
            findNavController().popBackStack()
            findNavController().popBackStack()
            findNavController().navigate(
                R.id.action_gameDuelStartFragment_to_gameDuelFragment,
                bundleOf(
                    ARG_OPERATION to operation,
                    ARG_MIN_NUMBER to minNumber,
                    ARG_MAX_NUMBER to maxNumber
                )
            )
        }
    }

    private fun initOnBackPressedCallback(){
        val customOnBackPressed = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
                findNavController().popBackStack()
                viewModel.showInterstitialAd()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, customOnBackPressed)
    }
}