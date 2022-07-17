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
import com.palmdev.learn_math.utils.*

class SelectTrainingFragment : Fragment() {

    private lateinit var binding: FragmentSelectTrainingBinding
    private var minNumber = 0
    private var maxNumber = 10
    private var operation = Operation.MULTIPLICATION


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
            findNavController().navigate(
                R.id.action_selectTrainingFragment_to_exerciseInputFragment,
                bundleOf(
                    ARG_OPERATION to operation,
                    ARG_MAX_NUMBER to maxNumber,
                    ARG_MIN_NUMBER to minNumber
                )
            )
        }
        binding.btnGameSelect.setOnClickListener {
            checkEnteredData()
            findNavController().navigate(
                R.id.action_selectTrainingFragment_to_exerciseSelectFragment,
                bundleOf(
                    ARG_OPERATION to operation,
                    ARG_MAX_NUMBER to maxNumber,
                    ARG_MIN_NUMBER to minNumber,
                    ARG_TYPE to NumberType.RANDOM
                )
            )
        }
        binding.btnGameTrueOrFalse.setOnClickListener {
            checkEnteredData()
            findNavController().navigate(
                R.id.action_selectTrainingFragment_to_exerciseTrueOrFalseFragment,
                bundleOf(
                    ARG_OPERATION to operation,
                    ARG_MAX_NUMBER to maxNumber,
                    ARG_MIN_NUMBER to minNumber,
                )
            )
        }
    }

    private fun setData() {
        minNumber = if (binding.minNumber.text.isNullOrEmpty()) 0
        else binding.minNumber.text.toString().toInt()

        maxNumber = if (binding.maxNumber.text.isNullOrEmpty()) 10
        else binding.maxNumber.text.toString().toInt()

        if (binding.multiply.isChecked) operation = Operation.MULTIPLICATION
        if (binding.divide.isChecked) operation = Operation.DIVISION
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