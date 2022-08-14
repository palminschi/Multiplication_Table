package com.palmdev.learn_math.presentation.screens.games.game_catch

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentGameCatchBinding
import com.palmdev.learn_math.presentation.animations.ClickAnim
import com.palmdev.learn_math.presentation.animations.FallAnim
import com.palmdev.learn_math.presentation.animations.ShakingAnim
import com.palmdev.learn_math.utils.ARG_BEST_SCORE
import com.palmdev.learn_math.utils.ARG_CONTINUE_WITH_SCORE
import com.palmdev.learn_math.utils.Sounds
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameCatchFragment : Fragment() {

    private val viewModel: GameCatchViewModel by viewModel()
    private lateinit var binding: FragmentGameCatchBinding
    private var countDownTimer: CountDownTimer? = null
    private var minNumber = 0
    private var maxNumber = 2
    private var correctAnswers = 0
    private var correctAnswerPosition = 1
    private var timeForAnswer: Long = 5000
    private val sounds by lazy { Sounds(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameCatchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.navigationBarColor = resources.getColor(R.color.black, null)
        init()
        initCountDownTimer()
        initCustomOnBackPressed()
    }

    private fun init() {
        getExercise()
        viewModel.exercise.observe(viewLifecycleOwner) {
            correctAnswerPosition = it.correctAnswerPosition
            binding.tvCondition.text = it.condition
            binding.option1.text = it.choice_1.toString()
            binding.option2.text = it.choice_2.toString()
        }
        binding.option1.setOnClickListener {
            if (correctAnswerPosition == 1) answeredCorrectly(it)
            else answeredWrongly(it)
        }
        binding.option2.setOnClickListener {
            if (correctAnswerPosition == 2) answeredCorrectly(it)
            else answeredWrongly(it)
        }
    }

    private fun getExercise() {
        viewModel.getExercise(minNumber, maxNumber)
        updateCountDownTimer()
        FallAnim.anim(binding.buttons, durationInMillis = timeForAnswer)
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
        finishGame()
    }

    private fun initCountDownTimer() {
        val timerInterval: Long = 1000
        countDownTimer = object : CountDownTimer(timeForAnswer, timerInterval) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                finishGame()
            }
        }
        countDownTimer?.start()
    }

    private fun updateCountDownTimer() {
        countDownTimer?.cancel()
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
            R.id.action_gameCatchFragment_to_continueDialogFragment,
            bundleOf(
                ARG_CONTINUE_WITH_SCORE to correctAnswers,
                ARG_BEST_SCORE to viewModel.bestScore.value
            )
        )
    }

    private fun initCustomOnBackPressed() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
                viewModel.showInterstitialAd()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    override fun onPause() {
        super.onPause()
        countDownTimer?.cancel()
        viewModel.saveResults(correctAnswers = correctAnswers)
        activity?.window?.navigationBarColor = resources.getColor(R.color.background, null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
        countDownTimer = null
    }
}