package com.palmdev.learn_math.presentation.screens.games.game_duel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.data.model.ExerciseSelect
import com.palmdev.learn_math.databinding.FragmentGameDuelBinding
import com.palmdev.learn_math.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameDuelFragment : Fragment() {

    private val viewModel by viewModel<GameDuelViewModel>()
    private lateinit var binding: FragmentGameDuelBinding
    private var operation = Operation.MULTIPLICATION
    private var minNumber = 0
    private var maxNumber = 10
    private var exercises = emptyList<ExerciseSelect>()
    private var correctAnswerPositionTop = 0
    private var correctAnswerPositionBottom = 0
    private var correctNumberTop = 0
    private var correctNumberBottom = 0
    private var progressCounterTop = 0
    private var progressCounterBottom = 0
    private var correctAnswersTop = 0
    private var correctAnswersBottom = 0
    private var wrongAnswersTop = 0
    private var wrongAnswersBottom = 0
    private var topUserFinished = false
    private var bottomUserFinished = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameDuelBinding.inflate(layoutInflater, container, false)
        operation = arguments?.getSerializable(ARG_OPERATION) as Operation
        minNumber = arguments?.getInt(ARG_MIN_NUMBER) ?: 0
        maxNumber = arguments?.getInt(ARG_MAX_NUMBER) ?: 10
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewModel.exercises.observe(viewLifecycleOwner) {
            exercises = it
            setNewExerciseBottom()
            setNewExerciseTop()
        }
        when (operation) {
            Operation.MULTIPLICATION -> viewModel.getMultiplicationExercise(minNumber, maxNumber)
            Operation.DIVISION -> viewModel.getDivisionExercise(minNumber, maxNumber)
            Operation.ADDITION -> viewModel.getAdditionExercise(minNumber, maxNumber)
            Operation.SUBTRACTION -> viewModel.getSubtractionExercise(minNumber, maxNumber)
        }



        binding.option1Top.setOnClickListener {
            val isCorrect = correctAnswerPositionTop == 1
            topPlayerAnswered(isCorrect)
        }
        binding.option2Top.setOnClickListener {
            val isCorrect = correctAnswerPositionTop == 2
            topPlayerAnswered(isCorrect)
        }
        binding.option3Top.setOnClickListener {
            val isCorrect = correctAnswerPositionTop == 3
            topPlayerAnswered(isCorrect)
        }
        binding.option4Top.setOnClickListener {
            val isCorrect = correctAnswerPositionTop == 4
            topPlayerAnswered(isCorrect)
        }

        binding.option1Bottom.setOnClickListener {
            val isCorrect = correctAnswerPositionBottom == 1
            bottomPlayerAnswered(isCorrect)
        }
        binding.option2Bottom.setOnClickListener {
            val isCorrect = correctAnswerPositionBottom == 2
            bottomPlayerAnswered(isCorrect)
        }
        binding.option3Bottom.setOnClickListener {
            val isCorrect = correctAnswerPositionBottom == 3
            bottomPlayerAnswered(isCorrect)
        }
        binding.option4Bottom.setOnClickListener {
            val isCorrect = correctAnswerPositionBottom == 4
            bottomPlayerAnswered(isCorrect)
        }
    }

    private fun setNewExerciseTop() {
        exercises[progressCounterTop].let {
            binding.option1Top.text = it.choice_1.toString()
            binding.option2Top.text = it.choice_2.toString()
            binding.option3Top.text = it.choice_3.toString()
            binding.option4Top.text = it.choice_4.toString()
            binding.tvConditionTop.text = it.condition
            correctNumberTop = it.correctAnswer
            correctAnswerPositionTop = it.correctAnswerPosition
        }
    }

    private fun setNewExerciseBottom() {
        exercises[progressCounterBottom].let {
            binding.option1Bottom.text = it.choice_1.toString()
            binding.option2Bottom.text = it.choice_2.toString()
            binding.option3Bottom.text = it.choice_3.toString()
            binding.option4Bottom.text = it.choice_4.toString()
            binding.tvConditionBottom.text = it.condition
            correctNumberBottom = it.correctAnswer
            correctAnswerPositionBottom = it.correctAnswerPosition
        }
    }

    private fun topPlayerAnswered(correctly: Boolean) {
        if (correctly) correctAnswersTop++ else wrongAnswersTop++
        progressCounterTop++
        if (progressCounterTop == 10) {
            topUserFinished = true
            finishExercise()
        } else setNewExerciseTop()
    }

    private fun bottomPlayerAnswered(correctly: Boolean) {
        if (correctly) correctAnswersBottom++ else wrongAnswersBottom++
        progressCounterBottom++
        if (progressCounterBottom == 10) {
            bottomUserFinished = true
            finishExercise()
        } else setNewExerciseBottom()
    }

    private fun finishExercise() {
        if (topUserFinished) {
            binding.topContainer.visibility = View.GONE
            binding.topUserFinished.visibility = View.VISIBLE
        }
        if (bottomUserFinished) {
            binding.bottomUserFinished.visibility = View.VISIBLE
            binding.bottomContainer.visibility = View.GONE
        }
        if (topUserFinished && bottomUserFinished) {
            findNavController().navigate(
                R.id.action_gameDuelFragment_to_gameDuelEndFragment,
                bundleOf(
                    ARG_RED_CORRECT_ANSWERS to correctAnswersTop,
                    ARG_RED_WRONG_ANSWERS to wrongAnswersTop,
                    ARG_BLUE_CORRECT_ANSWERS to correctAnswersBottom,
                    ARG_BLUE_WRONG_ANSWERS to wrongAnswersBottom
                )
            )
        }
    }
}