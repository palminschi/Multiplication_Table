package com.palmdev.learn_math.presentation.screens.games.game_hard_math

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentGameHardMathBinding
import com.palmdev.learn_math.presentation.animations.ClickAnim
import com.palmdev.learn_math.presentation.animations.ShakingAnim
import com.palmdev.learn_math.utils.ARG_BEST_SCORE
import com.palmdev.learn_math.utils.ARG_CONTINUE_WITH_SCORE
import com.palmdev.learn_math.utils.Sounds
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameHardMathFragment : Fragment() {

    private val viewModel: GameHardMathViewModel by viewModel()
    private lateinit var binding: FragmentGameHardMathBinding
    private var minNumber = 0
    private var maxNumber = 2
    private var correctAnswerPosition = 0
    private var correctAnswer = 0
    private var correctAnswers = 0
    private val handler by lazy { Handler(Looper.getMainLooper()) }
    private val sounds by lazy { Sounds(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameHardMathBinding.inflate(layoutInflater, container, false)
        correctAnswers = arguments?.getInt(ARG_CONTINUE_WITH_SCORE, 0) ?: 0
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        getNewExercise()
        viewModel.exercise.observe(viewLifecycleOwner) {
            correctAnswerPosition = it.correctAnswerPosition
            binding.tvCondition.text = it.condition
            correctAnswer = it.correctAnswer
            binding.option1.text = it.choice_1.toString()
            binding.option2.text = it.choice_2.toString()
            binding.option3.text = it.choice_3.toString()
            binding.option4.text = it.choice_4.toString()
        }
        binding.option1.setOnClickListener {
            if (correctAnswerPosition == 1) answeredCorrectly(it)
            else answeredWrongly(it)
        }
        binding.option2.setOnClickListener {
            if (correctAnswerPosition == 2) answeredCorrectly(it)
            else answeredWrongly(it)
        }
        binding.option3.setOnClickListener {
            if (correctAnswerPosition == 3) answeredCorrectly(it)
            else answeredWrongly(it)
        }
        binding.option4.setOnClickListener {
            if (correctAnswerPosition == 4) answeredCorrectly(it)
            else answeredWrongly(it)
        }
    }

    private fun updateViews() {
        binding.tvCorrectAnswers.text = correctAnswers.toString()
        binding.tvCorrectAnswer.text = getText(R.string.questionMark)
        binding.tvCorrectAnswer.setTextColor(resources.getColor(R.color.white, null))
        binding.option1.setBackgroundColor(resources.getColor(R.color.second_background, null))
        binding.option2.setBackgroundColor(resources.getColor(R.color.second_background, null))
        binding.option3.setBackgroundColor(resources.getColor(R.color.second_background, null))
        binding.option4.setBackgroundColor(resources.getColor(R.color.second_background, null))
        binding.option1.isClickable = true
        binding.option2.isClickable = true
        binding.option3.isClickable = true
        binding.option4.isClickable = true
    }

    private fun getNewExercise() {
        viewModel.getExercise(minNumber, maxNumber)
        updateViews()
    }

    private fun answeredCorrectly(view: View) {
        ClickAnim.anim(view)
        sounds.playClick()
        correctAnswers++
        maxNumber++
        binding.tvCorrectAnswers.text = correctAnswers.toString()
        view.setBackgroundColor(resources.getColor(R.color.green, null))
        binding.tvCorrectAnswer.text = correctAnswer.toString()
        binding.tvCorrectAnswer.setTextColor(resources.getColor(R.color.green, null))
        binding.option1.isClickable = false
        binding.option2.isClickable = false
        binding.option3.isClickable = false
        binding.option4.isClickable = false
        handler.postDelayed({
            getNewExercise()
        }, 800)
    }

    private fun answeredWrongly(view: View) {
        ShakingAnim.anim(view)
        sounds.playWrongAnswer()
        view.setBackgroundColor(resources.getColor(R.color.red, null))
        binding.tvCorrectAnswer.text = correctAnswer.toString()
        binding.tvCorrectAnswer.setTextColor(resources.getColor(R.color.green, null))
        binding.option1.isClickable = false
        binding.option2.isClickable = false
        binding.option3.isClickable = false
        binding.option4.isClickable = false
        handler.postDelayed({
            finishExercise()
        }, 2000)
    }

    private fun finishExercise() {
        viewModel.saveResults(correctAnswers = correctAnswers)
        findNavController().navigate(
            R.id.action_gameHardMathFragment_to_continueDialogFragment,
            bundleOf(
                ARG_CONTINUE_WITH_SCORE to correctAnswers,
                ARG_BEST_SCORE to viewModel.bestScore.value
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveResults(correctAnswers = correctAnswers)
    }
}