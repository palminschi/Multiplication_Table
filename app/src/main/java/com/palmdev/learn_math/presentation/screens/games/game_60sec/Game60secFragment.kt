package com.palmdev.learn_math.presentation.screens.games.game_60sec

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
import com.palmdev.learn_math.databinding.FragmentGame60secBinding
import com.palmdev.learn_math.presentation.animations.ClickAnim
import com.palmdev.learn_math.presentation.animations.ShakingAnim
import com.palmdev.learn_math.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class Game60secFragment : Fragment() {

    private val viewModel: Game60secViewModel by viewModel()
    private lateinit var binding: FragmentGame60secBinding
    private var minNumber = 0
    private var maxNumber = 10
    private var operation: Operation? = null
    private var correctAnswers = 0
    private var wrongAnswers = 0
    private var correctAnswerPosition = 0
    private var correctAnswer = 0
    private val sounds by lazy { Sounds(requireContext()) }
    private var isGame = false
    private var countDownTimer: CountDownTimer? = null
    private var timerTimeRemaining: Long = 60000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGame60secBinding.inflate(layoutInflater, container, false)
        minNumber = arguments?.getInt(ARG_MIN_NUMBER) ?: 0
        maxNumber = arguments?.getInt(ARG_MAX_NUMBER) ?: 10
        arguments?.getSerializable(ARG_OPERATION)?.let {
            operation = it as Operation
        }
        isGame = arguments?.getBoolean(ARG_IS_GAME) ?: false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initCustomOnBackPressed()
    }

    override fun onResume() {
        super.onResume()
        initCountDownTimer()
    }

    override fun onPause() {
        super.onPause()
        countDownTimer?.cancel()
    }

    private fun init() {
        binding.tvCorrectAnswer.text = getText(R.string.questionMark)
        binding.tvCorrectAnswers.text = correctAnswers.toString()
        binding.tvWrongAnswers.text = wrongAnswers.toString()
        viewModel.getExercise(minNumber, maxNumber, operation)
        viewModel.exercise.observe(viewLifecycleOwner) {
            binding.tvCondition.text = it.condition
            correctAnswer = it.correctAnswer
            correctAnswerPosition = it.correctAnswerPosition
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

    private fun initCountDownTimer() {
        val timerInterval: Long = 1000
        countDownTimer = object : CountDownTimer(timerTimeRemaining, timerInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timerTimeRemaining -= timerInterval
                binding.timeProgress.progress = (timerTimeRemaining / 1000).toInt()
                binding.tvSecondsRemaining.text = (timerTimeRemaining / 1000).toString()
            }

            override fun onFinish() {
                finishGame()
            }
        }
        countDownTimer?.start()
    }

    private fun answeredCorrectly(view: View) {
        ClickAnim.anim(view)
        sounds.playClick()
        correctAnswers++
        binding.tvCorrectAnswers.text = correctAnswers.toString()
        ClickAnim.anim(binding.tvCorrectAnswers)
        if (isGame) {
            maxNumber++
        }
        getNewExercise()
    }

    private fun answeredWrongly(view: View) {
        ShakingAnim.anim(view)
        sounds.playWrongAnswer()
        wrongAnswers++
        binding.tvWrongAnswers.text = wrongAnswers.toString()
        ClickAnim.anim(binding.tvWrongAnswers)
        if (isGame && maxNumber > 1) {
            maxNumber--
        }
        getNewExercise()
    }

    private fun getNewExercise() {
        viewModel.getExercise(minNumber, maxNumber, operation)
    }

    private fun finishGame() {
        findNavController().navigate(
            R.id.game60secEndFragment,
            bundleOf(
                ARG_MIN_NUMBER to minNumber,
                ARG_MAX_NUMBER to maxNumber,
                ARG_OPERATION to operation,
                ARG_RIGHT_ANSWERS to correctAnswers,
                ARG_WRONG_ANSWERS to wrongAnswers,
                ARG_IS_GAME to isGame
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

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
        countDownTimer = null
    }

}