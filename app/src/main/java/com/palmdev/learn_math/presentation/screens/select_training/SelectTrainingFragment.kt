package com.palmdev.learn_math.presentation.screens.select_training

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentSelectTrainingBinding
import com.palmdev.learn_math.presentation.screens.exercise_select.ExerciseSelectFragment
import com.palmdev.learn_math.presentation.screens.exercise_true_or_false.ExerciseTrueOrFalseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectTrainingFragment : Fragment() {

    private lateinit var binding: FragmentSelectTrainingBinding
    private var minNumber = 0
    private var maxNumber = 10
    private var operation = Operation.MULTIPLY

    enum class Operation { MULTIPLY, DIVIDE, PLUS, MINUS }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectTrainingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.multiply.isChecked = true
        binding.btnGameInput.setOnClickListener {
            checkEnteredData()
            // TODO: Navigation
        }
        binding.btnGameSelect.setOnClickListener {
            checkEnteredData()
            findNavController().navigate(
                R.id.action_selectTrainingFragment_to_exerciseSelectFragment,
                bundleOf(
                    ExerciseSelectFragment.ARG_OPERATION to when (operation) {
                        Operation.MULTIPLY -> ExerciseSelectFragment.Operation.MULTIPLICATION
                        Operation.DIVIDE -> ExerciseSelectFragment.Operation.DIVISION
                        Operation.PLUS -> ExerciseSelectFragment.Operation.PLUS
                        Operation.MINUS -> ExerciseSelectFragment.Operation.MINUS
                    },
                    ExerciseSelectFragment.ARG_MAX_NUMBER to maxNumber,
                    ExerciseSelectFragment.ARG_MIN_NUMBER to minNumber,
                    ExerciseSelectFragment.ARG_TYPE to ExerciseSelectFragment.Type.RANDOM
                )
            )
        }
        binding.btnGameTrueOrFalse.setOnClickListener {
            checkEnteredData()
            findNavController().navigate(
                R.id.action_selectTrainingFragment_to_exerciseTrueOrFalseFragment,
                bundleOf(
                    ExerciseTrueOrFalseFragment.ARG_OPERATION to when (operation) {
                        Operation.MULTIPLY -> ExerciseTrueOrFalseFragment.Operation.MULTIPLICATION
                        Operation.DIVIDE -> ExerciseTrueOrFalseFragment.Operation.DIVISION
                        Operation.PLUS -> ExerciseTrueOrFalseFragment.Operation.PLUS
                        Operation.MINUS -> ExerciseTrueOrFalseFragment.Operation.MINUS
                    },
                    ExerciseTrueOrFalseFragment.ARG_MAX_NUMBER to maxNumber,
                    ExerciseTrueOrFalseFragment.ARG_MIN_NUMBER to minNumber,
                )
            )
        }
    }

    private fun setData() {
        minNumber = if (binding.minNumber.text.isNullOrEmpty()) 0
        else binding.minNumber.text.toString().toInt()

        maxNumber = if (binding.maxNumber.text.isNullOrEmpty()) 10
        else binding.maxNumber.text.toString().toInt()

        if (binding.multiply.isChecked) operation = Operation.MULTIPLY
        if (binding.divide.isChecked) operation = Operation.DIVIDE
        if (binding.minus.isChecked) operation = Operation.MINUS
        if (binding.plus.isChecked) operation = Operation.PLUS
    }

    private fun checkEnteredData() {
        setData()
        if (minNumber > maxNumber) {
            val x = minNumber
            minNumber = maxNumber
            maxNumber = x
        }
    }

}