package com.palmdev.learn_math.presentation.screens.games.game_duel

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentGameDuelEndBinding
import com.palmdev.learn_math.utils.ARG_BLUE_CORRECT_ANSWERS
import com.palmdev.learn_math.utils.ARG_BLUE_WRONG_ANSWERS
import com.palmdev.learn_math.utils.ARG_RED_CORRECT_ANSWERS
import com.palmdev.learn_math.utils.ARG_RED_WRONG_ANSWERS

class GameDuelEndFragment : Fragment() {

    private lateinit var binding: FragmentGameDuelEndBinding
    private var redCorrectAnswers = 0
    private var redWrongAnswers = 0
    private var blueCorrectAnswers = 0
    private var blueWrongAnswers = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameDuelEndBinding.inflate(layoutInflater, container, false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        redCorrectAnswers = arguments?.getInt(ARG_RED_CORRECT_ANSWERS) ?: 0
        redWrongAnswers = arguments?.getInt(ARG_RED_WRONG_ANSWERS) ?: 0
        blueCorrectAnswers = arguments?.getInt(ARG_BLUE_CORRECT_ANSWERS) ?: 0
        blueWrongAnswers = arguments?.getInt(ARG_BLUE_WRONG_ANSWERS) ?: 0
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
            binding.tvWhoWins.setTextColor(resources.getColor(R.color.second_background, null))
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
            findNavController().popBackStack()
            findNavController().popBackStack()
        }
        binding.btnAgain.setOnClickListener {
            findNavController().popBackStack()
            findNavController().popBackStack()
        }
    }

    private fun initOnBackPressedCallback(){
        val customOnBackPressed = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
                findNavController().popBackStack()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, customOnBackPressed)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}