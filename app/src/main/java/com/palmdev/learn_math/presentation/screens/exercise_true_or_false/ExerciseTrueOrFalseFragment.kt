package com.palmdev.learn_math.presentation.screens.exercise_true_or_false

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentExerciseTrueOrFalseBinding
import com.palmdev.learn_math.presentation.animations.ClickAnim
import com.palmdev.learn_math.presentation.animations.ShakingAnim
import com.palmdev.learn_math.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

class ExerciseTrueOrFalseFragment : Fragment() {

    private val viewModel: ExerciseTrueOrFalseViewModel by viewModel()
    private lateinit var binding: FragmentExerciseTrueOrFalseBinding
    private var minNumber = 0
    private var maxNumber = 10
    private var withNumber = 0
    private var operation = Operation.MULTIPLICATION
    private var progressCounter = 0
    private var correctAnswers = 0
    private var wrongAnswers = 0
    private var isTrueOrFalse = true
    private val handler by lazy { Handler(Looper.getMainLooper()) }
    private var answerStartTime: Long = 0
    private var answerEndTime: Long = 0
    private var listAnswersTime = ArrayList<Long>()
    private var avgAnswerTime = 0.0
    private val progressViews by lazy {
        listOf(
            binding.progress1, binding.progress2, binding.progress3, binding.progress4,
            binding.progress5, binding.progress6, binding.progress7, binding.progress8,
            binding.progress9, binding.progress10
        )
    }
    private val sounds by lazy { Sounds(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciseTrueOrFalseBinding.inflate(layoutInflater, container, false)
        minNumber = arguments?.getInt(ARG_MIN_NUMBER, 0) ?: 0
        maxNumber = arguments?.getInt(ARG_MAX_NUMBER, 10) ?: 10
        arguments?.getSerializable(ARG_OPERATION)?.let { operation = it as Operation }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initCustomOnBackPressed()
    }

    private fun init() {
        getNewExercise()
        viewModel.exercise.observe(viewLifecycleOwner) {
            binding.tvCondition.text = it.condition
            binding.tvCondition2.text = it.condition
            isTrueOrFalse = it.isTrueOrFalse
            binding.tvAnswer.text =
                if (isTrueOrFalse) it.correctAnswer.toString()
                else it.wrongAnswer.toString()
            binding.tvCorrectAnswer.text = it.correctAnswer.toString()
        }

        binding.btnTrue.setOnClickListener {
            when (isTrueOrFalse) {
                true -> answeredCorrectly(it)
                false -> answeredWrongly(it)
            }
            progressCounter++
        }
        binding.btnFalse.setOnClickListener {
            when (isTrueOrFalse) {
                true -> answeredWrongly(it)
                false -> answeredCorrectly(it)
            }
            progressCounter++
        }
        progressViews.forEach { it.setBackgroundColor(resources.getColor(R.color.gray, null)) }
        binding.tvWrongAnswers.text = wrongAnswers.toString()
        binding.tvCorrectAnswers.text = correctAnswers.toString()
    }

    private fun getNewExercise() {
        answerStartTime = System.currentTimeMillis()
        withNumber = Random(System.currentTimeMillis()).nextInt(minNumber, maxNumber + 1)
        when (operation) {
            Operation.MULTIPLICATION ->
                viewModel.getMultiplicationExercise(withNumber, minNumber, maxNumber)
            Operation.DIVISION ->
                viewModel.getDivisionExercise(withNumber, minNumber, maxNumber)
            Operation.ADDITION ->
                viewModel.getAdditionExercise(withNumber, minNumber, maxNumber)
            Operation.SUBTRACTION ->
                viewModel.getSubtractionExercise(withNumber, minNumber, maxNumber)
        }
        binding.layoutCorrectAnswer.visibility = View.INVISIBLE
        binding.btnTrue.isClickable = true
        binding.btnFalse.isClickable = true
        initHint()
    }

    private fun answeredCorrectly(view: View) {
        ClickAnim.anim(view)
        sounds.playClick()
        setAnswerTime()
        correctAnswers++
        binding.tvCorrectAnswers.text = correctAnswers.toString()
        progressViews[progressCounter].setBackgroundColor(resources.getColor(R.color.green, null))
        binding.btnTrue.isClickable = false
        binding.btnFalse.isClickable = false
        handler.postDelayed({
            if (progressCounter == 10) {
                finishExercise()
                return@postDelayed
            }
            getNewExercise()
        }, 200)
    }

    private fun answeredWrongly(view: View) {
        ShakingAnim.anim(view)
        sounds.playWrongAnswer()
        setAnswerTime()
        wrongAnswers++
        binding.tvWrongAnswers.text = wrongAnswers.toString()
        progressViews[progressCounter].setBackgroundColor(resources.getColor(R.color.red, null))
        binding.layoutCorrectAnswer.visibility = View.VISIBLE
        binding.btnTrue.isClickable = false
        binding.btnFalse.isClickable = false
        handler.postDelayed({
            if (progressCounter == 10) {
                finishExercise()
                return@postDelayed
            }
            getNewExercise()
        }, 1500)
    }

    private fun setAnswerTime() {
        answerEndTime = System.currentTimeMillis()
        val exerciseTime = answerEndTime - answerStartTime
        listAnswersTime.add(exerciseTime)
        avgAnswerTime = listAnswersTime.average() / 1000.0
    }

    private fun finishExercise() {
        findNavController().navigate(
            R.id.endFragment,
            bundleOf(
                ARG_OPERATION to operation,
                ARG_AVG_TIME to avgAnswerTime,
                ARG_RIGHT_ANSWERS to correctAnswers,
                ARG_WRONG_ANSWERS to wrongAnswers
            )
        )
    }

    private fun initHint() {
        if (withNumber <= 10 &&
            operation == Operation.MULTIPLICATION || operation == Operation.DIVISION
        ) {
            binding.btnHint.visibility = View.VISIBLE
            binding.btnHint.setOnClickListener {
                findNavController().navigate(
                    R.id.hintTableDialogFragment,
                    bundleOf(
                        ARG_WITH_NUMBER to withNumber,
                        ARG_OPERATION to operation
                    )
                )
            }
        } else {
            binding.btnHint.visibility = View.GONE
        }
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
        handler.removeCallbacksAndMessages(null)
    }

}