package com.palmdev.learn_math.presentation.screens.start_exam

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentStartExamBinding
import com.palmdev.learn_math.utils.*


class StartExamFragment : Fragment() {

    private lateinit var binding: FragmentStartExamBinding
    private var difficulty = Difficulty.EASY
    private var operation = Operation.MULTIPLICATION
    private var minNumber = 0
    private var maxNumber = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartExamBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.easy.isChecked = true
        binding.multiply.isChecked = true

        binding.btnStart.setOnClickListener {
            if (binding.easy.isChecked) difficulty = Difficulty.EASY
            else if (binding.medium.isChecked) difficulty = Difficulty.MEDIUM
            else if (binding.hard.isChecked) difficulty = Difficulty.HARD

            if (binding.multiply.isChecked) operation = Operation.MULTIPLICATION
            else if (binding.divide.isChecked) operation = Operation.DIVISION
            else if (binding.plus.isChecked) operation = Operation.ADDITION
            else if (binding.minus.isChecked) operation = Operation.SUBTRACTION

            when (difficulty) {
                Difficulty.EASY -> {
                    minNumber = 0
                    maxNumber = 10
                }
                Difficulty.MEDIUM -> {
                    if (operation == Operation.MULTIPLICATION || operation == Operation.DIVISION) {
                        minNumber = 7
                        maxNumber = 20
                    } else {
                        minNumber = 7
                        maxNumber = 35
                    }
                }
                Difficulty.HARD -> {
                    if (operation == Operation.MULTIPLICATION || operation == Operation.DIVISION) {
                        minNumber = 15
                        maxNumber = 50
                    } else {
                        minNumber = 50
                        maxNumber = 200
                    }
                }
            }

            findNavController().navigate(
                R.id.action_startExamFragment_to_exerciseInputFragment,
                bundleOf(
                    ARG_OPERATION to operation,
                    ARG_MIN_NUMBER to minNumber,
                    ARG_MAX_NUMBER to maxNumber,
                    ARG_EXAM_OR_TRAINING to EXAM
                )
            )
        }
    }
}