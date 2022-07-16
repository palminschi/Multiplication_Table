package com.palmdev.learn_math.presentation.screens.exercise_select

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentExerciseSelectBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExerciseSelectFragment : Fragment() {

    private lateinit var binding: FragmentExerciseSelectBinding
    private val viewModel by viewModel<ExerciseSelectViewModel>()
    private var withNumber = 0
    private var minNumber = 0
    private var maxNumber = 10
    private var type = Type.MULTIPLICATION
    private var correctAnswerPosition = 0
    private var correctAnswer = 0
    private var progressCounter = 0
    private val progressViews by lazy {
        listOf(
            binding.progress1, binding.progress2, binding.progress3, binding.progress4,
            binding.progress5, binding.progress6, binding.progress7, binding.progress8,
            binding.progress9, binding.progress10
        )
    }
    private var correctAnswers = 0
    private var wrongAnswers = 0

    enum class Type { MULTIPLICATION, DIVISION }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciseSelectBinding.inflate(layoutInflater, container, false)
        arguments?.getInt(ARG_WITH_NUMBER)?.let { withNumber = it }
        arguments?.getSerializable(ARG_TYPE)?.let { type = it as Type }
        minNumber = arguments?.getInt(ARG_MIN_NUMBER, 0) ?: 0
        maxNumber = arguments?.getInt(ARG_MAX_NUMBER, 10) ?: 10
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        when (type) {
            Type.MULTIPLICATION ->
                viewModel.getMultiplicationExercise(withNumber, minNumber, maxNumber)
            Type.DIVISION ->
                viewModel.getDivisionExercise(withNumber, minNumber, maxNumber)
        }
        updateViews()
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
            if (correctAnswerPosition == 1) answeredRight(it)
            else answeredWrong(it)
        }
        binding.option2.setOnClickListener {
            if (correctAnswerPosition == 2) answeredRight(it)
            else answeredWrong(it)
        }
        binding.option3.setOnClickListener {
            if (correctAnswerPosition == 3) answeredRight(it)
            else answeredWrong(it)
        }
        binding.option4.setOnClickListener {
            if (correctAnswerPosition == 4) answeredRight(it)
            else answeredWrong(it)
        }
        progressViews.forEach {
            it.setBackgroundColor(resources.getColor(R.color.gray_transparent, null))
        }
    }

    private fun updateViews() {
        binding.tvCorrectAnswers.text = correctAnswers.toString()
        binding.tvWrongAnswers.text = wrongAnswers.toString()
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
        when (type) {
            Type.MULTIPLICATION ->
                viewModel.getMultiplicationExercise(withNumber, minNumber, maxNumber)
            Type.DIVISION ->
                viewModel.getDivisionExercise(withNumber, minNumber, maxNumber)
        }
        updateViews()
        progressCounter++
    }

    private fun answeredRight(view: View) {
        correctAnswers++
        binding.tvCorrectAnswers.text = correctAnswers.toString()
        view.setBackgroundColor(resources.getColor(R.color.green, null))
        progressViews[progressCounter].setBackgroundColor(resources.getColor(R.color.green, null))
        binding.tvCorrectAnswer.text = correctAnswer.toString()
        binding.tvCorrectAnswer.setTextColor(resources.getColor(R.color.green, null))
        binding.option1.isClickable = false
        binding.option2.isClickable = false
        binding.option3.isClickable = false
        binding.option4.isClickable = false
        Handler(Looper.getMainLooper()).postDelayed({
            if (progressCounter == 9) finishExercise()
            else getNewExercise()
        }, 2000)
    }

    private fun answeredWrong(view: View) {
        wrongAnswers++
        binding.tvWrongAnswers.text = wrongAnswers.toString()
        view.setBackgroundColor(resources.getColor(R.color.red, null))
        progressViews[progressCounter].setBackgroundColor(resources.getColor(R.color.red, null))
        binding.tvCorrectAnswer.text = correctAnswer.toString()
        binding.tvCorrectAnswer.setTextColor(resources.getColor(R.color.green, null))
        binding.option1.isClickable = false
        binding.option2.isClickable = false
        binding.option3.isClickable = false
        binding.option4.isClickable = false
        Handler(Looper.getMainLooper()).postDelayed({
            if (progressCounter == 9) finishExercise()
            else getNewExercise()
        }, 2000)
    }

    private fun finishExercise() {
        findNavController().navigate(R.id.action_exerciseSelectFragment_to_endFragment) // TODO: Bundle
    }

    companion object {
        const val ARG_WITH_NUMBER = "ARG_WITH_NUMBER"
        const val ARG_MIN_NUMBER = "ARG_MIN_NUMBER"
        const val ARG_MAX_NUMBER = "ARG_MAX_NUMBER"
        const val ARG_TYPE = "ARG_TYPE"
    }
}