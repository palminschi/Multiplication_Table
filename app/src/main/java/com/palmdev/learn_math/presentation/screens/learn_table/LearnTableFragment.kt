package com.palmdev.learn_math.presentation.screens.learn_table

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentLearnTableBinding
import com.palmdev.learn_math.presentation.animations.TurnOverAnim
import com.palmdev.learn_math.utils.ARG_OPERATION
import com.palmdev.learn_math.utils.ARG_SELECTED_NUMBER
import com.palmdev.learn_math.utils.ARG_WITH_NUMBER
import com.palmdev.learn_math.utils.Operation
import org.koin.androidx.viewmodel.ext.android.viewModel

class LearnTableFragment : Fragment() {

    private lateinit var binding: FragmentLearnTableBinding
    private val viewModel by viewModel<LearnTableViewModel>()
    private var selectedNumber = 1
    private var isDivision = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLearnTableBinding.inflate(layoutInflater, container, false)
        selectedNumber = arguments?.getInt(ARG_SELECTED_NUMBER) ?: 1
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initCustomOnBackPressed()
    }

    private fun init() {
        setMultiplication()
        viewModel.table.observe(viewLifecycleOwner) {
            binding.tvTable.text = it
        }

        binding.btnStartMultiply.setOnClickListener {
            findNavController().navigate(
                R.id.exerciseSelectFragment,
                bundleOf(
                    ARG_WITH_NUMBER to selectedNumber,
                    ARG_OPERATION to Operation.MULTIPLICATION
                )
            )
        }

        binding.btnStartDivision.setOnClickListener {
            findNavController().navigate(
                R.id.exerciseSelectFragment,
                bundleOf(
                    ARG_WITH_NUMBER to selectedNumber,
                    ARG_OPERATION to Operation.DIVISION
                )
            )
        }

        binding.btnSwitchTable.setOnClickListener {
            TurnOverAnim.anim(binding.cardView) {
                when (isDivision) {
                    true -> setMultiplication()
                    false -> setDivision()
                }
            }
        }
    }

    private fun setMultiplication(){
        viewModel.getMultiplicationTable(selectedNumber)
        isDivision = false
    }

    private fun setDivision(){
        viewModel.getDivisionTable(selectedNumber)
        isDivision = true
    }

    private fun initCustomOnBackPressed() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack(R.id.selectTableFragment, false)
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

}