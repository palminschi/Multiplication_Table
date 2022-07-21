package com.palmdev.learn_math.presentation.screens.exercise_input

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentExerciseInputBinding
import com.palmdev.learn_math.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

class ExerciseInputFragment : Fragment() {

    private val viewModel: ExerciseInputViewModel by viewModel()
    private lateinit var binding: FragmentExerciseInputBinding
    private var minNumber = 0
    private var maxNumber = 10
    private var withNumber = 0
    private var operation = Operation.MULTIPLICATION
    private var examOrTraining = TRAINING
    private var progressCounter = 0
    private var correctAnswers = 0
    private var correctAnswer = 0
    private var wrongAnswers = 0
    private val userInput = MutableLiveData<String>()
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
    private val keyboardButtons by lazy {
        listOf(
            binding.btnNumber0, binding.btnNumber1, binding.btnNumber2, binding.btnNumber3,
            binding.btnNumber4, binding.btnNumber5, binding.btnNumber6, binding.btnNumber7,
            binding.btnNumber8, binding.btnNumber9, binding.btnRemove
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciseInputBinding.inflate(layoutInflater, container, false)
        minNumber = arguments?.getInt(ARG_MIN_NUMBER, 0) ?: 0
        maxNumber = arguments?.getInt(ARG_MAX_NUMBER, 10) ?: 10
        arguments?.getSerializable(ARG_OPERATION)?.let { operation = it as Operation }
        examOrTraining = arguments?.getString(ARG_EXAM_OR_TRAINING, TRAINING) ?: TRAINING
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        getNewExercise()
        viewModel.exercise.observe(viewLifecycleOwner) {
            binding.tvCondition.text = it.condition
            binding.tvCorrectAnswer.text = it.answer.toString()
            correctAnswer = it.answer
        }
        userInput.observe(viewLifecycleOwner) {
            binding.tvAnswer.text = it
            if (correctAnswer.toString().length == it.length) checkAnswer()
        }
        initButtons()
        progressViews.forEach { it.setBackgroundColor(resources.getColor(R.color.gray, null)) }
        binding.tvCorrectAnswers.text = correctAnswers.toString()
        binding.tvWrongAnswers.text = wrongAnswers.toString()
    }

    private fun getNewExercise() {
        answerStartTime = System.currentTimeMillis()
        withNumber = Random(System.currentTimeMillis()).nextInt(minNumber, maxNumber + 1)
        when (operation) {
            Operation.MULTIPLICATION ->
                viewModel.getMultiplicationExercise(withNumber, minNumber, maxNumber)
            Operation.DIVISION ->
                viewModel.getDivisionExercise(withNumber, minNumber, maxNumber)
            // TODO: Plus and minus
            Operation.ADDITION ->
                viewModel.getMultiplicationExercise(withNumber, minNumber, maxNumber)
            Operation.SUBTRACTION ->
                viewModel.getDivisionExercise(withNumber, minNumber, maxNumber)
        }
        binding.tvCorrectAnswer.visibility = View.INVISIBLE
        userInput.value = ""
        keyboardButtons.forEach { it.isClickable = true }
        binding.tvAnswer.setTextColor(resources.getColor(R.color.white, null))
    }

    private fun checkAnswer() {
        if (correctAnswer == binding.tvAnswer.text.toString().toInt()) answeredCorrectly()
        else answeredWrongly()
        progressCounter++
        setAnswerTime()
    }

    private fun answeredCorrectly() {
        binding.tvAnswer.setTextColor(resources.getColor(R.color.green, null))
        progressViews[progressCounter].setBackgroundColor(resources.getColor(R.color.green, null))
        correctAnswers++
        binding.tvCorrectAnswers.text = correctAnswers.toString()
        keyboardButtons.forEach { it.isClickable = false }
        handler.postDelayed({
            if (progressCounter == 10) {
                finishExercise()
                return@postDelayed
            }
            getNewExercise()
        }, 500)
    }

    private fun answeredWrongly() {
        binding.tvCorrectAnswer.visibility = View.VISIBLE
        binding.tvAnswer.setTextColor(resources.getColor(R.color.red, null))
        progressViews[progressCounter].setBackgroundColor(resources.getColor(R.color.red, null))
        wrongAnswers++
        binding.tvWrongAnswers.text = wrongAnswers.toString()
        keyboardButtons.forEach { it.isClickable = false }
        handler.postDelayed({
            if (progressCounter == 10) {
                finishExercise()
                return@postDelayed
            }
            getNewExercise()
        }, 1500)
    }

    private fun initButtons() {
        binding.btnRemove.setOnClickListener {
            userInput.value = userInput.value?.dropLast(1)
        }
        binding.btnNumber1.setOnClickListener {
            userInput.value = userInput.value + 1
        }
        binding.btnNumber2.setOnClickListener {
            userInput.value = userInput.value + 2
        }
        binding.btnNumber3.setOnClickListener {
            userInput.value = userInput.value + 3
        }
        binding.btnNumber4.setOnClickListener {
            userInput.value = userInput.value + 4
        }
        binding.btnNumber5.setOnClickListener {
            userInput.value = userInput.value + 5
        }
        binding.btnNumber6.setOnClickListener {
            userInput.value = userInput.value + 6
        }
        binding.btnNumber7.setOnClickListener {
            userInput.value = userInput.value + 7
        }
        binding.btnNumber8.setOnClickListener {
            userInput.value = userInput.value + 8
        }
        binding.btnNumber9.setOnClickListener {
            userInput.value = userInput.value + 9
        }
        binding.btnNumber0.setOnClickListener {
            userInput.value = userInput.value + 0
        }
    }

    private fun setAnswerTime() {
        answerEndTime = System.currentTimeMillis()
        val exerciseTime = answerEndTime - answerStartTime
        listAnswersTime.add(exerciseTime)
        avgAnswerTime = listAnswersTime.average() / 1000.0
    }

    private fun finishExercise() {
            findNavController().navigate(
                R.id.action_exerciseInputFragment_to_endFragment,
                bundleOf(
                    ARG_OPERATION to operation,
                    ARG_RIGHT_ANSWERS to correctAnswers,
                    ARG_WRONG_ANSWERS to wrongAnswers,
                    ARG_AVG_TIME to avgAnswerTime,
                    ARG_EXAM_OR_TRAINING to examOrTraining
                )
            )
    }
}