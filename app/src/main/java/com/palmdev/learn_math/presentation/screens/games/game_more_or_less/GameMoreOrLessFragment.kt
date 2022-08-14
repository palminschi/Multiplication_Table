package com.palmdev.learn_math.presentation.screens.games.game_more_or_less

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentGameMoreOrLessBinding
import com.palmdev.learn_math.presentation.animations.ClickAnim
import com.palmdev.learn_math.presentation.animations.ShakingAnim
import com.palmdev.learn_math.utils.ARG_BEST_SCORE
import com.palmdev.learn_math.utils.ARG_CONTINUE_WITH_SCORE
import com.palmdev.learn_math.utils.Sounds
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameMoreOrLessFragment : Fragment() {

    private val viewModel: GameMoreOrLessViewModel by viewModel()
    private lateinit var binding: FragmentGameMoreOrLessBinding
    private var countDownTimer: CountDownTimer? = null
    private var minNumber = 0
    private var maxNumber = 6
    private var isTrue = false
    private var correctAnswers = 0
    private var wrongAnswers = 0
    private var timerTimeRemaining: Long = 20000
    private val sounds by lazy { Sounds(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameMoreOrLessBinding.inflate(layoutInflater, container, false)
        correctAnswers = arguments?.getInt(ARG_CONTINUE_WITH_SCORE, 0) ?: 0
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        initCountDownTimer()
    }

    override fun onPause() {
        super.onPause()
        countDownTimer?.cancel()
        viewModel.saveResults(correctAnswers = correctAnswers)
    }

    private fun init() {
        getExercise()
        viewModel.exercise.observe(viewLifecycleOwner) {
            binding.tvCondition.text = it.condition
            isTrue = it.isTrue
        }
        binding.btnTrue.setOnClickListener {
            if (isTrue) answeredCorrectly(it)
            else answeredWrongly(it)
        }
        binding.btnFalse.setOnClickListener {
            if (!isTrue) answeredCorrectly(it)
            else answeredWrongly(it)
        }
    }

    private fun getExercise() {
        viewModel.getExercise(minNumber, maxNumber)
        updateCountDownTimer()
    }

    private fun answeredCorrectly(view: View) {
        sounds.playClick()
        correctAnswers++
        maxNumber++
        getExercise()
        ClickAnim.anim(view)
    }

    private fun answeredWrongly(view: View) {
        sounds.playWrongAnswer()
        ShakingAnim.anim(view)
        wrongAnswers++
        finishGame()
    }

    private fun initCountDownTimer() {
        val timerInterval: Long = 1000
        countDownTimer = object : CountDownTimer(timerTimeRemaining, timerInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timerTimeRemaining -= timerInterval
                binding.timeProgress.progress = ((timerTimeRemaining / 1000).toInt())
                binding.tvSecondsRemaining.text = (timerTimeRemaining / 1000).toString()
            }

            override fun onFinish() {
                finishGame()
            }
        }
        countDownTimer?.start()
    }

    private fun updateCountDownTimer() {
        countDownTimer?.cancel()
        timerTimeRemaining = 20000
        countDownTimer?.start()
    }


    private fun finishGame() {
        countDownTimer?.cancel()
        countDownTimer = null
        viewModel.saveResults(correctAnswers = correctAnswers)
        showDialog()
    }

    private fun showDialog() {
        findNavController().navigate(
            R.id.action_gameMoreOrLessFragment_to_continueDialogFragment,
            bundleOf(
                ARG_CONTINUE_WITH_SCORE to correctAnswers,
                ARG_BEST_SCORE to viewModel.bestScore.value
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
        countDownTimer = null
    }

}