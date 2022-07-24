package com.palmdev.learn_math.presentation.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        viewModel.loadAds()
    }

    private fun init() {
        viewModel.getResults()
        viewModel.coins.observe(viewLifecycleOwner) {
            binding.coins.text = it.toString()
        }
        viewModel.multiplicationCorrectAnswersPercent.observe(viewLifecycleOwner) {
            if (it != 0) {
                binding.tvMultiplication.text = "${getText(R.string.multiplicationLine)} $it%"
                binding.multiplicationProgress.progress = it
            } else {
                binding.tvMultiplication.text =
                    "${getText(R.string.multiplicationLine)} ${getText(R.string.questionMark)}"
            }
        }
        viewModel.divisionCorrectAnswersPercent.observe(viewLifecycleOwner) {
            if (it != 0) {
                binding.tvDivision.text = "${getText(R.string.divisionLine)} $it%"
                binding.divisionProgress.progress = it
            } else {
                binding.tvDivision.text =
                    "${getText(R.string.divisionLine)} ${getText(R.string.questionMark)}"
            }
        }
        viewModel.additionCorrectAnswersPercent.observe(viewLifecycleOwner) {
            if (it != 0) {
                binding.tvAddition.text = "${getText(R.string.additionLine)} $it%"
                binding.additionProgress.progress = it
            } else {
                binding.tvAddition.text =
                    "${getText(R.string.additionLine)} ${getText(R.string.questionMark)}"
            }
        }
        viewModel.subtractionCorrectAnswersPercent.observe(viewLifecycleOwner) {
            if (it != 0) {
                binding.tvSubtraction.text = "${getText(R.string.subtractionLine)} $it%"
                binding.subtractionProgress.progress = it
            } else {
                binding.tvSubtraction.text =
                    "${getText(R.string.subtractionLine)} ${getText(R.string.questionMark)}"
            }
        }
        binding.btnLearnTable.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_selectTableFragment)
        }
        binding.btnTraining.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_selectTrainingFragment)
        }
        binding.btnExam.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_startExamFragment)
        }
        binding.btnDetails.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_statisticsFragment)
        }
        binding.btnGames.setOnClickListener {
            // TODO:
            Snackbar.make(it, "Coming soon", Snackbar.LENGTH_SHORT).show()
        }
        binding.btnRemoveAds.setOnClickListener {
            // TODO:
            Snackbar.make(it, "Coming soon", Snackbar.LENGTH_SHORT).show()
        }

    }
}