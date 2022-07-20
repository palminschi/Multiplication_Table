package com.palmdev.learn_math.presentation.screens.exercise_true_or_false

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentExerciseTrueOrFalseBinding
import com.palmdev.learn_math.utils.ARG_MAX_NUMBER
import com.palmdev.learn_math.utils.ARG_MIN_NUMBER
import com.palmdev.learn_math.utils.ARG_OPERATION
import com.palmdev.learn_math.utils.Operation
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
    private val progressViews by lazy {
        listOf(
            binding.progress1, binding.progress2, binding.progress3, binding.progress4,
            binding.progress5, binding.progress6, binding.progress7, binding.progress8,
            binding.progress9, binding.progress10
        )
    }

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
                true -> answeredCorrectly()
                false -> answeredWrongly()
            }
            progressCounter++
        }
        binding.btnFalse.setOnClickListener {
            when (isTrueOrFalse) {
                true -> answeredWrongly()
                false -> answeredCorrectly()
            }
            progressCounter++
        }
        progressViews.forEach { it.setBackgroundColor(resources.getColor(R.color.gray, null)) }
        binding.tvWrongAnswers.text = wrongAnswers.toString()
        binding.tvCorrectAnswers.text = correctAnswers.toString()
    }

    private fun getNewExercise() {
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
        binding.layoutCorrectAnswer.visibility = View.INVISIBLE
        binding.btnTrue.isClickable = true
        binding.btnFalse.isClickable = true
    }

    private fun answeredCorrectly() {
        correctAnswers++
        binding.tvCorrectAnswers.text = correctAnswers.toString()
        progressViews[progressCounter].setBackgroundColor(resources.getColor(R.color.green, null))
        binding.btnTrue.isClickable = false
        binding.btnFalse.isClickable = false
        handler.postDelayed({
            if (progressCounter == 9) {
                finishExercise()
                return@postDelayed
            }
            getNewExercise()
        }, 200)
    }

    private fun answeredWrongly() {
        wrongAnswers++
        binding.tvWrongAnswers.text = wrongAnswers.toString()
        progressViews[progressCounter].setBackgroundColor(resources.getColor(R.color.red, null))
        binding.layoutCorrectAnswer.visibility = View.VISIBLE
        binding.btnTrue.isClickable = false
        binding.btnFalse.isClickable = false
        handler.postDelayed({
            if (progressCounter == 9) {
                finishExercise()
                return@postDelayed
            }
            getNewExercise()
        }, 1500)
    }

    private fun finishExercise() {
        findNavController().navigate(R.id.action_exerciseTrueOrFalseFragment_to_endFragment) // TODO: Bundle
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }

}